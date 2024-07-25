package org.hla.mowitnow.mower_app.services;


import org.hla.mowitnow.mower_app.models.Orientation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrientationServiceTest {

    @Autowired
    private OrientationService orientationService;

    @Test
    public void getOrientationAngleByDirectionKO(){
        assertThat(orientationService.getOrientationByDirection("G")).isEmpty();
    }
    @Test
    public void getOrientationAngleByDirectionOK(){
        assertThat(orientationService.getOrientationByDirection("G")).isPresent();
    }
    @Test
    public void getOrientationByDirectionTestRefactoOK() {
        assertThat(orientationService.getOrientationByDirection("G")).isPresent().contains(Orientation.LEFT);
    }
    @Test
    public void getOrientationByDirectionTestRefactoKO() {
        assertThat(orientationService.getOrientationByDirection("A")).isEmpty();
    }


    @Test
    public void getAngleByOrientationKO(){
        assertThat(orientationService.getAngleByOrientation(180, "A")).isZero();
    }
    @Test
    public void getProperAngleTestOK(){
        assertThat(orientationService.getAngleByOrientation(180, "G")).isNotZero();
    }

    @Test
    public void getProperAngleTestRefacto(){
        assertThat(orientationService.getAngleByOrientation(180, "D")).isEqualTo(270);
    }
}
