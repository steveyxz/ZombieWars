package com.games.zombieWars.objects;

import com.games.zombieWars.GameObject;
import com.games.zombieWars.Handler;
import com.games.zombieWars.ID;

import java.awt.*;
import java.util.Random;

public class RadioactiveZone extends GameObject {

    public float opacity = 0.4F;
    private int colorTick = 0;
    private Handler handler;
    public int timeRemaining = 15;
    private int timeTick = 0;

    public RadioactiveZone(float x, float y, ID id, float speedX, float speedY, int hp, int width, int height, Handler handler) {
        super(x, y, id, speedX, speedY, hp, width, height);
        this.handler = handler;
    }

    private float floatClamp(float var, float min, float max) {
        if (var < min) {
            return floatClamp(new Random().nextFloat() - 0.2F, min, max);
        } else if (var > max) {
            return floatClamp(new Random().nextFloat() - 0.2F, min, max);
        } else {
            return var;
        }
    }

    @Override
    public void tick() {
        colorTick++;
        if (colorTick == 10) {
            opacity = floatClamp(new Random().nextFloat() - 0.2F, 0.2F, 0.4F);
            colorTick = 0;
        }
        timeTick++;
        if (timeTick == 60) {
            timeRemaining--;
            if (timeRemaining < 1) {
                kill();
            }
            timeTick = 0;
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(30 / 255F, 210 / 255F, 20 / 255F, opacity));
        g.fillOval((int) x, (int) y, width, height);
    }

    @Override
    public void kill() {
        handler.removeObject(this);
    }

    @Override
    public void realRender(Graphics2D g) {

    }
}
