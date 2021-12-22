package com.games.zombieWars.objects;

import com.games.zombieWars.*;
import org.w3c.dom.ranges.Range;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class MWeapon extends GameObject {

    private final Handler handler;
    private SpriteSheet spriteSheet;
    private BufferedImage image;
    private int count = 0;
    private int secs = 0;
    private Camera cam;
    private GameObject tempObject;


    public MWeapon(float x, float y, ID id, float speedX, float speedY, int hp, int width, int height, Handler handler, SpriteSheet ss, Camera cam) {
        super(x, y, id, speedX, speedY, hp, width, height);

        this.handler = handler;
        this.spriteSheet = ss;
        this.cam = cam;
    }

    public void findPlayer() {
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            if (handler.gameObjects.get(i).getId() == ID.Player1) {
                tempObject = handler.gameObjects.get(i);
                break;
            }
        }
    }

    @Override
    public void tick() {
        findPlayer();

        if (alive) {

            if (count < 10 && count > 0) {
                HUD.isMeleeing = true;
                x += speedX;
                y += speedY;
            } else if (count < 20 && count > 10) {
                x -= speedX;
                y -= speedY;
            } else if (count < 30 && count > 20) {
                HUD.isMeleeing = false;
                kill();
            }

            if (x < -Game.additionalX) {
                HUD.isMeleeing = false;
                kill();
            }

            if (x > Game.WIDTH + Game.additionalX) {
                HUD.isMeleeing = false;
                kill();
            }

            if (y > Game.HEIGHT + Game.additionalY) {
                HUD.isMeleeing = false;
                kill();
            }

            if (y < -Game.additionalY) {
                HUD.isMeleeing = false;
                kill();
            }

            collision();

            count++;
        }
    }

    private void collision() {
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            GameObject tempObject = handler.gameObjects.get(i);

            if (tempObject instanceof Zombie) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    tempObject.setHp((int) (tempObject.getHp()-(long) HUD.currentMelee.get("damage")));
                    ((Zombie) tempObject).isFlashing = true;
                    if (tempObject.getHp() < 1) {
                        tempObject.kill();
                    }
                    HUD.isMeleeing = false;
                    kill();
                }
            } else if (tempObject instanceof RangedZombie) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    tempObject.setHp((int) (tempObject.getHp()-(long) HUD.currentMelee.get("damage")));
                    ((RangedZombie) tempObject).isFlashing = true;
                    if (tempObject.getHp() < 1) {
                        tempObject.kill();
                    }
                    HUD.isMeleeing = false;
                    kill();
                }
            } else if (tempObject instanceof BossZombie) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    tempObject.setHp((int) (tempObject.getHp()-(long) HUD.currentMelee.get("damage")));
                    ((BossZombie) tempObject).isFlashing = true;
                    if (tempObject.getHp() < 1) {
                        tempObject.kill();
                    }
                    HUD.isMeleeing = false;
                    kill();
                }
            } else if (tempObject.getId() == ID.Obstacle) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    HUD.isMeleeing = false;
                    kill();
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {

    }

    public void realRender(Graphics2D g) {
        if (alive) {
            g.drawImage(spriteSheet.grabImage((long) HUD.currentMelee.get("skinCol"), (long) HUD.currentMelee.get("skinRow"), 20, 20), (int) x, (int) y, null);
        }
    }

    @Override
    public void kill() {
        handler.removeObject(this);
    }
}
