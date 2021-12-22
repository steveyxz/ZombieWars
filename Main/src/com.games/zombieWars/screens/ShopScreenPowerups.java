package com.games.zombieWars.screens;

import com.games.zombieWars.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.IOException;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class ShopScreenPowerups extends MouseAdapter {

    private Game game;
    private Handler handler;
    private String subtext = "";
    private Color sTextColor = Color.BLACK;

    public ShopScreenPowerups(Game game, Handler handler) {
        this.handler = handler;
        this.game = game;
    }

    public void render(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(10, 10, Game.WIDTH-35, Game.HEIGHT-58);
        g.setFont(new Font("Raleway", Font.BOLD, 40));
        g.setColor(Color.BLACK);
        g.drawString("SHOP", Game.WIDTH/2 - 50, 60);
        g.fillRect(Game.WIDTH - 100, 60, 30, 30);
        g.setFont(new Font("Raleway", Font.BOLD, 10));
        g.setColor(Color.red);
        g.drawString("EXIT", Game.WIDTH - 96, 77);
        g.setColor(Color.white);
        g.drawRect(50, 80, 80, 30);
        g.drawRect(155, 80, 80, 30);
        g.drawRect(250, 80, 80, 30);
        g.setColor(Color.black);
        g.fillRect(50, 80, 80, 30);
        g.fillRect(155, 80, 80, 30);
        g.setColor(new Color(0, 0, 0, 50));
        g.fillRect(250, 80, 80, 30);
        g.setFont(new Font("Raleway", Font.PLAIN, 20));
        g.setColor(Color.cyan);
        g.drawString("Melee", 60, 100);
        g.drawString("Ranged", 160, 100);
        g.drawString("Boosts", 260, 100);
        g.setColor(Color.BLACK);
        g.drawString("Coins: " + HUD.coins, 400, 100);
        JSONObject tempJson = openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\boosts\\heal.json");
        drawNewBoostItem(g, 50, 140, (String) tempJson.get("name"), (long) tempJson.get("cost"), "+ " + tempJson.get("value") + " HP");
        tempJson = openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\boosts\\refillammo.json");
        drawNewBoostItem(g, 150, 140, (String) tempJson.get("name"), (long) tempJson.get("cost"), "Refill Ammo");
        tempJson = openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\boosts\\fullheal.json");
        drawNewBoostItem(g, 250, 140, (String) tempJson.get("name"), (long) tempJson.get("cost"), "+ 100 HP");
        int regenIndex = 0;
        int moveSpeedIndex = 0;
        int bulletSpeedIndex = 0;
        for (int i = 0; i < Game.allStackPowers.size(); i++) {
            if (Game.allStackPowers.get(i).get("id").equals("regenStack")) {
                regenIndex = i;
            }
            if (Game.allStackPowers.get(i).get("id").equals("moveSpeedStack")) {
                moveSpeedIndex = i;
            }
            if (Game.allStackPowers.get(i).get("id").equals("bulletSpeedStack")) {
                bulletSpeedIndex = i;
            }
        }
        tempJson = Game.allStackPowers.get(regenIndex);
        drawNewStackBoostItem(g, 350, 140, (String) tempJson.get("name"), (long) tempJson.get("cost"), "Better regen", (long) tempJson.get("stage"), (long) tempJson.get("maxStage"));
        tempJson = Game.allStackPowers.get(moveSpeedIndex);
        drawNewStackBoostItem(g, 450, 140, (String) tempJson.get("name"), (long) tempJson.get("cost"), "SPEEEEEED", (long) tempJson.get("stage"), (long) tempJson.get("maxStage"));
        tempJson = Game.allStackPowers.get(bulletSpeedIndex);
        drawNewStackBoostItem(g, 50, 240, (String) tempJson.get("name"), (long) tempJson.get("cost"), "Zoooom", (long) tempJson.get("stage"), (long) tempJson.get("maxStage"));
        g.setFont(new Font("Raleway", Font.BOLD, 15));
        g.setColor(sTextColor);
        g.drawString(subtext, 170, 400);
    }

    private void updateSubText(String state) {
        if (state.equals("success")) {
            subtext = "Successfully bought your item!";
            sTextColor = Color.green;
        } else if (state.equals("failure")) {
            subtext = "You're too poor";
            sTextColor = Color.RED;
        }
    }

    private void drawNewBoostItem(Graphics g, int x, int y, String name, long cost, String des) {
        g.setColor(Color.black);
        g.fillRect(x, y, 80, 80);
        g.setColor(Color.white);
        g.setFont(new Font("Raleway", Font.BOLD, 15));
        g.drawString(name, x, y + 23);
        g.setFont(new Font("Raleway", Font.BOLD, 13));
        g.drawString("Cost: " + cost, x + 10, y + 45);
        g.drawString(des, x + 10, y + 70);
    }

    private void drawNewStackBoostItem(Graphics g, int x, int y, String name, long cost, String des, long stage, long maxStage) {
        g.setColor(Color.black);
        g.fillRect(x, y, 80, 80);
        g.setColor(Color.white);
        g.setFont(new Font("Raleway", Font.BOLD, 15));
        g.drawString(name, x, y + 15);
        g.setFont(new Font("Raleway", Font.BOLD, 13));
        g.drawString("Cost: " + cost, x + 10, y + 34);
        g.drawString(des, x + 10, y + 50);
        long boxLen = 60 / (maxStage);
        int count = 1;
        g.setColor(Color.GREEN);
        for (int i = 0; i < maxStage + 1; i++) {
            if (count > stage) {
                g.setColor(Color.GRAY);
            }
            g.fillRect((int) (x + (boxLen * count) - 12), y + 60, (int) boxLen, 15);
            count++;
        }
    }



    public static JSONObject openJSONFile(String fileName) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(fileName));
            return jsonObject;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void tick() {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mouseOver(mx, my, Game.WIDTH - 100, 60, 30, 30) && KeyInput.shopOpen) {
            game.gameState = Game.STATE.Game;
            KeyInput.shopOpen = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mouseOver(mx, my, 50, 80, 80, 30) && KeyInput.shopOpen) {
            game.gameState = Game.STATE.MeleeScreen;
            HUD.shopState = "melee";
        }
        if (mouseOver(mx, my, 155, 80, 80, 30) && KeyInput.shopOpen) {
            game.gameState = Game.STATE.RangedScreen;
            HUD.shopState = "ranged";
        }
        if (mouseOver(mx, my, 250, 80, 80, 30) && KeyInput.shopOpen) {
            game.gameState = Game.STATE.PowerupsScreen;
            HUD.shopState = "boosts";
        }
        if (mouseOver(mx, my, 50, 140, 80, 80) && KeyInput.shopOpen && HUD.shopState.equals("boosts")) {
            JSONObject tempJson = openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\boosts\\heal.json");
            if (HUD.coins >= (long) tempJson.get("cost")) {
                HUD.coins -= (long) tempJson.get("cost");
                HUD.PLAYER_1_HP += (long) tempJson.get("value");
                HUD.PLAYER_1_HP = (int) Game.clamp(HUD.PLAYER_1_HP, 0, 100);
                updateSubText("success");
            } else {
                updateSubText("failure");
            }
        }
        if (mouseOver(mx, my, 150, 140, 80, 80) && KeyInput.shopOpen && HUD.shopState.equals("boosts")) {
            JSONObject tempJson = openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\boosts\\refillammo.json");
            if (HUD.coins >= (long) tempJson.get("cost")) {
                HUD.coins -= (long) tempJson.get("cost");
                MouseInput.bulletsRemaining.put((String) HUD.currentRanged.get("name"), (long) HUD.maxAmmo);
                System.out.println(HUD.maxAmmo);
                updateSubText("success");
            } else {
                updateSubText("failure");
            }
        }
        if (mouseOver(mx, my, 350, 140, 80, 80) && KeyInput.shopOpen && HUD.shopState.equals("boosts")) {
            int index = 0;
            for (int i = 0; i < Game.allStackPowers.size(); i++) {
                if (Game.allStackPowers.get(i).get("id").equals("regenStack")) {
                    index = i;
                    break;
                }
            }
            JSONObject tempJson = Game.allStackPowers.get(index);
            if ((long) tempJson.get("stage") <= (long) tempJson.get("maxStage")) {
                if (HUD.coins >= (long) tempJson.get("cost")) {
                    HUD.coins -= (long) tempJson.get("cost");
                    tempJson.put("stage", ((long) tempJson.get("stage")) + 1);
                    tempJson.put("cost", ((long) tempJson.get("cost"))*(long) tempJson.get("multiplier"));
                    tempJson.put("timeDelay", (long) tempJson.get("timeDelay") - (long) tempJson.get("stage"));
                    for (int i = 0; i < Game.allStackPowers.size(); i++) {
                        if (Game.allStackPowers.get(i).get("id").equals("regenStack")) {
                            Game.allStackPowers.set(i, tempJson);
                        }
                    }
                    updateSubText("success");
                } else {
                    updateSubText("failure");
                }
            } else {
                subtext = "You have reached max level for this upgrade";
                sTextColor = Color.blue;
            }
        }
        if (mouseOver(mx, my, 450, 140, 80, 80) && KeyInput.shopOpen && HUD.shopState.equals("boosts")) {
            int index = 0;
            for (int i = 0; i < Game.allStackPowers.size(); i++) {
                if (Game.allStackPowers.get(i).get("id").equals("moveSpeedStack")) {
                    index = i;
                    break;
                }
            }
            JSONObject tempJson = Game.allStackPowers.get(index);
            if ((long) tempJson.get("stage") <= (long) tempJson.get("maxStage")) {
                if (HUD.coins >= (long) tempJson.get("cost")) {
                    HUD.coins -= (long) tempJson.get("cost");
                    tempJson.put("stage", ((long) tempJson.get("stage")) + 1);
                    tempJson.put("cost", ((Double)(((long) tempJson.get("cost"))*(double) tempJson.get("multiplier"))).longValue());
                    HUD.moveSpeed++;
                    for (int i = 0; i < Game.allStackPowers.size(); i++) {
                        if (Game.allStackPowers.get(i).get("id").equals("moveSpeedStack")) {
                            Game.allStackPowers.set(i, tempJson);
                        }
                    }
                    updateSubText("success");
                } else {
                    updateSubText("failure");
                }
            } else {
                subtext = "You have reached max level for this upgrade";
                sTextColor = Color.blue;
            }
        }
        if (mouseOver(mx, my, 50, 240, 80, 80) && KeyInput.shopOpen && HUD.shopState.equals("boosts")) {
            int index = 0;
            for (int i = 0; i < Game.allStackPowers.size(); i++) {
                if (Game.allStackPowers.get(i).get("id").equals("bulletSpeedStack")) {
                    index = i;
                    break;
                }
            }
            JSONObject tempJson = Game.allStackPowers.get(index);
            if ((long) tempJson.get("stage") <= (long) tempJson.get("maxStage")) {
                if (HUD.coins >= (long) tempJson.get("cost")) {
                    HUD.coins -= (long) tempJson.get("cost");
                    tempJson.put("stage", ((long) tempJson.get("stage")) + 1);
                    tempJson.put("cost", ((Double)(((long) tempJson.get("cost"))*(double) tempJson.get("multiplier"))).longValue());
                    HUD.bulletSpeed += 0.3;
                    for (int i = 0; i < Game.allStackPowers.size(); i++) {
                        if (Game.allStackPowers.get(i).get("id").equals("bulletSpeedStack")) {
                            Game.allStackPowers.set(i, tempJson);
                        }
                    }
                    updateSubText("success");
                } else {
                    updateSubText("failure");
                }
            } else {
                subtext = "You have reached max level for this upgrade";
                sTextColor = Color.blue;
            }
        }
        if (mouseOver(mx, my, 250, 140, 80, 80) && KeyInput.shopOpen && HUD.shopState.equals("boosts")) {
            JSONObject tempJson = openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\boosts\\fullheal.json");
            if (HUD.coins >= (long) tempJson.get("cost")) {
                HUD.coins -= (long) tempJson.get("cost");
                HUD.PLAYER_1_HP += (long) tempJson.get("value");
                HUD.PLAYER_1_HP = (int) Game.clamp(HUD.PLAYER_1_HP, 0, 100);
                updateSubText("success");
            } else {
                updateSubText("failure");
            }
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
