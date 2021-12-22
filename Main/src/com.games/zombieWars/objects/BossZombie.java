package com.games.zombieWars.objects;

import com.games.zombieWars.*;

import java.awt.*;
import java.util.Random;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class BossZombie extends GameObject {

    public int health;
    public boolean alive = true;
    public int difficulty;
    private GameObject player;
    private Handler handler;
    private ID id;
    private int hp;
    public boolean isFlashing;
    public float opacity = 1;
    private int flashWait;
    private int damageDelay = 0;

    public BossZombie(int x, int y, ID id, int speedX, int speedY, int difficulty, int hp, int width, int height, Handler handler) {
        super(x, y, id, speedX, speedY, hp, width, height);

        this.difficulty = difficulty;
        this.handler = handler;

        setHp(difficulty*600);

        Random r = new Random();

        id = ID.Player1;

        for (int i = 0; i < handler.gameObjects.size(); i++) {
            if (handler.gameObjects.get(i).getId() == id) {
                player = handler.gameObjects.get(i);
            }
        }

    }

    private boolean isPlayerAlive() {
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            if (handler.gameObjects.get(i).getId() == ID.Player1) {
                if (handler.gameObjects.get(i).alive) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void tick() {
        if (alive && isPlayerAlive()) {
            x += speedX;
            x = Game.clamp(x, -120, Game.WIDTH + 120);
            y += speedY;
            y = Game.clamp(y, -120, Game.HEIGHT + 120);

            collision();

            float diffX = x - player.getX() - 8;
            float diffY = y - player.getY() - 8;
            float distance = (float) Math.sqrt(((diffX + 8)*(diffX + 8)) + ((diffY + 8)*(diffY + 8)));

            speedX = (float) (((-1.0/distance) * diffX) / 3);
            speedY = (float) (((-1.0/distance) * diffY) / 3);

            if (Spawn.timer%3 == 0 && Spawn.count == 1) {
                handler.addObject(new Zombie(x + new Random().nextInt(120), y + new Random().nextInt(120), ID.WeakZombie, 0, 0, 1, 10, 16, 16, handler));
                HUD.zombieCount++;
            }

            if (isFlashing) {
                opacity = 0.5F;
                flashWait++;
                if (flashWait == 10) {
                    opacity = 1;
                    flashWait = 0;
                    isFlashing = false;
                }
            }
        }
    }

    private void collision() {
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            GameObject tempObject = handler.gameObjects.get(i);
            if (tempObject.getId() == ID.RadioActiveZone) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    if (damageDelay == 0) {
                        isFlashing = true;
                        setHp(getHp() - (((RadioactiveZone) tempObject).timeRemaining*4));
                        if (getHp() < 1) {
                            kill();
                        }
                        damageDelay++;
                    } else {
                        damageDelay++;
                        if (damageDelay == 120) {
                            damageDelay = 0;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (alive && isPlayerAlive()) {
            switch (difficulty) {
                case 1:
                    g.setColor(new Color(0 / 255F, 255 / 255F, 255 / 255F, opacity));
                    g.fillRect((int)x, (int)y, 40, 40);
                    break;
                case 2:
                    g.setColor(new Color(0 / 255F, 255 / 255F, 0 / 255F, opacity));
                    g.fillRect((int)x, (int)y, 40, 40);
                    break;
                case 3:
                    g.setColor(new Color(255 / 255F, 200 / 255F, 0 / 255F, opacity));
                    g.fillRect((int)x, (int)y, 40, 40);
                    break;
                default:
                    break;
            }
        }
    }


    public void kill() {
        alive = false;
        HUD.zombieCount--;
        handler.gameObjects.remove(this);
    }

    @Override
    public void realRender(Graphics2D g) {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}

