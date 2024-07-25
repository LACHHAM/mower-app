package org.hla.mowitnow.mower_app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


/**
 * @author lach_hamza
 * Une énumération qui représente la direction de la tondeuse
 */
@Getter
@AllArgsConstructor
@ToString
public enum Direction {
    NORTH("N", 0, 1, 0),
    EAST("E", 1, 0, 90),
    SOUTH("S", 0, -1, 180),
    WEST("W", -1, 0, 270);


    private final String orientation;
    private final int xIncrement;
    private final int yIncrement;
    private final int angle;
}
