package com.games.zombieWars.screens;

import com.games.zombieWars.*;
import com.games.zombieWars.objects.Border;
import com.games.zombieWars.objects.Player;
import com.games.zombieWars.objects.StartScreenParticle;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Random;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class StartScreen extends MouseAdapter implements Serializable {

    private Game game;
    private Handler handler;
    private ParticleHandler particleHandler;
    private int count = 0;
    private int secs = 0;
    private KeyInput keyInput;

    public StartScreen(Game game, Handler handler) {
        this.game = game;
        this.handler = handler;

        keyInput = new KeyInput(handler, game);
        particleHandler = new ParticleHandler(game);
    }

    public void updateKeyInput() {
        Game.saverKeyInput = keyInput;
    }

    public void tick() {
        particleHandler.tick();
        Random r = new Random();
        if (count % 3 == 0) {
            if (r.nextInt(2) == 0) {
                int xLocation = r.nextInt(Game.WIDTH);
                int speedX = r.nextInt(4);
                speedX = (int) Game.clamp((float) speedX, 1.0F, 4.0F);
                int speedY = r.nextInt(10);
                speedY = (int) Game.clamp((float) speedY, 1.0F, 10.0F);
                boolean isNeg = r.nextBoolean();
                if (isNeg) {
                    speedX *= -1;
                }
                particleHandler.addObject(new StartScreenParticle(xLocation, 0, ID.StartScreenParticle, speedX, speedY, 10,8, 16,  particleHandler));
            } else {
                int xLocation = r.nextInt(Game.WIDTH);
                int speedX = r.nextInt(4);
                speedX = (int) Game.clamp((float) speedX, 1.0F, 4.0F);
                int speedY = r.nextInt(10);
                speedY = (int) -Game.clamp((float) speedY, 1.0F, 10.0F);
                boolean isNeg = r.nextBoolean();
                if (isNeg) {
                    speedX *= -1;
                }
                particleHandler.addObject(new StartScreenParticle(xLocation, Game.HEIGHT, ID.StartScreenParticle, speedX, speedY, 10, 8, 16, particleHandler));
            }
        }

        if (secs == 3) {
            particleHandler.kill();
            secs = 0;
        }

        count++;
        if (count%60 == 0) {
            secs++;
            count = 0;
        }
    }

    public void render(Graphics g) {
        particleHandler.render(g);

        g.setColor(Color.red);
        g.fillRect(Game.WIDTH / 2 - 80, 200, 150, 60);

        g.setColor(Color.blue);
        g.fillRect(Game.WIDTH / 2 - 80, 300, 150, 60);

        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, 40));
        g.drawString("Zombie WARS", Game.WIDTH / 2 - 130, 60);

        g.setColor(Color.yellow);
        g.setFont(new Font("Nunito", Font.BOLD, 30));
        g.drawString("PLAY", Game.WIDTH / 2 - 50, 240);
        g.drawString("HELP", Game.WIDTH / 2 - 50, 340);
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mouseOver(mx, my, Game.WIDTH / 2 - 80, 200, 150, 60) && !HUD.hasStarted) {
            game.gameState = Game.STATE.Game;
            handler.addObject(new Player(100, 100, ID.Player1, 0, 0, 100, 16, 16, handler, game));
            handler.addObject(new Border(-120, -120, ID.Border, 0, 0, 10000, Game.WIDTH + 240, Game.HEIGHT + 240));
            game.addKeyListener(keyInput);
            HUD.hasStarted = true;
            HUD.unlockedRanged.add(HUD.rangedJson);
            HUD.unlockedMelee.add(HUD.meleeJson);
        }
        if (mouseOver(mx, my, Game.WIDTH / 2 - 80, 300, 150, 60) && !HUD.hasStarted) {
            game.gameState = Game.STATE.Help;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}
