package org.hla.mowitnow.mower_app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author lach_hamza
 * une enumération la direction et l'angle de rotation de la tandeuse
 */
@Getter
@AllArgsConstructor
@ToString
public enum Orientation {
    RIGHT("D", 90),
    LEFT("G", -90);

    private static final Map<String, Orientation> DIRECTION_TO_ORIENTATION_MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(Orientation::getDirection, Function.identity()));

    private final String direction;
    private final int angle;

    public static Optional<Orientation> getOrientationByDirection(String direction) {
        return Optional.ofNullable(DIRECTION_TO_ORIENTATION_MAP.get(direction.toUpperCase()));
    }
}
