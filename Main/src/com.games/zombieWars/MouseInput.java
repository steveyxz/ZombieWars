package com.games.zombieWars;

import com.games.zombieWars.objects.Bullet;
import com.games.zombieWars.objects.MWeapon;
import com.games.zombieWars.objects.Rocket;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.HashMap;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class MouseInput extends MouseAdapter implements Serializable {

    private Handler handler;
    private GameObject tempObject = null;
    private Camera cam;
    public static HashMap<String, Long> bulletsRemaining = new HashMap<>();
    private Game game;
    private SpriteSheet spriteSheet;

    public MouseInput(Handler handler, Camera cam, Game game, SpriteSheet ss) {
        this.handler = handler;
        this.cam = cam;
        this.game = game;
        this.spriteSheet = ss;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (KeyInput.shopOpen || game.gameState != Game.STATE.Game) {
            return;
        }
        int mx = e.getX();
        int my = e.getY();
        if (e.getButton() == MouseEvent.BUTTON1) {
            shoot(mx, my);
        } else {
            melee(mx, my);
        }
    }

    public void findPlayer() {
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            if (handler.gameObjects.get(i).getId() == ID.Player1) {
                tempObject = handler.gameObjects.get(i);
                break;
            }
        }
    }

    public void findBullet() {
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            if (handler.gameObjects.get(i).getId() == ID.Bullet) {
                tempObject = handler.gameObjects.get(i);
                break;
            }
        }
    }


    private void shoot(int x, int y) {
        if (bulletsRemaining.get(HUD.currentRanged.get("name")) > 0 && game.gameState == Game.STATE.Game && !KeyInput.shopOpen) {
            Sound sound = new Sound(
                    "C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\resources\\Sounds\\pew.wav",
                    "C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\resources\\Sounds\\pew.wav");
            sound.playSound();
            bulletsRemaining.put((String) HUD.currentRanged.get("name"), bulletsRemaining.get(HUD.currentRanged.get("name"))-1);
            findPlayer();
            if (HUD.currentRanged.get("spreadAngle") == null) {
                if ((boolean) HUD.currentRanged.get("isExploding")) {
                    GameObject gameObject = handler.addObject(new Rocket(
                            tempObject.getX(),
                            tempObject.getY(),
                            ID.Bullet,
                            0,
                            0,
                            20,
                            4,
                            4,
                            handler,
                            cam));
                    float diffX = tempObject.getX() - x - 8 + cam.getX();
                    float diffY = tempObject.getY() - y - 8 + cam.getY();
                    float distance = (float) Math.sqrt(((diffX + 8) * (diffX + 8)) + ((diffY + 8) * (diffY + 8)));

                    gameObject.speedX = (float) (((-1.0 / distance) * diffX) * 3) * HUD.bulletSpeed;
                    gameObject.speedY = (float) (((-1.0 / distance) * diffY) * 3) * HUD.bulletSpeed;
                } else {
                    GameObject gameObject = handler.addObject(new Bullet(
                            tempObject.getX(),
                            tempObject.getY(),
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

                    gameObject.speedX = (float) (((-1.0 / distance) * diffX) * 3) * HUD.bulletSpeed;
                    gameObject.speedY = (float) (((-1.0 / distance) * diffY) * 3) * HUD.bulletSpeed;
                }
            } else {
                String toggle = "start";
                int count = 0;
                for (int i = 0; i < (long) HUD.currentRanged.get("bulletsAtOnce"); i++) {
                    if (bulletsRemaining.get(HUD.currentRanged.get("name")) > 0) {
                        if (toggle.equals("start")) {
                            GameObject gameObject = handler.addObject(new Bullet(
                                    tempObject.getX(),
                                    tempObject.getY(),
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

                            gameObject.speedX = (float) (((-1.0 / distance) * diffX) * 3);
                            gameObject.speedY = (float) (((-1.0 / distance) * diffY) * 3);

                            toggle = "left";
                        } else if (toggle.equals("left")) {
                            GameObject gameObject = handler.addObject(new Bullet(
                                    tempObject.getX(),
                                    tempObject.getY(),
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

                            gameObject.speedX = (float) (((float) (((-1.0 / distance) * diffX) * 3)) + Math.toRadians(((long) HUD.currentRanged.get("spreadAngle"))) * count + 1)  * HUD.bulletSpeed;
                            gameObject.speedY = (float) ((float) (((-1.0 / distance) * diffY) * 3) + Math.toRadians(((long) HUD.currentRanged.get("spreadAngle"))) * count + 1)  * HUD.bulletSpeed;

                            toggle = "right";
                        } else if (toggle.equals("right")) {
                            GameObject gameObject = handler.addObject(new Bullet(
                                    tempObject.getX(),
                                    tempObject.getY(),
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

                            gameObject.speedX = (float) (((float) (((-1.0 / distance) * diffX) * 3)) - Math.toRadians(((long) HUD.currentRanged.get("spreadAngle"))) * count + 1)  * HUD.bulletSpeed;
                            gameObject.speedY = (float) ((float) (((-1.0 / distance) * diffY) * 3) - Math.toRadians(((long) HUD.currentRanged.get("spreadAngle"))) * count + 1) * HUD.bulletSpeed;

                            toggle = "left";
                            count++;
                        }
                        bulletsRemaining.put((String) HUD.currentRanged.get("name"), bulletsRemaining.get(HUD.currentRanged.get("name"))-1);
                    }
                }

            }
        }
    }

    private void melee(int x, int y) {
        if (HUD.currentMelee.get("name").equals("Fists")) {
            Sound sound = new Sound(
                    "C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\resources\\Sounds\\punch.wav",
                    "C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\resources\\Sounds\\punch.wav");
            sound.playSound();
        } else {
            Sound sound = new Sound(
                    "C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\resources\\Sounds\\meleeOther.wav",
                    "C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\resources\\Sounds\\meleeOther.wav");
            sound.playSound();
        }
        findPlayer();
        if (game.gameState == Game.STATE.Game) {
            findPlayer();
            GameObject gameObject = handler.addObject(new MWeapon(
                    tempObject.getX(),
                    tempObject.getY(),
                    ID.MWeapon,
                    0,
                    0,
                    20,
                    20,
                    20,
                    handler,
                    spriteSheet,
                    cam));
            float diffX = tempObject.getX() - x - 8 + cam.getX();
            float diffY = tempObject.getY() - y - 8 + cam.getY();
            float distance = (float) Math.sqrt(((diffX + 8) * (diffX + 8)) + ((diffY + 8) * (diffY + 8)));

            gameObject.speedX = (float) (((-1.0 / distance) * diffX) * 3);
            gameObject.speedY = (float) (((-1.0 / distance) * diffY) * 3);
        }
    }

}
