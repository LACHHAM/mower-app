package org.hla.mowitnow.mower_app.services;

import org.hla.mowitnow.mower_app.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MowerServiceTest {
    @Autowired
    private CoordinateService coordinateService;

    @Autowired
    private OrientationService orientationService;

    @Autowired
    private DirectionService directionService;

    @Autowired
    private MowerService mowerService;

    private Mower mower;

    @BeforeEach
    public void setup() {
        mower = Mower.builder()
                .coordinate(new Coordinate(0, 0))
                .direction(Direction.NORTH)
                .build();
        Lawn lawn = Lawn.builder()
                .maxWidth(5)
                .maxHeight(5)
                .build();
        mowerService.setMower(mower);
        mowerService.setLawn(lawn);
    }

    @Test
    public void executeTestKO(){
        mowerService.execute(List.of("G"));
        assertThat(mower.getDirection()).isNull();
    }

    @Test
    public void executeTestOK(){
        mowerService.execute(List.of("G"));
        assertThat(mower.getDirection()).isNotNull();
    }

    @Test
    public void executeTestRefactoOK(){
        mowerService.execute(List.of("G"));
        assertThat(mower.getDirection()).isEqualTo(Direction.WEST);
    }
}
