package com.games.zombieWars.screens;

import com.games.zombieWars.Game;
import com.games.zombieWars.HUD;
import com.games.zombieWars.Handler;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class HelpMenu extends MouseAdapter {

    private Game game;
    private Handler handler;

    public HelpMenu(Game game, Handler handler) {
        this.handler = handler;
        this.game = game;
    }

    public void render(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(10, 10, Game.WIDTH-35, Game.HEIGHT-58);
        g.setFont(new Font("Raleway", Font.BOLD, 40));
        g.setColor(Color.red);
        g.drawString("HELP", Game.WIDTH/2 - 50, 60);
        g.setColor(Color.magenta);
        g.setFont(new Font("arial", Font.PLAIN, 20));
        g.drawString("You can use WASD or Arrow keys to move.", Game.WIDTH/ 2 - 300, 120);
        g.drawString("Left click the direction you want to shoot to shoot", Game.WIDTH/ 2 - 300, 150);
        g.drawString("Right click the direction you want to melee to melee", Game.WIDTH/ 2 - 300, 180);
        g.drawString("You can only shoot when you have ammo.", Game.WIDTH/ 2 - 300, 210);
        g.drawString("To get to the next wave you must kill all the zombies", Game.WIDTH/ 2 - 300, 240);
        g.drawString("Buy new melee and ranged weapons in the shop!", Game.WIDTH/ 2 - 300, 270);
        g.drawString("Space is pause.", Game.WIDTH / 2 - 300, 300);
        g.drawString("White zombies are weak, yellow ones are medium, gray ones", Game.WIDTH/ 2 - 300, 330);
        g.drawString("are strong, magenta (darker pink) zombies are fast and pink", Game.WIDTH/ 2 - 300, 360);
        g.drawString("ones are tank (lots of health). Good luck!!", Game.WIDTH/ 2 - 300, 390);
        g.setColor(Color.CYAN);
        g.fillRect(Game.WIDTH/ 2 - 130, 400, 80, 40);
        g.setColor(Color.red);
        g.drawString("BACK", Game.WIDTH/ 2 - 120, 425);


    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            }
        }
        return false;
    }

    public void mousePressed(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();

        if (mouseOver(mx, my, Game.WIDTH/ 2 - 130, 400, 80, 40) && !HUD.hasStarted) {
            game.gameState = Game.STATE.Start;
        }

    }

    public void mouseReleased(MouseEvent e) {

    }
}
