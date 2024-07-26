package org.hla.mowitnow.mower_app.services;

import org.hla.mowitnow.mower_app.models.Direction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class DirectionServiceTest {

    @Autowired
    private DirectionService directionService;



    @Test
    public void getDirectionByAngleTestKO(){
       assertThat(directionService.getDirectionByAngle(0)).isEmpty();
    }
    @Test
    public void getDirectionByAngleTestOK(){
        assertThat(directionService.getDirectionByAngle(0)).isPresent();
    }

    @Test
    public void getDirectionByAngleTestRefactoOK(){
        assertThat(directionService.getDirectionByAngle(0)).isPresent().contains(Direction.NORTH);
    }

    @Test
    public void getDirectionByAngleTestRefactoKO(){
        assertThat(directionService.getDirectionByAngle(-1)).isEmpty();
    }

    @Test
    public void getDirectionByOrientationTestKO(){
        assertThat(directionService.getDirectionByOrientation("S")).isEmpty();
    }
    @Test
    public void getDirectionByOrientationTestOK(){
        assertThat(directionService.getDirectionByOrientation("S")).isNotEmpty();
    }

    @Test
    public void getDirectionByOrientationTestRefactoOK(){
        assertThat(directionService.getDirectionByOrientation("S")).isPresent().contains(Direction.SOUTH);
    }

    @Test
    public void getDirectionByOrientationTestRefactoKO(){
        assertThat(directionService.getDirectionByOrientation("A")).isEmpty();
    }

}
