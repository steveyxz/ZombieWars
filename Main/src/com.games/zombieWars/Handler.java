package com.games.zombieWars;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class Handler implements Serializable {

    private Game game;

    public Handler(Game game) {
        this.game = game;
    }

    public LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();

    public void tick() {
        if (game.gameState == Game.STATE.Game) {
            for (int i = 0; i < gameObjects.size(); i++) {
                gameObjects.get(i).tick();
            }
        }
    }

    public void render(Graphics g) {
        if (game.gameState == Game.STATE.Game) {
            for (int i = 0; i < gameObjects.size(); i++) {
                if (gameObjects.get(i).getId() == ID.MWeapon) {
                    gameObjects.get(i).realRender((Graphics2D) g);
                } else {
                    gameObjects.get(i).render(g);
                }
            }
        }
    }

    public void clearObjects() {
        gameObjects.clear();
    }

    public GameObject addObject(GameObject g) {
        this.gameObjects.add(g);
        return g;
    }

    public GameObject removeObject(GameObject g) {
        this.gameObjects.remove(g);
        return g;
    }

    public LinkedList<GameObject> getObjects(ID id) {
        LinkedList<GameObject> list = new LinkedList<>();
        for (int i = 0; i < this.gameObjects.size(); i++) {
            if (gameObjects.get(i).getId() == id) {
                list.add(gameObjects.get(i));
            }
        }
        return list;
    }


}
