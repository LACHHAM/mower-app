package org.hla.mowitnow.mower_app.batch.config;

import org.hla.mowitnow.mower_app.models.Coordinate;
import org.hla.mowitnow.mower_app.models.Direction;
import org.hla.mowitnow.mower_app.models.Lawn;
import org.hla.mowitnow.mower_app.models.Mower;
import org.hla.mowitnow.mower_app.services.DirectionService;
import org.hla.mowitnow.mower_app.services.MowerService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Configuration
@EnableBatchProcessing
public class MowerRunningJobConfig {


    @Value("${datasource.schema}")
    private String h2SchemaPath;

    @Value("${instructions.file.path}")
    public String filePath;

    @Value("${job.name}")
    private String jobName;

    private final MowerService mowerService;
    private final DirectionService directionService;

    public MowerRunningJobConfig(MowerService wtitorService, DirectionService directionService) {
        this.mowerService = wtitorService;
        this.directionService = directionService;
    }

    @Bean
    public ItemReader<String> reader() {
        FileSystemResource resource = new FileSystemResource(filePath);
        if (!resource.exists()) {
            throw new IllegalStateException("File not found: " + resource.getPath());
        }

        return new FlatFileItemReaderBuilder<String>()
                .name("itemReader")
                .resource(resource)
                .lineMapper((line, lineNumber) -> line)
                .build();
    }

    @Bean
    public ItemProcessor<String, Object> processor() {
        return line -> {
            if (islawnLine(line)) {
                return createLawn(line);
            } else if (isMowerLocationLine(line)) {
                return createMower(line);
            } else {
                return Arrays.asList(line.split(""));
            }
        };
    }

    private boolean islawnLine(String line) {
        final String REGEX_SURFACE = "^\\s*(\\d+)\\s+(\\d+)\\s*$";
        return Pattern.compile(REGEX_SURFACE).matcher(line).find();
    }

    private boolean isMowerLocationLine(String line) {
        final String REGEX_MOWER_LOCATION = "^\\s*(\\d+)\\s+(\\d+)\\s+([NEWS])\\s*$";
        return Pattern.compile(REGEX_MOWER_LOCATION).matcher(line).find();
    }

    private Lawn createLawn(String line) {
        String[] parts = line.trim().split("\\s+");
        int width = Integer.parseInt(parts[0]);
        int height = Integer.parseInt(parts[1]);
        return Lawn.builder().maxWidth(width).maxHeight(height).build();
    }

    private Mower createMower(String line) {
        String[] parts = line.trim().split("\\s+");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        Direction direction = directionService.getDirectionByOrientation(parts[2]).orElse(Direction.NORTH);
        return Mower.builder()
                .coordinate(Coordinate.builder().x(x).y(y).build())
                .direction(direction)
                .build();
    }

    @Bean
    public ItemWriter<Object> writer() {
        return items -> items.forEach(this::processItem);
    }

    private void processItem(Object item) {
        if (item instanceof Mower mower) {
            mowerService.setMower(mower);
        } else if (item instanceof Lawn lawn) {
            mowerService.setLawn(lawn);
        } else if (item instanceof List<?> instructions) {
            executeInstructions((List<String>) instructions);
        }
    }

    private void executeInstructions(List<String> instructions) {
        mowerService.execute(instructions);
        printMowerFinalPosition(mowerService.getMower());
    }

    private void printMowerFinalPosition(Mower mower) {
        System.out.printf("%d %d %s%n",
                mower.getCoordinate().getX(),
                mower.getCoordinate().getY(),
                mower.getDirection().getOrientation());
    }
    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder(jobName, jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
        return new StepBuilder("step", jobRepository)
                .<String, Object>chunk(1, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public DataSource dataSource() {
        System.out.println(h2SchemaPath);
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(h2SchemaPath)
                .build();
    }

    @Bean
    public JdbcTransactionManager transactionManager(DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }
}
