package com.games.zombieWars;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class ParticleHandler {

    private Game game;

    public ParticleHandler(Game game) {
        this.game = game;
    }

    public LinkedList<GameObject> particles = new LinkedList<GameObject>();

    public void tick() {
        if (game.gameState == Game.STATE.Start) {
            for (int i = 0; i < particles.size(); i++) {
                particles.get(i).tick();
            }
        }
    }

    public void render(Graphics g) {
        if (game.gameState == Game.STATE.Start) {
            for (int i = 0; i < particles.size(); i++) {
                particles.get(i).render(g);
            }
        }
    }

    public void kill() {
        Random r = new Random();
        if (game.gameState == Game.STATE.Start) {
            for (int i = 0; i < particles.size(); i++) {
                if (r.nextInt(2) == 0) {
                    particles.get(i).kill();
                }
            }
        }
    }

    public GameObject addObject(GameObject g) {
        this.particles.add(g);
        return g;
    }

    public GameObject removeObject(GameObject g) {
        this.particles.remove(g);
        return g;
    }


}
