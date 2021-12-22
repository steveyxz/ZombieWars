package com.games.zombieWars.objects;

import com.games.zombieWars.*;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class Zombie extends GameObject {

    public int health;
    public boolean alive = true;
    public int difficulty;
    private GameObject player;
    private Handler handler;
    private ID id;
    private int hp;
    private int exX;
    private int exY;
    private int exGen;
    /**
     * A HashMap that contains which way the zombie is planning to go, preventing them from changing
     * directions midway.
     * T -> Top
     * B -> Bottom
     * L -> Left
     * R -> Right
     **/
    private HashMap<GameObject, String> Tdirections = new HashMap<>();
    private HashMap<GameObject, String> Bdirections = new HashMap<>();
    private HashMap<GameObject, String> Ldirections = new HashMap<>();
    private HashMap<GameObject, String> Rdirections = new HashMap<>();
    private double opacity = 1;
    private int flashWait = 0;
    public boolean isFlashing;
    private int damageDelay;

    public Zombie(float x, float y, ID id, float speedX, float speedY, int difficulty, int hp, int width, int height, Handler handler) {
        super(x, y, id, speedX, speedY, hp, width, height);

        this.id = id;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.hp = hp;

        this.difficulty = difficulty;
        this.handler = handler;

        health = difficulty * 60;
        if (id == ID.MutatedZombie) {
            setHp(getHp()*20);
        }

        Random r = new Random();

        id = ID.Player1;

        for(int i = 0; i<handler.gameObjects.size();i++) {

            if (handler.gameObjects.get(i).getId() == id) {
                player = handler.gameObjects.get(i);
            }
        }

        if (HUD.divisor < 0.1) {
            HUD.divisor = 0.1F;
        }

        exX =r.nextInt((int)(HUD.divisor*1.1));
        exY =r.nextInt((int)(HUD.divisor*1.1));
        exGen =r.nextInt((int)(HUD.divisor))+1;

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

    @Override
    public void tick() {
        if (alive && isPlayerAlive()) {
            x += (speedX);
            x = Game.clamp(x, -Game.additionalX, Game.WIDTH + Game.additionalX);
            y += (speedY);
            y = Game.clamp(y, -Game.additionalY, Game.HEIGHT + Game.additionalY);
            collision();
            Random r = new Random();

            float diffX = x - player.getX() - 8;
            float diffY = y - player.getY() - 8;
            float distance = (float) Math.sqrt((diffX + 8)*(diffX + 8) + (diffY + 8)*(diffY + 8));

            if (this.id == ID.FastZombie) {
                speedX = (float) (((-1.0/distance) * diffX) * (5 - HUD.divisor));
                speedY = (float) (((-1.0/distance) * diffY) * (5 - HUD.divisor));
            } else if (this.id == ID.TankZombie) {
                speedX = (float) (((-1.0/distance) * diffX) / (exGen));
                speedY = (float) (((-1.0/distance) * diffY) / (exGen));
            } else {
                speedX = (float) (((-1.0 / distance) * diffX) / (exGen));
                speedY = (float) (((-1.0 / distance) * diffY) / (exGen));
            }
            dodgeObstacles();
            if (isFlashing) {
                opacity = 0.5;
                flashWait++;
                if (flashWait == 10) {
                    opacity = 1;
                    flashWait = 0;
                    isFlashing = false;
                }
            }
        }
    }

    private void dodgeObstacles() {
        LinkedList<GameObject> obstacleList = new LinkedList<>();
        GameObject player = null;
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            if (handler.gameObjects.get(i).getId() == ID.Obstacle) {
                obstacleList.add(handler.gameObjects.get(i));
            }
            if (handler.gameObjects.get(i).getId() == ID.Player1) {
                player = handler.gameObjects.get(i);
            }
        }
        for (int i = 0; i < obstacleList.size(); i++) {
            GameObject obs = obstacleList.get(i);
            if (new Rectangle((int) x - 20, (int) y - 20, width + 4000, height + 40).intersects(obs.getBounds())) {
                if (new Rectangle((int) x - 20, (int) y + (height), width + 40, 20).intersects(obs.getBounds()) && player.getY() > getY()) {
                    //Zombie is on Obstacle TOP
                    LinkedList<GameObject> keys = new LinkedList<>();
                    //Adds adds all not needed obstacles to the linkedList keys
                    for (GameObject object : Tdirections.keySet()) {
                        if (!object.equals(obs)) {
                            keys.add(object);
                        }
                    }
                    //Removes them all
                    for (GameObject g :
                            keys) {
                        Tdirections.remove(g);
                    }
                    if (!Tdirections.containsKey(obs)) {
                        //First time they meet that obstacle
                        if (speedX < 0) {
                            speedX -= 2F / (exGen);
                            Tdirections.put(obs, "negative");
                        } else if (speedX > 0) {
                            speedX += 2F / (exGen);
                            Tdirections.put(obs, "positive");
                        } else {
                            Tdirections.put(obs, "positive");
                            if (player.getX() > getX()) {
                                speedX += 2F / (exGen);
                            } else if (player.getX() < getX()) {
                                speedX += 2F / (exGen);
                            } else {
                                speedX = 2F / (exGen);
                            }
                        }
                        if (Bdirections.containsKey(obs)) {
                            if (!Bdirections.get(obs).equals(Tdirections.get(obs))) {
                                Bdirections.put(obs, Tdirections.get(obs));
                            }
                        }
                    } else {
                        if (Tdirections.get(obs).equals("negative")) {
                            speedX = -2F / (exGen);
                            Tdirections.put(obs, "negative");
                        } else if (Tdirections.get(obs).equals("positive")) {
                            speedX = 2F / (exGen);
                            Tdirections.put(obs, "positive");
                        }
                    }
                } else {
                    Tdirections.clear();
                }
                if (new Rectangle((int) x + width, (int) y - 20, 20, height + 40).intersects(obs.getBounds()) && player.getX() > getX()) {
                    //Zombie is on Obstacle LEFT
                    LinkedList<GameObject> keys = new LinkedList<>();
                    for (GameObject object : Ldirections.keySet()) {
                        if (!object.equals(obs)) {
                            keys.add(object);
                        }
                    }
                    for (GameObject g :
                            keys) {
                        Ldirections.remove(g);
                    }
                    if (!Ldirections.containsKey(obs)) {
                        if (speedY < 0) {
                            speedY -= 2F / (exGen);
                            Ldirections.put(obs, "negative");
                        } else if (speedY > 0) {
                            speedY += 2F / (exGen);
                            Ldirections.put(obs, "positive");
                        } else {
                            Ldirections.put(obs, "positive");
                            if (player.getX() > getX()) {
                                speedY += 2F / (exGen);
                            } else if (player.getX() < getX()) {
                                speedY += 2F / (exGen);
                            } else {
                                speedY = 2F / (exGen);
                            }
                        }
                        if (Rdirections.containsKey(obs)) {
                            if (!Rdirections.get(obs).equals(Ldirections.get(obs))) {
                                Rdirections.put(obs, Ldirections.get(obs));
                            }
                        }
                    } else {
                        if (Ldirections.get(obs).equals("negative")) {
                            speedY = -2F / (exGen);
                            Ldirections.put(obs, "negative");
                        } else if (Ldirections.get(obs).equals("positive")) {
                            speedY = 2F / (exGen);
                            Ldirections.put(obs, "positive");
                        }
                    }
                } else {
                    Ldirections.clear();
                }
                if (new Rectangle((int) x - 20, (int) y - 20, 20, height + 40).intersects(obs.getBounds()) && player.getX() < getX()) {
                    //Zombie is on Obstacle RIGHT
                    LinkedList<GameObject> keys = new LinkedList<>();
                    for (GameObject object : Rdirections.keySet()) {
                        if (!object.equals(obs)) {
                            keys.add(object);
                        }
                    }
                    for (GameObject g :
                            keys) {
                        Rdirections.remove(g);
                    }
                    if (!Rdirections.containsKey(obs)) {
                        if (speedY < 0) {
                            speedY -= 2F / (exGen);
                            Rdirections.put(obs, "negative");
                        } else if (speedY > 0) {
                            speedY += 2F / (exGen);
                            Rdirections.put(obs, "positive");
                        } else {
                            Rdirections.put(obs, "positive");
                            if (player.getX() > getX()) {
                                speedY += 2F / (exGen);
                            } else if (player.getX() < getX()) {
                                speedY += 2F / (exGen);
                            } else {
                                speedY = 2F / (exGen);
                            }
                        }
                        if (Ldirections.containsKey(obs)) {
                            if (!Rdirections.get(obs).equals(Ldirections.get(obs))) {
                                Ldirections.put(obs, Rdirections.get(obs));
                            }
                        }
                    } else {
                        if (Rdirections.get(obs).equals("negative")) {
                            speedY = -2F / (exGen);
                            Rdirections.put(obs, "negative");
                        } else if (Rdirections.get(obs).equals("positive")) {
                            speedY = 2F / (exGen);
                            Rdirections.put(obs, "positive");
                        }
                    }
                } else {
                    Rdirections.clear();
                }
                if (new Rectangle((int) x - 20, (int) y - 20, width + 40, 20).intersects(obs.getBounds()) && player.getY() < getY()) {
                    //Zombie is on Obstacle BOTTOM
                    LinkedList<GameObject> keys = new LinkedList<>();
                    for (GameObject object : Bdirections.keySet()) {
                        if (!object.equals(obs)) {
                            keys.add(object);
                        }
                    }
                    for (GameObject g :
                            keys) {
                        Bdirections.remove(g);
                    }
                    if (!Bdirections.containsKey(obs)) {
                        if (speedX < 0) {
                            speedX -= 2F / (exGen);
                            Bdirections.put(obs, "negative");
                        } else if (speedX > 0) {
                            speedX += 2F / (exGen);
                            Bdirections.put(obs, "positive");
                        } else {
                            Bdirections.put(obs, "positive");
                            if (player.getX() > getX()) {
                                speedX += 2F / (exGen);
                            } else if (player.getX() < getX()) {
                                speedX += 2F / (exGen);
                            } else {
                                speedX = 2F / (exGen);
                            }
                        }
                        if (Bdirections.containsKey(obs)) {
                            if (!Bdirections.get(obs).equals(Tdirections.get(obs))) {
                                Tdirections.put(obs, Bdirections.get(obs));
                            }
                        }
                    } else {
                        if (Bdirections.get(obs).equals("negative")) {
                            speedX = -2F / (exGen);
                            Bdirections.put(obs, "negative");
                        } else if (Bdirections.get(obs).equals("positive")) {
                            speedX = 2F / (exGen);
                            Bdirections.put(obs, "positive");
                        }
                    }
                } else {
                    Bdirections.clear();
                }
            }
        }
    }

    private void collision() {
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            GameObject tempObject = handler.gameObjects.get(i);
            if (tempObject.getId() == ID.RadioActiveZone) {
                if (id == ID.MutatedZombie) {
                    break;
                }
                if (getBounds().intersects(tempObject.getBounds())) {
                    if (damageDelay == 0) {
                        isFlashing = true;
                        setHp(getHp() - (((RadioactiveZone) tempObject).timeRemaining*4));
                        if (getHp() < 1) {
                            kill();
                        }
                        damageDelay++;
                    } else {
                        damageDelay++;
                        if (damageDelay == 60) {
                            damageDelay = 0;
                        }
                    }
                }
            }
            if (tempObject.getId() == ID.Obstacle) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    if (getBounds().intersects(tempObject.getBoundsBottom())) {
                        y = Game.clamp(y, tempObject.getY()+tempObject.getHeight(), Game.HEIGHT + Game.additionalY-height);
                    }
                    if (getBounds().intersects(tempObject.getBoundsTop())) {
                        y = Game.clamp(y, -Game.additionalY, tempObject.getY() - height);
                    }
                    if (getBounds().intersects(tempObject.getBoundsLeft())) {
                        x = Game.clamp(x, -Game.additionalX, tempObject.getX()-width);
                    }
                    if (getBounds().intersects(tempObject.getBoundsRight())) {
                        x = Game.clamp(x, tempObject.getX()+tempObject.getWidth(), Game.WIDTH + Game.additionalX);
                    }
                    if (getBounds().intersects(tempObject.getBoundsMiddle())) {
                        x = Game.clamp(x, tempObject.getX()+tempObject.getWidth(), Game.WIDTH + Game.additionalX);
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (alive && isPlayerAlive()) {
            switch (difficulty) {
                case 1:
                    g.setColor(new Color(1, 1, 1, (float) (opacity)));
                    g.fillRect((int)x, (int)y, 16, 16);
                    g.setColor(Color.BLACK);
                    g.drawString("B", (int)x+4, (int)y+10);
                    break;
                case 2:
                    g.setColor(new Color(210 / 255F, 210 / 255F, 20 / 255F, (float) (opacity)));
                    g.fillRect((int)x, (int)y, 16, 16);
                    g.setColor(Color.BLACK);
                    g.drawString("R", (int)x+4, (int)y+10);
                    break;
                case 3:
                    g.setColor(new Color(190 / 255F, 190 / 255F, 190 / 255F, (float) (opacity)));
                    g.fillRect((int)x, (int)y, 16, 16);
                    g.setColor(Color.BLACK);
                    g.drawString("H", (int)x+4, (int)y+10);
                    break;
                case 4:
                    g.setColor(new Color(255 / 255F, 0 / 255F, 255 / 255F, (float) (opacity)));
                    g.fillRect((int)x, (int)y, 16, 16);
                    g.setColor(Color.BLACK);
                    g.drawString("F", (int)x+4, (int)y+10);
                    break;
                case 5:
                    g.setColor(new Color(255 / 255F, 175 / 255F, 175 / 255F, (float) (opacity)));
                    g.fillRect((int)x, (int)y, 16, 16);
                    g.setColor(Color.BLACK);
                    g.drawString("T", (int)x+4, (int)y+10);
                    break;
                case 6:
                    g.setColor(new Color(145 / 255F, 156 / 255F, 112 / 255F, (float) (opacity)));
                    g.fillRect((int)x, (int)y, 30, 30);
                    g.setColor(Color.BLACK);
                    g.drawString("M", (int)x+8, (int)y+10);
                    break;
                default:
                    break;
            }
        }
    }

    public void kill() {
        alive = false;
        switch (id) {
            case FastZombie:
            case StrongZombie:
                HUD.coins += 5;
                break;
            case TankZombie:
                HUD.coins += 8;
                break;
            case WeakZombie:
                HUD.coins += 1;
                break;
            case MediumZombie:
                HUD.coins += 2;
                break;
            default:
                HUD.coins += 3;
                break;
        }
        HUD.zombieCount--;
        handler.removeObject(this);
    }

    @Override
    public void realRender(Graphics2D g) {

    }

    @Override
    public Rectangle getBounds() {
        if (id == ID.MutatedZombie) {
            return new Rectangle((int)x, (int) y, 30, 30);
        }
        return new Rectangle((int)x, (int) y, 16, 16);
    }
}

