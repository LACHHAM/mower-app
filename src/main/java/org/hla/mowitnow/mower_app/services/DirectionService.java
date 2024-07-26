package org.hla.mowitnow.mower_app.services;

import org.hla.mowitnow.mower_app.models.Direction;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lach_hamza
 */
@Service
public class DirectionService {
    /**
     *
     * @param angle l'angle de la direction
     * @return la direction de la tandeuse selon la valeur de l'angle
     */
    public Optional<Direction> getDirectionByAngle(int angle) {
        return Arrays.stream(Direction.values())
                .filter(direction -> direction.getAngle() == angle)
                .findFirst();
    }

    /**
     *
     * @param orientation l'orientation de la direction
     * @return la direction de la tandeuse selon la valeur de l'orientation
     */
    public Optional<Direction> getDirectionByOrientation(String orientation) {
        return Optional.ofNullable(Arrays.stream(Direction.values())
                .collect(Collectors.toMap(Direction::getOrientation, Function.identity())).get(orientation.toUpperCase()));
    }
}
