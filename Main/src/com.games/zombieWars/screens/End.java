package com.games.zombieWars.screens;

import com.games.zombieWars.Game;
import com.games.zombieWars.HUD;
import com.games.zombieWars.Handler;
import com.games.zombieWars.Spawn;
import org.apache.commons.io.FileUtils;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Iterator;

/*
 * Copying this class in any way is not allowed.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class End extends MouseAdapter {

    private Game game;
    private Handler handler;

    public End(Game game, Handler handler) {
        this.handler = handler;
        this.game = game;
    }

    public void render(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(10, 10, Game.WIDTH-35, Game.HEIGHT-58);
        g.setFont(new Font("Raleway", Font.BOLD, 80));
        g.setColor(Color.BLACK);
        g.drawString("GAME OVER", Game.WIDTH/2 - 250, 200);
        g.fillRect(Game.WIDTH/2 - 50, 300, 100, 40);
        g.setFont(new Font("Raleway", Font.PLAIN, 15));
        g.setColor(Color.green);
        g.drawString("Play Again", Game.WIDTH/2 - 36, 323);
    }

    public void tick() {

    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mouseOver(mx, my, Game.WIDTH/2 - 50, 300, 100, 40) && HUD.isFinished) {
            game.gameState = Game.STATE.Start;
            HUD.PLAYER_1_HP = 100;
            HUD.zombieCount = 0;
            HUD.LEVEL = 0;
            HUD.divisor = 5;
            Spawn.isTimerFinished = false;
            Spawn.timer = 30;
            HUD.isFinished = false;
            HUD.coins = 0;
            HUD.maxAmmo = 20;
            HUD.unlockedMelee.clear();
            HUD.unlockedRanged.clear();
            HUD.unlockedRanged.add(HUD.rangedJson);
            HUD.unlockedMelee.add(HUD.meleeJson);
            HUD.currentMelee = HUD.meleeJson;
            HUD.currentRanged = HUD.rangedJson;

            Game.allRanged.clear();
            Game.allMelee.clear();

            JSONParser parser = new JSONParser();

            Iterator it = FileUtils.iterateFiles(new File("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\melee"), null, false);
            while(it.hasNext()){
                Game.allMelee.add(ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\melee\\" + ((File) it.next()).getName()));
            }

            it = FileUtils.iterateFiles(new File("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged"), null, false);
            while(it.hasNext()){
                Game.allRanged.add(ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged\\" + ((File) it.next()).getName()));
            }
            handler.clearObjects();
        }
    }

    public void mouseReleased(MouseEvent e) {

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
