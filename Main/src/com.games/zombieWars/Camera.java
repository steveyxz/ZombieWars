package com.games.zombieWars;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

import java.io.Serializable;

public class Camera implements Serializable {

    private float x, y;
    private Handler handler;

    public Camera(float x, float y, Handler handler) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void tick(GameObject player) {
        x = -player.getX() + Game.WIDTH/2;
        y = -player.getY() + Game.HEIGHT/2;
    }

    private boolean isPlayerAlive() {
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            if (handler.gameObjects.get(i).getId() == ID.Player1) {
                if (handler.gameObjects.get(i).alive) {
                    return true;
                }
            }
        }
        return false;
    }


}
