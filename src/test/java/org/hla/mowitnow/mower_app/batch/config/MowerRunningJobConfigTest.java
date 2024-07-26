package org.hla.mowitnow.mower_app.batch.config;

import org.hla.mowitnow.mower_app.models.Direction;
import org.hla.mowitnow.mower_app.models.Mower;
import org.hla.mowitnow.mower_app.services.MowerService;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@SpringBatchTest
@Import(MowerRunningJobConfig.class)
@TestPropertySource(properties = {
        "datasource.schema=classpath:/org/springframework/batch/core/schema-h2.sql",
        "instructions.file.path=src/main/resources/instructions-test"
})
public class MowerRunningJobConfigTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private MowerService mowerService;



    @Test
    void jobExecutionTestKO() throws Exception {
        var jobParameters = new JobParametersBuilder()
                .toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        Mower mower = mowerService.getMower();
        assertThat(mower).isNotNull();
        assertThat(mower.getCoordinate().getX()).isEqualTo(0);
        assertThat(mower.getCoordinate().getY()).isEqualTo(0);
        assertThat(mower.getDirection()).isEqualTo(Direction.NORTH);
    }
    @Test
    void jobExecutionTestOK() throws Exception {
        var jobParameters = new JobParametersBuilder()
                .toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        Mower mower = mowerService.getMower();
        assertThat(mower).isNotNull();
        assertThat(mower.getCoordinate().getX()).isEqualTo(1);
        assertThat(mower.getCoordinate().getY()).isEqualTo(3);
        assertThat(mower.getDirection()).isEqualTo(Direction.NORTH);

    }@Test
    void jobExecutionTestRefacto() throws Exception {
        var jobParameters = new JobParametersBuilder()
                .toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        Mower mower = mowerService.getMower();
        assertThat(mower).isNotNull();
        assertThat(mower.getCoordinate().getX()).isEqualTo(1);
        assertThat(mower.getCoordinate().getY()).isEqualTo(3);
        assertThat(mower.getDirection()).isEqualTo(Direction.EAST);
    }
}
