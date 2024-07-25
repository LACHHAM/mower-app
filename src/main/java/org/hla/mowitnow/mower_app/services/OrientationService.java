package org.hla.mowitnow.mower_app.services;

import org.hla.mowitnow.mower_app.models.Orientation;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class OrientationService {
    private static final int CIRCLE_DEGREE = 360;

    /**
     *
     * @param direction
     * @return l'orientation de la tandeuse par la direction
     */
    public Optional<Orientation> getOrientationByDirection(String direction) {
        return Orientation.getOrientationByDirection(direction);
    }

    /**
     *
     * @param initAngle
     * @param orientationDirection
     * @return la valeur de l'angle apreés la rotation
     */
    public int getAngleByOrientation(int initAngle, String orientationDirection) {
        Optional<Orientation> orientation = getOrientationByDirection(orientationDirection);
        int angleChange = orientation.map(Orientation::getAngle).orElse(0);
        return calculateAngle(initAngle + angleChange);
    }

    /**
     *
     * @param angle
     * @return la bonne valeur de l'angle
     */
    private int calculateAngle(int angle) {
        return ((angle % CIRCLE_DEGREE) + CIRCLE_DEGREE) % CIRCLE_DEGREE;
    }
}
