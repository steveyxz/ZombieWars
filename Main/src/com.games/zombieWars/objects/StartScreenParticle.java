package com.games.zombieWars.objects;

import com.games.zombieWars.GameObject;
import com.games.zombieWars.ID;
import com.games.zombieWars.ParticleHandler;

import java.awt.*;
import java.util.Random;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class StartScreenParticle extends GameObject {

    private int red;
    private int blue;
    private int green;
    private ParticleHandler handler;

    public StartScreenParticle(float x, float y, ID id, float speedX, float speedY, int hp, int width, int height, ParticleHandler handler) {
        super(x, y, id, speedX, speedY, hp, width, height);
        this.handler = handler;
        Random r = new Random();
        green = r.nextInt(255);
        red = r.nextInt(255);
        blue = r.nextInt(255);
    }

    @Override
    public void tick() {
        x += speedX / 4;
        y += speedY / 4;
    }

    @Override
    public void render(Graphics g) {
        Random r = new Random();
        g.setColor(new Color(red, green, blue));
        g.fillRect((int) x, (int) y, 8, 16);
    }

    @Override
    public void kill() {
        handler.removeObject(this);
    }

    @Override
    public void realRender(Graphics2D g) {

    }
}
