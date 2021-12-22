package com.games.zombieWars.objects;

import com.games.zombieWars.*;

import java.awt.*;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class ZombieBullet extends GameObject {

    private final Handler handler;

    public ZombieBullet(float x, float y, ID id, float speedX, float speedY, int hp, int width, int height, Handler handler) {
        super(x, y, id, speedX, speedY, hp, width, height);

        this.handler = handler;
    }

    @Override
    public void tick() {
        if (alive) {
            x += speedX;
            y += speedY;

            if (x < -Game.additionalX) {
                kill();
            }

            if (x > Game.WIDTH + Game.additionalX) {
                kill();
            }

            if (y > Game.HEIGHT + Game.additionalY) {
                kill();
            }

            if (y < -Game.additionalY) {
                kill();
            }

            collision();
        }
    }

    private void collision() {
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            GameObject tempObject = handler.gameObjects.get(i);

            if (tempObject.getId() == ID.Player1) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    HUD.PLAYER_1_HP -= (long) HUD.currentRanged.get("damage");
                    ((Player) tempObject).isFlashing = true;
                    if (HUD.PLAYER_1_HP < 1) {
                        tempObject.kill();
                    }
                    kill();
                }
            } else if (tempObject.getId() == ID.Obstacle) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    kill();
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (alive) {
            g.setColor(Color.green);
            g.fillRect((int) x, (int) y, width, height);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }


    @Override
    public void kill() {
        handler.removeObject(this);
    }

    @Override
    public void realRender(Graphics2D g) {

    }
}
