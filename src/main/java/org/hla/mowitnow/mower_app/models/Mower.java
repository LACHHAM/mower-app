package org.hla.mowitnow.mower_app.models;

import lombok.*;

/**
 * @author lach_hamza
 * une classe qui représente la tandeuse et sont état(coordonnées, la direction)
 */
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class Mower{
    private Coordinate coordinate;
    private Direction direction;
}
