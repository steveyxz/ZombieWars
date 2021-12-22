package com.games.zombieWars;

import com.games.zombieWars.screens.ShopScreenPowerups;
import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.LinkedList;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class HUD extends MouseAdapter implements Serializable {

    private Handler handler;
    private Game game;
    public static float divisor = 5;

    public HUD(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;
    }

    public static int PLAYER_1_HP = 100;
    public static int LEVEL = 0;
    public static boolean hasStarted = false;
    private int times = 0;
    private boolean allDead = false;
    public static int zombieCount = 0;
    public static boolean isFinished = false;
    public static int coins = 0;
    public static boolean isMeleeing = false;
    public static int maxAmmo = 20;
    public static LinkedList<JSONObject> unlockedRanged = new LinkedList<>();
    public static LinkedList<JSONObject> unlockedMelee = new LinkedList<>();
    public static JSONObject rangedJson = ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged\\pistol.json");
    public static JSONObject meleeJson = ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\melee\\fists.json");
    public static JSONObject currentRanged = rangedJson;
    public static JSONObject currentMelee = meleeJson;
    public static String shopState;
    public static int moveSpeed = 3;
    public static float bulletSpeed = 1;

    public void tick() {
        if (Spawn.timer < 1) {
            Spawn.isTimerFinished = true;
            if (Spawn.count%60 == 0) {
                PLAYER_1_HP -= 10;
                if (PLAYER_1_HP < 1) {
                    for (int i = 0; i < handler.gameObjects.size(); i++) {
                        if (handler.gameObjects.get(i).getId() == ID.Player1) {
                            handler.gameObjects.get(i).kill();
                        }
                    }
                }
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("monospace", Font.BOLD, 20));
        g.drawString("FPS: " + Game.FPS, Game.WIDTH - 110, Game.HEIGHT - 60);
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            if (handler.gameObjects.get(i).getId() == ID.Player1) {
                g.setColor(Color.lightGray);
                g.fillRect(20, 20, 200, 32);
                g.setColor(new Color(0, 190, 220));
                g.fillRect(20, 20, PLAYER_1_HP * 2, 32);
                g.setFont(new Font("Nunito", Font.PLAIN, 20));
                g.setColor(Color.WHITE);
                g.drawString("Wave: " + LEVEL, Game.WIDTH / 2 - 40, 20);
                if (!Spawn.isTimerFinished) {
                    g.drawString("Time Remaining: " + Spawn.timer, Game.WIDTH / 2 - 40, 50);
                    g.drawString("Zombies Remaining: " + zombieCount, Game.WIDTH / 2 - 40, 80);
                    g.drawString("Bullets Remaining: " + MouseInput.bulletsRemaining.get(HUD.currentRanged.get("name")), Game.WIDTH / 2 - 40, 110);
                } else {
                    g.drawString("Zombies Remaining: " + zombieCount, Game.WIDTH / 2 - 40, 50);
                    g.drawString("Bullets Remaining: " + MouseInput.bulletsRemaining.get(HUD.currentRanged.get("name")), Game.WIDTH / 2 - 40, 80);
                }
                g.fillOval(Game.WIDTH - 90, 20, 40 ,40);
                g.setColor(Color.blue);
                g.setFont(new Font("Nunito", Font.PLAIN, 11));
                g.drawString("SHOP", Game.WIDTH - 85, 43);
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.setColor(Color.red);
                g.drawString("Current ranged: " + HUD.currentRanged.get("name"), 50, Game.HEIGHT - 70);
                g.drawString("Current melee: " + HUD.currentMelee.get("name"), 50, Game.HEIGHT - 50);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mouseOver(mx, my, Game.WIDTH - 90, 20, 40 ,40) && HUD.hasStarted) {
            game.gameState = Game.STATE.MeleeScreen;
            KeyInput.shopOpen = true;
            shopState = "melee";
        }
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            }
        }
        return false;
    }
}
