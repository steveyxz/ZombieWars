package com.games.zombieWars;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.Serializable;

/*
 * Copying this class in any way is not allowed.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class BufferedImageLoader implements Serializable {

    BufferedImage image;

    public BufferedImage loadImage(String path) {
        try {
            image = ImageIO.read(new FileInputStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

}
