package com.games.zombieWars.screens;

import com.games.zombieWars.*;
import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/*
* Copying this class in any way is an infraction of the law, and will be punished.
* All content of this class is protected by copyright
* © 2020 Steven Zhu - All Rights Reserved
 */


@SuppressWarnings("unchecked")
public class RangedPowerups extends MouseAdapter {

    private Game game;
    private Handler handler;

    private boolean hovering = false;
    private String currentBox = "";
    private String subtext = "";
    private Color sTextColor = Color.BLACK;

    public RangedPowerups(Game game, Handler handler) {
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
        g.setColor(new Color(0, 0, 0, 50));
        g.fillRect(155, 80, 80, 30);
        g.setColor(Color.black);
        g.fillRect(250, 80, 80, 30);
        g.setFont(new Font("Raleway", Font.PLAIN, 20));
        g.setColor(Color.cyan);
        g.drawString("Melee", 60, 100);
        g.drawString("Ranged", 160, 100);
        g.drawString("Boosts", 260, 100);
        g.setColor(Color.BLACK);
        g.drawString("Coins: " + HUD.coins, 400, 100);
        g.fillRect(50, 140, 80, 80);
        JSONObject tempJson = ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged\\pistol.json");
        assert tempJson != null;
        drawNewRangedItem(
                tempJson,
                g,
                50,
                140,
                (String) tempJson.get("name"),
                (long) tempJson.get("cost"),
                (long) tempJson.get("damage"),
                (long) tempJson.get("ammo"),
                "Gun #1");
        tempJson = ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged\\magnum.json");
        assert tempJson != null;
        drawNewRangedItem(
                tempJson,
                g,
                180,
                140,
                (String) tempJson.get("name"),
                (long) tempJson.get("cost"),
                (long) tempJson.get("damage"),
                (long) tempJson.get("ammo"),
                "Better than pistol!");
        tempJson = ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged\\basicShotgun.json");
        assert tempJson != null;
        drawNewRangedItem(
                tempJson,
                g,
                310,
                140,
                (String) tempJson.get("name"),
                (long) tempJson.get("cost"),
                (long) tempJson.get("damage"),
                (long) tempJson.get("ammo"),
                "5 at once!");
        tempJson = ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged\\rocketLauncher.json");
        assert tempJson != null;
        drawNewRangedItem(
                tempJson,
                g,
                440,
                140,
                (String) tempJson.get("name"),
                (long) tempJson.get("cost"),
                (long) tempJson.get("damage"),
                (long) tempJson.get("ammo"),
                "BOOM BOOM BOOM!!!");
        tempJson = ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged\\nukeLauncher.json");
        assert tempJson != null;
        drawNewRangedItem(
                tempJson,
                g,
                50,
                270,
                (String) tempJson.get("name"),
                (long) tempJson.get("cost"),
                (long) tempJson.get("damage"),
                (long) tempJson.get("ammo"),
                "Zombie be ded though");
        g.setFont(new Font("Raleway", Font.BOLD, 15));
        g.setColor(sTextColor);
        g.drawString(subtext, 170, 400);
    }

    private void drawNewRangedItem(JSONObject info, Graphics g, int x, int y, String name, long cost, long damage, long ammo, String des) {
        boolean locked = false;
        boolean isCurrent = false;
        for (int i = 0; i < Game.allRanged.size(); i++) {
            JSONObject object = Game.allRanged.get(i);
            if (object.get("name").equals(name) && object.get("state").equals("locked")) {
                locked = true;
            } else if (object.get("name").equals(name) && object.get("state").equals("current")){
                isCurrent = true;
            }
        }
        if (!locked) {
            g.setColor(Color.blue);
        } else {
            g.setColor(new Color(0, 0, 0, 50));
        }
        g.fillRoundRect(x, y, 100, 100, 15, 15);
        g.setColor(Color.white);
        g.setFont(new Font("Raleway", Font.BOLD, 15));
        g.drawString(name, x, y + 23);
        g.setFont(new Font("Raleway", Font.BOLD, 13));
        g.drawString("Cost: " + cost, x + 10, y + 40);
        g.drawString("Damage: " + damage, x + 10, y + 57);
        if (!locked) {
            g.drawString("Ammo Left: " + MouseInput.bulletsRemaining.get(info.get("name")), x + 10, y + 74);
        } else {
            g.drawString("Ammo: " + ammo, x + 10, y + 74);
        }
        g.setFont(new Font("arial", Font.PLAIN, 10));
        g.drawString(des, x, y + 94);
        g.setColor(Color.red);
        if (isCurrent) {
            for (int i = 0; i < 10; i++) {
                g.drawRoundRect(x, y, 100, 100, 15, 15);
            }
        }
    }

    public void tick() {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mouseOver(mx, my, Game.WIDTH - 100, 60, 30, 30) && KeyInput.shopOpen) {
            KeyInput.shopOpen = false;
            game.gameState = Game.STATE.Game;
        }
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
        if (mouseOver(mx, my, 50, 140, 100, 100) && KeyInput.shopOpen && HUD.shopState.equals("ranged")) {
            JSONObject tempJson = ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged\\pistol.json");
            weaponAction(tempJson);
        }
        if (mouseOver(mx, my, 180, 140, 100, 100) && KeyInput.shopOpen && HUD.shopState.equals("ranged")) {
            JSONObject tempJson = ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged\\magnum.json");
            weaponAction(tempJson);
        }
        if (mouseOver(mx, my, 310, 140, 100, 100) && KeyInput.shopOpen && HUD.shopState.equals("ranged")) {
            JSONObject tempJson = ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged\\basicShotgun.json");
            weaponAction(tempJson);
        }
        if (mouseOver(mx, my, 440, 140, 100, 100) && KeyInput.shopOpen && HUD.shopState.equals("ranged")) {
            JSONObject tempJson = ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged\\rocketLauncher.json");
            weaponAction(tempJson);
        }
        if (mouseOver(mx, my, 50, 270, 100, 100) && KeyInput.shopOpen && HUD.shopState.equals("ranged")) {
            JSONObject tempJson = ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged\\nukeLauncher.json");
            weaponAction(tempJson);
        }
    }

    private void weaponAction(JSONObject tempJson) {
        boolean isCurrent = true;
        for (JSONObject json : Game.allRanged) {
            if (tempJson.get("name").equals(json.get("name")) && !json.get("state").equals("current")) {
                isCurrent = false;
            }
        }
        boolean unlocked = false;
        for (JSONObject json : HUD.unlockedRanged) {
            if (tempJson.get("name").equals(json.get("name"))) {
                unlocked = true;
            }
        }
        if (!isCurrent) {
            if (unlocked) {
                HUD.maxAmmo = 0;
                HUD.maxAmmo += (long) tempJson.get("ammo");
                HUD.currentRanged.remove("state");
                HUD.currentRanged.put("state", "unlocked");
                tempJson.remove("state");
                tempJson.put("state", "current");
                for (int i = 0; i < Game.allRanged.size(); i++) {
                    if (Game.allRanged.get(i).get("name").equals(tempJson.get("name"))) {
                        Game.allRanged.get(i).remove("state");
                        Game.allRanged.get(i).put("state", "current");
                    } else if (Game.allRanged.get(i).get("name").equals(HUD.currentRanged.get("name"))) {
                        Game.allRanged.get(i).remove("state");
                        Game.allRanged.get(i).put("state", "unlocked");
                    }
                }
                HUD.currentRanged = tempJson;
            } else {
                if (HUD.coins >= (long) tempJson.get("cost")) {
                    HUD.coins -= (long) tempJson.get("cost");
                    HUD.currentRanged.remove("state");
                    HUD.currentRanged.put("state", "unlocked");
                    HUD.maxAmmo = 0;
                    HUD.maxAmmo += (long) tempJson.get("ammo");
                    MouseInput.bulletsRemaining.put((String) tempJson.get("name"), (long) HUD.maxAmmo);
                    HUD.unlockedRanged.add(tempJson);
                    tempJson.remove("state");
                    tempJson.put("state", "current");
                    for (int i = 0; i < Game.allRanged.size(); i++) {
                        if (Game.allRanged.get(i).get("name").equals(tempJson.get("name"))) {
                            Game.allRanged.get(i).remove("state");
                            Game.allRanged.get(i).put("state", "current");
                        } else if (Game.allRanged.get(i).get("name").equals(HUD.currentRanged.get("name"))) {
                            Game.allRanged.get(i).remove("state");
                            Game.allRanged.get(i).put("state", "unlocked");
                        }
                    }
                    HUD.currentRanged = tempJson;
                    updateSubText("success");
                } else {
                    updateSubText("failure");
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
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
