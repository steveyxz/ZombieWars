package com.games.zombieWars.objects;

import com.games.zombieWars.Game;
import com.games.zombieWars.GameObject;
import com.games.zombieWars.ID;

import java.awt.*;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class Border extends GameObject {

    public Border(float x, float y, ID id, float speedX, float speedY, int hp, int width, int height) {
        super(x, y, id, speedX, speedY, hp, width, height);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(-Game.additionalX - 15, Game.HEIGHT + Game.additionalY + 15, Game.WIDTH + Game.additionalX * 2 + 30, 30);
        g.fillRect(-Game.additionalX - 30, -Game.additionalY - 30, Game.WIDTH + Game.additionalX * 2 + 30, 30);
        g.fillRect(-Game.additionalX - 30, -Game.additionalY - 30, 30, Game.HEIGHT + Game.additionalY * 2 + 30);
        g.fillRect(Game.WIDTH + Game.additionalX + 15, -Game.additionalY - 15, 30, Game.HEIGHT + 2 * Game.additionalY + 30);
    }

    @Override
    public void kill() {

    }

    @Override
    public void realRender(Graphics2D g) {

    }
}
