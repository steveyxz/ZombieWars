package com.games.zombieWars.objects;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

import com.games.zombieWars.GameObject;
import com.games.zombieWars.Handler;
import com.games.zombieWars.ID;

import java.awt.*;

public class Obstacle extends GameObject {

    private int width;
    private int height;
    private Color color;
    private Handler handler;

    public Obstacle(float x, float y, ID id, float speedX, float speedY, int hp, int width, int height, Color color, Handler handler) {
        super(x, y, id, speedX, speedY, hp, width, height);

        this.height = height;
        this.width = width;
        this.color = color;
        this.handler = handler;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, width, height);
    }

    @Override
    public void kill() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    @Override
    public void realRender(Graphics2D g) {

    }
}
