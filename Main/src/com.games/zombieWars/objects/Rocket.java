package com.games.zombieWars.objects;

import com.games.zombieWars.*;

import java.awt.*;
import java.util.Random;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class Rocket extends GameObject {

        private final Handler handler;
        private GameObject tempObject;
        private Camera cam;

        public Rocket(float x, float y, ID id, float speedX, float speedY, int hp, int width, int height, Handler handler, Camera cam) {
            super(x, y, id, speedX, speedY, hp, width, height);

            this.handler = handler;
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
            if (alive) {
                x += speedX;
                y += speedY;

                if (x < -Game.additionalX) {
                    explode();
                    kill();
                }

                if (x > Game.WIDTH + Game.additionalX) {
                    explode();
                    kill();
                }

                if (y > Game.HEIGHT + Game.additionalY) {
                    explode();
                    kill();
                }

                if (y < -Game.additionalY) {
                    explode();
                    kill();
                }

                collision();
            }
        }

        private void explode() {
            for (int i = 0; i < (long) HUD.currentRanged.get("power"); i++) {
                Random r = new Random();
                findPlayer();
                int exX = r.nextInt(5);
                int exY = r.nextInt(5);
                if (r.nextInt(135436) == 0) {
                    exX *= -1;
                } else {
                    exX *= 1;
                }
                if (r.nextInt(23456) == 0) {
                    exY *= -1;
                } else {
                    exY *= 1;
                }
                GameObject gameObject = handler.addObject(new Bullet(
                        x + exX,
                        y + exY,
                        ID.Bullet,
                        0,
                        0,
                        20,
                        4,
                        4,
                        handler));
                float diffX = tempObject.getX() - x - 8 + cam.getX();
                float diffY = tempObject.getY() - y - 8 + cam.getY();
                float distance = (float) Math.sqrt(((diffX + 8) * (diffX + 8)) + ((diffY + 8) * (diffY + 8)));
                float exVelX = r.nextFloat()*2;
                float exVelY = r.nextFloat()*2;
                if (r.nextInt(2) == 0) {
                    exVelY *= -1;
                }
                if (r.nextInt(2) == 0) {
                    exVelX *= -1;
                }
                System.out.println(exVelY);
                System.out.println(exVelX);
                gameObject.setVelX((float) ((((-1.0 / distance) * diffX) * 3) * (exVelX * (r.nextFloat() * 1.3))));
                gameObject.setVelY((float) ((((-1.0 / distance) * diffY) * 3) * (exVelY * (r.nextFloat() * 1.3))));
            }
        }

        private void collision() {
            for (int i = 0; i < handler.gameObjects.size(); i++) {
                GameObject tempObject = handler.gameObjects.get(i);

                if (tempObject.getId() == ID.WeakZombie) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        tempObject.setHp((int) (tempObject.getHp()-(long) HUD.currentRanged.get("damage")));
                        if (tempObject.getHp() < 1) {
                            tempObject.kill();
                        }
                        explode();
                        kill();
                    }
                } else if (tempObject.getId() == ID.StrongZombie) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        tempObject.setHp((int) (tempObject.getHp()-(long) HUD.currentRanged.get("damage")));
                        if (tempObject.getHp() < 1) {
                            tempObject.kill();
                        }
                        explode();
                        kill();
                    }
                } else if (tempObject.getId() == ID.MediumZombie) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        tempObject.setHp((int) (tempObject.getHp()-(long) HUD.currentRanged.get("damage")));
                        if (tempObject.getHp() < 1) {
                            tempObject.kill();
                        }
                        explode();
                        kill();
                    }
                } else if (tempObject.getId() == ID.BossZombie) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        tempObject.setHp((int) (tempObject.getHp()-(long) HUD.currentRanged.get("damage")));
                        if (tempObject.getHp() < 1) {
                            tempObject.kill();
                        }
                        explode();
                        kill();
                    }
                } else if (tempObject.getId() == ID.FastZombie) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        tempObject.setHp((int) (tempObject.getHp()-(long) HUD.currentRanged.get("damage")));
                        if (tempObject.getHp() < 1) {
                            tempObject.kill();
                        }
                        explode();
                        kill();
                    }
                } else if (tempObject.getId() == ID.TankZombie) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        tempObject.setHp((int) (tempObject.getHp()-(long) HUD.currentRanged.get("damage")));
                        if (tempObject.getHp() < 1) {
                            tempObject.kill();
                        }
                        explode();
                        kill();
                    }
                }else if (tempObject.getId() == ID.BasicRangedZombie) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        tempObject.setHp((int) (tempObject.getHp()-(long) HUD.currentRanged.get("damage")));
                        if (tempObject.getHp() < 1) {
                            tempObject.kill();
                        }
                        explode();
                        kill();
                    }
                } else if (tempObject.getId() == ID.MutatedZombie) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        tempObject.setHp((int) (tempObject.getHp()-(long) HUD.currentRanged.get("damage")));
                        if (tempObject.getHp() < 1) {
                            tempObject.kill();
                        }
                        explode();
                        kill();
                    }
                } else if (tempObject.getId() == ID.Obstacle) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        explode();
                        kill();
                    }
                }
            }
        }

        @Override
        public void render(Graphics g) {
            if (alive) {
                g.setColor(Color.yellow);
                g.fillRect((int) x, (int) y, 16, 16);
            }
        }

        @Override
        public void kill() {
            handler.removeObject(this);
        }

    @Override
    public void realRender(Graphics2D g) {

    }
}
