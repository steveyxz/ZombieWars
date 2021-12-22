package com.games.zombieWars;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class KeyInput extends KeyAdapter implements Serializable {

    private final Handler handler;
    private boolean[] keyDown = new boolean[4];
    private boolean[] keyDown2 = new boolean[4];
    private Game game;
    public static boolean paused = false;
    public static boolean shopOpen = false;

    public KeyInput(Handler handler, Game game) {
        this.handler = handler;
        keyDown[0] = false;
        keyDown[1] = false;
        keyDown[2] = false;
        keyDown[3] = false;
        keyDown2[0] = false;
        keyDown2[1] = false;
        keyDown2[2] = false;
        keyDown2[3] = false;
        this.game = game;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (HUD.hasStarted) {

            if (!paused && !HUD.isMeleeing) {

                for (int i = 0; i < handler.gameObjects.size(); i++) {
                    GameObject tempObject = handler.gameObjects.get(i);

                    if (tempObject.getId() == ID.Player1) {
                        if (key == KeyEvent.VK_W) {
                            tempObject.speedY = -HUD.moveSpeed;
                            keyDown[0] = true;
                        }
                        if (key == KeyEvent.VK_S) {
                            tempObject.speedY = HUD.moveSpeed;
                            keyDown[1] = true;
                        }
                        if (key == KeyEvent.VK_A) {
                            tempObject.speedX = -HUD.moveSpeed;
                            keyDown[2] = true;
                        }
                        if (key == KeyEvent.VK_D) {
                            tempObject.speedX = HUD.moveSpeed;
                            keyDown[3] = true;
                        }
                        if (key == KeyEvent.VK_UP) {
                            tempObject.speedY = -HUD.moveSpeed;
                            keyDown[0] = true;
                        }
                        if (key == KeyEvent.VK_DOWN) {
                            tempObject.speedY = HUD.moveSpeed;
                            keyDown[1] = true;
                        }
                        if (key == KeyEvent.VK_LEFT) {
                            tempObject.speedX = -HUD.moveSpeed;
                            keyDown[2] = true;
                        }
                        if (key == KeyEvent.VK_RIGHT) {
                            tempObject.speedX = HUD.moveSpeed;
                            keyDown[3] = true;
                        }
                    }
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    System.exit(1);
                }
                if (key == KeyEvent.VK_SPACE ) {
                    paused = true;
                }
            } else {
                if (key == KeyEvent.VK_SPACE) {
                    paused = false;
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.gameObjects.size(); i++) {
            GameObject tempObject = handler.gameObjects.get(i);

            if (tempObject.getId() == ID.Player1) {
                if (key == KeyEvent.VK_W) {
                    keyDown[0] = false;
                }
                if (key == KeyEvent.VK_S) {
                    keyDown[1] = false;
                }
                if (key == KeyEvent.VK_A) {
                    keyDown[2] = false;
                }
                if (key == KeyEvent.VK_D) {
                    keyDown[3] = false;
                }

                if (key == KeyEvent.VK_UP) {
                    keyDown[0] = false;
                }
                if (key == KeyEvent.VK_DOWN) {
                    keyDown[1] = false;
                }
                if (key == KeyEvent.VK_LEFT) {
                    keyDown[2] = false;
                }
                if (key == KeyEvent.VK_RIGHT) {
                    keyDown[3] = false;
                }

                if (!keyDown[0] && !keyDown[1]) {
                    tempObject.setVelY(0);
                }
                if (!keyDown[2] && !keyDown[3]) {
                    tempObject.setVelX(0);
                }
            }
        }
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
    }

}
