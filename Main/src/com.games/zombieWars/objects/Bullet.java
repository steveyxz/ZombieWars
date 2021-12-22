package com.games.zombieWars.objects;

import com.games.zombieWars.*;

import java.awt.*;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class Bullet extends GameObject {

    private final Handler handler;

    public Bullet(float x, float y, ID id, float speedX, float speedY, int hp, int width, int height, Handler handler) {
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

            if (tempObject instanceof Zombie ) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    nukeBoom(tempObject);
                    tempObject.setHp((int) (tempObject.getHp() - (long) HUD.currentRanged.get("damage")));
                    ((Zombie) tempObject).isFlashing = true;
                    if (tempObject.getHp() < 1) {
                        tempObject.kill();
                    }
                    kill();
                }
            } else if (tempObject instanceof BossZombie) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    nukeBoom(tempObject);
                    tempObject.setHp((int) (tempObject.getHp() - (long) HUD.currentRanged.get("damage")));
                    ((BossZombie) tempObject).isFlashing = true;
                    if (tempObject.getHp() < 1) {
                        tempObject.kill();
                    }
                    kill();
                }
            } else if (tempObject instanceof RangedZombie) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    nukeBoom(tempObject);
                    tempObject.setHp((int) (tempObject.getHp() - (long) HUD.currentRanged.get("damage")));
                    ((RangedZombie) tempObject).isFlashing = true;
                    if (tempObject.getHp() < 1) {
                        tempObject.kill();
                    }
                    kill();
                }
            } else if (tempObject.getId() == ID.Obstacle) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    nukeBoom(tempObject);
                    kill();
                }
            }
        }
    }

    private void nukeBoom(GameObject object) {
        int index = 0;
        for (int i = 0; i < Game.allRanged.size(); i++) {
            if (Game.allRanged.get(i).get("name").equals("Nuke Launcher")) {
                index = i;
            }
        }
        if (Game.allRanged.get(index).get("state").equals("current")) {
            handler.addObject(new RadioactiveZone(object.getX() - 100, object.getY() - 100, ID.RadioActiveZone, 0, 0, 100000, 200, 200, handler));
        }
    }

    @Override
    public void render(Graphics g) {
        if (alive) {
            g.setColor(Color.yellow);
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
