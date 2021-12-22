package com.games.zombieWars.screens;

import com.games.zombieWars.Game;

import java.awt.*;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class Paused {

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 40));
        g.drawString("PAUSED", Game.WIDTH / 2 - 100, 150);
        g.setFont(new Font("arial", Font.PLAIN, 20));
        g.setColor(Color.PINK);
        g.drawString("Press space to unpause", Game.WIDTH / 2 - 100, 300);
    }
    public void tick() {

    }

}
