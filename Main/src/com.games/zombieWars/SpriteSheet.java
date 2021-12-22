package com.games.zombieWars;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class SpriteSheet implements Serializable {

    private BufferedImage image;

    public SpriteSheet(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage grabImage(long col, long row, int width, int height) {
        BufferedImage img = image.getSubimage((int) (row * 32) - 32, (int) (col * 32) - 32, width, height);
        return img;
    }

}
