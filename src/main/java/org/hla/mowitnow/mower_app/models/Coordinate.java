package org.hla.mowitnow.mower_app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lach_hamza
 * C'est une classe qui représente les cordonnées de la tondeuse
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {
    private int x, y;
}
