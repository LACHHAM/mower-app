package org.hla.mowitnow.mower_app.services;

import lombok.Data;
import org.hla.mowitnow.mower_app.models.Lawn;
import org.hla.mowitnow.mower_app.models.Mower;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lach_hamza
 */
@Service
@Data
public class MowerService {


    private final CoordinateService coordinateService;
    private final OrientationService orientationService;
    private final DirectionService directionService;

    private static final String LEFT = "G";
    private static final String RIGHT = "D";
    private static final String FORWARD = "A";

    private Mower mower;
    private Lawn lawn;
    public MowerService(CoordinateService coordinateService, DirectionService directionService, OrientationService orientationService) {
        this.coordinateService = coordinateService;
        this.directionService = directionService;
        this.orientationService = orientationService;
    }

    /**
     *
     * @param instructions
     * effectuer les mouvements de la tandeuse selon les commandes passées dans la chaine instructions
     */
    public void execute(List<String> instructions) {
        AtomicInteger nextAngle = new AtomicInteger(mower.getDirection().getAngle());

        instructions.forEach(instr -> {
            switch (instr) {
                case LEFT -> rotateMower(nextAngle, LEFT);
                case RIGHT -> rotateMower(nextAngle, RIGHT);
                case FORWARD -> moveMowerForward(nextAngle);
            }
        });
    }

    private void rotateMower(AtomicInteger nextAngle, String orientation) {
        int mowerAngle = mower.getDirection().getAngle();
        nextAngle.set(orientationService.getAngleByOrientation(mowerAngle, orientation));
        directionService.getDirectionByAngle(nextAngle.get()).ifPresent(mower::setDirection);
    }

    private void moveMowerForward(AtomicInteger nextAngle) {
        directionService.getDirectionByAngle(nextAngle.get()).ifPresent(direction -> {
            mower.setDirection(direction);
            coordinateService.nextCoordinate(mower.getCoordinate(), direction, lawn);
        });
    }


}
