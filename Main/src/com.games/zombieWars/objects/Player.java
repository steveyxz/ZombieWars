package com.games.zombieWars.objects;

import com.games.zombieWars.*;

import java.awt.*;

/*
 * Copying this class in any way is not allowed.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class Player extends GameObject {

    private final Handler handler;
    private Game game;
    private int count = 0;
    private int secs = 0;

    public boolean isFlashing = false;
    public int flashWait = 0;
    public double opacity = 1;

    public Player(int x, int y, ID id, int speedX, int speedY, int hp, int width, int height, Handler handler, Game game) {
        super(x, y, id, speedX, speedY, hp, width, height);

        this.handler = handler;
        this.game = game;
    }

    @Override
    public void tick() {
        if (alive) {
            if (!HUD.isMeleeing) {
                x += speedX;
                x = Game.clamp(x, -Game.additionalX, Game.WIDTH + Game.additionalX);
                y += speedY;
                y = Game.clamp(y, -Game.additionalY, Game.HEIGHT + Game.additionalY);

                if (count % 30 == 0 && (speedX != 0 || speedY != 0)) {
                    game.sound.playSound();
                    count = 0;
                }
                if (speedY == 0 && speedX == 0) {
                    game.sound.pauseSound();
                }
                count++;

                collision();
            }
            if (isFlashing) {
                opacity = 0.5;
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
            GameObject tempObject;
            if (i < handler.gameObjects.size()) {
                tempObject = handler.gameObjects.get(i);
            } else {
                tempObject = handler.gameObjects.get(i - 1);
            }

            if (tempObject.getId() == ID.WeakZombie) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    tempObject.kill();
                    if (id == ID.Player1) {
                        HUD.PLAYER_1_HP -= 5;
                        if (HUD.PLAYER_1_HP < 1) {
                            kill();
                        }
                    }
                }
            } else if (tempObject.getId() == ID.StrongZombie) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    tempObject.kill();
                    if (id == ID.Player1) {
                        HUD.PLAYER_1_HP -= 15;
                        if (HUD.PLAYER_1_HP < 1) {
                            kill();
                        }
                    }
                }
            } else if (tempObject.getId() == ID.MediumZombie) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    tempObject.kill();
                    if (id == ID.Player1) {
                        HUD.PLAYER_1_HP -= 10;
                        if (HUD.PLAYER_1_HP < 1) {
                            kill();
                        }
                    }
                }
            } else if (tempObject.getId() == ID.MutatedZombie) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        tempObject.kill();
                        if (id == ID.Player1) {
                            HUD.PLAYER_1_HP -= 80;
                            if (HUD.PLAYER_1_HP < 1) {
                                kill();
                            }
                        }
                    }
                } else if (tempObject.getId() == ID.BossZombie) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        if (id == ID.Player1) {
                            HUD.PLAYER_1_HP -= 1;
                            if (HUD.PLAYER_1_HP < 1) {
                                kill();
                            }
                        }
                    }
                } else if (tempObject.getId() == ID.FastZombie) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        if (id == ID.Player1) {
                            tempObject.kill();
                            HUD.PLAYER_1_HP -= 9;
                            if (HUD.PLAYER_1_HP < 1) {
                                kill();
                            }
                        }
                    }
                } else if (tempObject.getId() == ID.TankZombie) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        if (id == ID.Player1) {
                            tempObject.kill();
                            HUD.PLAYER_1_HP -= 40;
                            if (this.hp < 1) {
                                kill();
                            }
                        }
                    }
                } else if (tempObject.getId() == ID.Obstacle) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        if (getBounds().intersects(tempObject.getBoundsBottom())) {
                            y = Game.clamp(y, tempObject.getY() + tempObject.getHeight(), Game.HEIGHT + Game.additionalY - height);
                        }
                        if (getBounds().intersects(tempObject.getBoundsTop())) {
                            y = Game.clamp(y, -Game.additionalY, tempObject.getY() - height);
                        }
                        if (getBounds().intersects(tempObject.getBoundsLeft())) {
                            x = Game.clamp(x, -Game.additionalX, tempObject.getX() - width);
                        }
                        if (getBounds().intersects(tempObject.getBoundsRight())) {
                            x = Game.clamp(x, tempObject.getX() + tempObject.getWidth(), Game.WIDTH + Game.additionalX);
                        }
                    }
                }
        }
    }

    @Override
    public void render(Graphics g) {
        if (alive) {
            if (id == ID.Player1) {
                g.setColor(new Color(0 / 255F, 0 / 255F, 255 / 255F, (float) (opacity)));
            } else {
                g.setColor(Color.green);
            }
            g.fillOval((int) x, (int) y, 16, 16);
        }
    }

    @Override
    public void kill() {
        game.gameState = Game.STATE.End;
        HUD.hasStarted = false;
        HUD.isFinished = true;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    @Override
    public void realRender(Graphics2D g) {

    }


}
