package org.hla.mowitnow.mower_app.services;

import org.hla.mowitnow.mower_app.models.Coordinate;
import org.hla.mowitnow.mower_app.models.Direction;
import org.hla.mowitnow.mower_app.models.Lawn;
import org.springframework.stereotype.Service;

@Service
public class CoordinateService {

    /**
     *
     * @param currentCoordinate
     * @param direction
     * @param lawn
     * modifier les cordonnées de la tandeuse selon sa direction et les dimensions du pelouse
     */
    public void nextCoordinate(Coordinate currentCoordinate, Direction direction, Lawn lawn) {
        int newX = currentCoordinate.getX() + direction.getXIncrement();
        int newY = currentCoordinate.getY() + direction.getYIncrement();

        if (isValueWithinBounds(newX, lawn.getMinWidth(), lawn.maxWidth())) {
            currentCoordinate.setX(newX);
        }

        if (isValueWithinBounds(newY, lawn.getMinHeight(), lawn.maxHeight())) {
            currentCoordinate.setY(newY);
        }
    }

    private boolean isValueWithinBounds(int value, int minValue, int maxValue) {
        return value >= minValue && value <= maxValue;
    }

}
