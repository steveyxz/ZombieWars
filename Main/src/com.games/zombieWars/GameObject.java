package com.games.zombieWars;

import java.awt.*;
import java.io.Serializable;

public abstract class GameObject implements Serializable {

    protected float x, y;
    protected ID id;
    protected float speedX, speedY;
    public boolean alive = true;
    protected int hp;
    protected int width;
    protected int height;

    public GameObject(float x, float y, ID id, float speedX, float speedY, int hp, int width, int height) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.speedX = speedX;
        this.speedY = speedY;
        this.hp = hp;
        this.width = width;
        this.height = height;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract void kill();

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setVelX(float vel) {
        this.speedX = vel;
    }

    public float getVelX() {
        return speedX;
    }

    public void setVelY(float vel) {
        this.speedY = vel;
    }

    public float getVelY() {
        return speedY;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public abstract void realRender(Graphics2D g);

    public Rectangle getBounds() {
        if (alive) {
            return new Rectangle((int) x, (int) y, width, height);
        }
        return new Rectangle(0, 0);
    }
    public Rectangle getBoundsBottom() {
        if (alive) {
            return new Rectangle((int) x + 5, (int) y + (height/8 * 7), width - 10, height/8);
        }
        return new Rectangle(0, 0);
    }
    public Rectangle getBoundsRight() {
        if (alive) {
            return new Rectangle((int) x + width-5, (int) y + 4, 5, height - 8);
        }
        return new Rectangle(0, 0);
    }
    public Rectangle getBoundsLeft() {
        if (alive) {
            return new Rectangle((int) x, (int) y + 4, 5, height - 8);
        }
        return new Rectangle(0, 0);
    }
    public Rectangle getBoundsTop() {
        if (alive) {
            return new Rectangle((int) x + 5, (int) y, width - 10, height/8);
        }
        return new Rectangle(0, 0);
    }

    public Rectangle getBoundsMiddle() {
        if (alive) {
            return new Rectangle((int) x + 10, (int) y + 10, width - 20, height - 20);
        }
        return new Rectangle(0, 0);
    }
}
