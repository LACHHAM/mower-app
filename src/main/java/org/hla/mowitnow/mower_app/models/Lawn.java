package org.hla.mowitnow.mower_app.models;

import lombok.Builder;
import lombok.Data;

/**
 * @author lach_hamza
 * une classe qui représente la pelouse
 */
@Builder
public record Lawn(int maxWidth, int maxHeight){
    private static final int MIN_WIDTH = 0;
    private static final int MIN_HEIGHT = 0;

    public int getMinHeight() {
        return MIN_HEIGHT;
    }

    public int getMinWidth() {
        return MIN_WIDTH;
    }

}
