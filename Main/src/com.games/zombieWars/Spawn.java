package com.games.zombieWars;

import com.games.zombieWars.objects.BossZombie;
import com.games.zombieWars.objects.Obstacle;
import com.games.zombieWars.objects.RangedZombie;
import com.games.zombieWars.objects.Zombie;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class Spawn implements Serializable {

    private Handler handler;
    private HUD hud;
    public static int timer = 30;
    public static int count = 0;
    public static boolean isTimerFinished = false;

    public Spawn(Handler handler, HUD hud) {
        this.handler = handler;
        this.hud = hud;
    }

    public void tick() {
        if (HUD.LEVEL == 0) {
            HUD.zombieCount = 5;
            spawnWeakZombies(5);
            int[] heights = {
                    50,
                    50,
                    50,
                    50
            };
            int[] widths = {
                    50,
                    50,
                    50,
                    50
            };
            Color[] colors = {
                    Color.green,
                    Color.green,
                    Color.green,
                    Color.green
            };
            int[] xs = {
                    350,
                    500,
                    236,
                    412
            };
            int[] ys = {
                    130,
                    439,
                    136,
                    298
            };
            for (int i = 0; i < widths.length; i++) {
                handler.addObject(new Obstacle(
                        xs[i],
                        ys[i],
                        ID.Obstacle,
                        0,
                        0,
                        100000,
                        widths[i],
                        heights[i],
                        colors[i],
                        handler
                ));
            }
            HUD.LEVEL++;
        } else {
            if (HUD.zombieCount < 1) {
                switch (HUD.LEVEL) {
                    case 1 -> {
                        removeObstacles();
                        int[] heights = {
                                60,
                                60,
                                43,
                                40,
                                53,
                                12,
                                140,
                                55
                        };
                        int[] widths = {
                                32,
                                89,
                                33,
                                53,
                                76,
                                94,
                                41,
                                55
                        };
                        Color[] colors = {
                                new Color(32, 190, 4),
                                new Color(140, 190, 4),
                                new Color(62, 140, 35),
                                Color.green,
                                new Color(32, 190, 4),
                                new Color(140, 190, 4),
                                new Color(62, 140, 35),
                                Color.green
                        };
                        int[] xs = {
                                360,
                                480,
                                -20,
                                110,
                                56,
                                235,
                                12,
                                450
                        };
                        int[] ys = {
                                320,
                                -30,
                                345,
                                298,
                                210,
                                148,
                                40,
                                450
                        };
                        for (int i = 0; i < widths.length; i++) {
                            handler.addObject(new Obstacle(
                                    xs[i],
                                    ys[i],
                                    ID.Obstacle,
                                    0,
                                    0,
                                    100000,
                                    widths[i],
                                    heights[i],
                                    colors[i],
                                    handler
                            ));
                        }
                        HUD.LEVEL++;
                        timer = 45;
                        isTimerFinished = false;
                        HUD.zombieCount = 10;
                        spawnMediumZombies(3);
                        spawnWeakZombies(7);
                        break;

                    }
                    case 2 -> {
                        removeObstacles();
                        int[] widths = {
                                41,
                                71,
                                111,
                                45,
                                25,
                                178,
                                89,
                                35
                        };
                        int[] heights = {
                                41,
                                171,
                                51,
                                43,
                                145,
                                190,
                                93,
                                22
                        };
                        int[] xs = {
                                432,
                                520,
                                172,
                                325,
                                30,
                                73,
                                323,
                                367
                        };
                        int[] ys = {
                                45,
                                125,
                                230,
                                120,
                                127,
                                10,
                                222,
                                170
                        };
                        Color[] colors = {
                                Color.green,
                                new Color(20, 180, 3),
                                new Color(60, 200, 40),
                                Color.green,
                                new Color(20, 180, 3),
                                new Color(60, 200, 40),
                                new Color(20, 140, 23),
                                new Color(87, 210, 4)
                        };
                        for (int i = 0; i < widths.length;i++) {
                            handler.addObject(new Obstacle(
                                    xs[i],
                                    ys[i],
                                    ID.Obstacle,
                                    0,
                                    0,
                                    100000,
                                    widths[i],
                                    heights[i],
                                    colors[i],
                                    handler
                            ));
                        }
                        HUD.divisor -= 0.1;
                        HUD.LEVEL++;
                        timer = 60;
                        isTimerFinished = false;
                        HUD.zombieCount = 20;
                        spawnWeakZombies(15);
                        spawnMediumZombies(5);
                        break;
                    }
                    case 3 -> {
                        removeObstacles();
                        int[] widths = {
                                41,
                                71,
                                111,
                                45,
                                25,
                                178,
                                89,
                                35
                        };
                        int[] heights = {
                                41,
                                171,
                                51,
                                43,
                                145,
                                190,
                                93,
                                22
                        };
                        int[] xs = {
                                432,
                                520,
                                172,
                                325,
                                30,
                                73,
                                323,
                                367
                        };
                        int[] ys = {
                                45,
                                125,
                                230,
                                120,
                                127,
                                10,
                                222,
                                170
                        };
                        Color[] colors = {
                                Color.red,
                                new Color(210, 180, 3),
                                new Color(140, 20, 40),
                                Color.yellow,
                                new Color(220, 20, 3),
                                new Color(141, 3, 40),
                                new Color(212, 140, 23),
                                new Color(87, 210, 4)
                        };
                        for (int i = 0; i < widths.length;i++) {
                            handler.addObject(new Obstacle(
                                    xs[i],
                                    ys[i],
                                    ID.Obstacle,
                                    0,
                                    0,
                                    100000,
                                    widths[i],
                                    heights[i],
                                    colors[i],
                                    handler
                            ));
                        }
                        HUD.divisor -= 1;
                        Game.additionalX = 140;
                        Game.additionalY = 140;
                        HUD.LEVEL++;
                        timer = 120;
                        isTimerFinished = false;
                        HUD.zombieCount = 30;
                        spawnWeakZombies(19);
                        spawnMediumZombies(10);
                        spawnStrongZombies(1);
                        break;
                    }
                    case 4 -> {
                        int[] widths = {
                                41,
                                71,
                                111,
                                45
                        };
                        int[] heights = {
                                41,
                                171,
                                51,
                                43
                        };
                        int[] xs = {
                                432,
                                -120,
                                -172,
                                325
                        };
                        int[] ys = {
                                445,
                                -125,
                                230,
                                -120
                        };
                        Color[] colors = {
                                Color.red,
                                new Color(210, 180, 3),
                                new Color(140, 20, 40),
                                Color.yellow,
                        };
                        for (int i = 0; i < widths.length;i++) {
                            handler.addObject(new Obstacle(
                                    xs[i],
                                    ys[i],
                                    ID.Obstacle,
                                    0,
                                    0,
                                    100000,
                                    widths[i],
                                    heights[i],
                                    colors[i],
                                    handler
                            ));
                        }
                        HUD.divisor -= 0.3;
                        Game.additionalX = 180;
                        Game.additionalY = 180;
                        HUD.LEVEL++;
                        timer = 120;
                        isTimerFinished = false;
                        HUD.zombieCount = 45;
                        spawnWeakZombies(19);
                        spawnMediumZombies(15);
                        spawnStrongZombies(5);
                        spawnTankZombies(2);
                        spawnFastZombies(4);
                        break;
                    }
                    case 5, 6, 7, 8 -> {
                        int[] widths = {
                                41,
                                71,
                                111,
                                45
                        };
                        int[] heights = {
                                41,
                                171,
                                51,
                                43
                        };
                        int[] xs = {
                                532,
                                -20,
                                -272,
                                525
                        };
                        int[] ys = {
                                445,
                                -125,
                                230,
                                -120
                        };
                        Color[] colors = {
                                Color.CYAN,
                                new Color(110, 80, 31),
                                new Color(140, 50, 120),
                                Color.YELLOW,
                        };
                        for (int i = 0; i < widths.length;i++) {
                            handler.addObject(new Obstacle(
                                    xs[i],
                                    ys[i],
                                    ID.Obstacle,
                                    0,
                                    0,
                                    100000,
                                    widths[i],
                                    heights[i],
                                    colors[i],
                                    handler
                            ));
                        }
                        HUD.divisor -= 0.3;
                        Game.additionalX = 260;
                        Game.additionalY = 260;
                        HUD.LEVEL++;
                        timer = 120;
                        isTimerFinished = false;
                        if (HUD.LEVEL == 5) {
                            HUD.zombieCount = 55;
                        } else if (HUD.LEVEL == 6) {
                            HUD.zombieCount = 65;
                        } else if (HUD.LEVEL == 7) {
                            HUD.zombieCount = 70;
                        } else if (HUD.LEVEL == 8) {
                            HUD.zombieCount = 84;
                        }
                        if (HUD.LEVEL == 5) {
                            spawnWeakZombies(15);
                            spawnMediumZombies(15);
                            spawnStrongZombies(10);
                            spawnTankZombies(5);
                            spawnFastZombies(10);
                        } else if (HUD.LEVEL == 6) {
                            spawnWeakZombies(17);
                            spawnMediumZombies(17);
                            spawnStrongZombies(12);
                            spawnTankZombies(7);
                            spawnFastZombies(12);
                        } else if (HUD.LEVEL == 7) {
                            spawnWeakZombies(18);
                            spawnMediumZombies(18);
                            spawnStrongZombies(13);
                            spawnTankZombies(8);
                            spawnFastZombies(13);
                        } else if (HUD.LEVEL == 8) {
                            spawnWeakZombies(20);
                            spawnMediumZombies(20);
                            spawnStrongZombies(15);
                            spawnTankZombies(12);
                            spawnFastZombies(17);
                        }
                        break;
                    }
                    case 9 -> {
                        HUD.divisor -= 0.3;
                        Game.additionalX = 300;
                        Game.additionalY = 300;
                        HUD.LEVEL++;
                        timer = 120;
                        isTimerFinished = false;
                        HUD.zombieCount = 90;
                        spawnWeakZombies(10);
                        spawnMediumZombies(25);
                        spawnStrongZombies(20);
                        spawnTankZombies(25);
                        spawnStrongZombies(10);
                        break;
                    }
                    case 10 -> {
                        HUD.divisor -= 0.3;
                        Game.additionalX = 250;
                        Game.additionalY = 250;
                        HUD.LEVEL++;
                        timer = 120;
                        isTimerFinished = false;
                        HUD.zombieCount = 54;
                        spawnMediumZombies(50);
                        spawnBossZombies(1);
                        spawnMutatedZombies(3);
                        break;
                    }
                    case 11 -> {
                        HUD.divisor -= 0.3;
                        Game.additionalX = 270;
                        Game.additionalY = 270;
                        HUD.LEVEL++;
                        timer = 160;
                        isTimerFinished = false;
                        HUD.zombieCount = 14;
                        spawnBasicRangedZombies(4);
                        spawnStrongZombies(10);
                    }
                    case 12 -> {
                        HUD.divisor -= 0.3;
                        Game.additionalX = 270;
                        Game.additionalY = 270;
                        HUD.LEVEL++;
                        timer = 200;
                        isTimerFinished = false;
                        HUD.zombieCount = 90;
                        spawnBasicRangedZombies(20);
                        spawnStrongZombies(20);
                        spawnFastZombies(50);
                    }
                    case 13 -> {
                        HUD.divisor -= 0.1;
                        Game.additionalX = 300;
                        Game.additionalY = 300;
                        HUD.LEVEL++;
                        timer = 240;
                        isTimerFinished = false;
                        HUD.zombieCount = 160;
                        spawnBasicRangedZombies(20);
                        spawnStrongZombies(50);
                        spawnMutatedZombies(20);
                        spawnTankZombies(20);
                        spawnFastZombies(50);
                    }
                    case 14 -> {
                        Game.additionalX = 320;
                        Game.additionalY = 320;
                        HUD.LEVEL++;
                        timer = 280;
                        isTimerFinished = false;
                        HUD.zombieCount = 240;
                        spawnBasicRangedZombies(30);
                        spawnStrongZombies(75);
                        spawnMutatedZombies(35);
                        spawnTankZombies(35);
                        spawnFastZombies(35);
                    }
                    default -> {
                        Game.additionalX = 330;
                        Game.additionalY = 330;
                        HUD.LEVEL++;
                        timer = 150;
                        isTimerFinished = false;
                        HUD.zombieCount = 100;
                        spawnMediumZombies(40);
                        spawnStrongZombies(30);
                        spawnTankZombies(10);
                        spawnFastZombies(20);
                        break;
                    }
                }
            }
            count++;
            if (count%((int) ((long) HUD.currentRanged.get("ammodelay"))) == 0) {
                timer--;
                if (MouseInput.bulletsRemaining.get(HUD.currentRanged.get("name")) < HUD.maxAmmo) {
                    MouseInput.bulletsRemaining.put((String) HUD.currentRanged.get("name"), MouseInput.bulletsRemaining.get(HUD.currentRanged.get("name"))+1);;
                }
            }
            if (count > 1200000) {
                count = 0;
            }

        }
    }

    private void removeObstacles() {
        LinkedList<GameObject> indexes = new LinkedList<>();
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            if (handler.gameObjects.get(i).getId() == ID.Obstacle) {
                indexes.add(handler.gameObjects.get(i));
            }
        }
        for (int i = 0; i < indexes.size(); i++) {
            handler.removeObject(indexes.get(i));
        }
    }

    private void spawnWeakZombies(int amount) {
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            handler.addObject(new Zombie(
                    r.nextInt(Game.WIDTH + 40),
                    r.nextInt(Game.HEIGHT + 40),
                    ID.WeakZombie,
                    0,
                    0,
                    1,
                    10,
                    16,
                    16,
                    handler));
        }
    }

    private void spawnTankZombies(int amount) {
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            handler.addObject(new Zombie(
                    r.nextInt(Game.WIDTH + Game.additionalX - 30),
                    r.nextInt(Game.HEIGHT + Game.additionalY - 30),
                    ID.TankZombie,
                    0,
                    0,
                    5,
                    100,
                    16,
                    16,
                    handler));
        }
    }

    private void spawnFastZombies(int amount) {
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            handler.addObject(new Zombie(
                    r.nextInt(Game.WIDTH + Game.additionalX - 30),
                    r.nextInt(Game.HEIGHT + Game.additionalY - 30),
                    ID.FastZombie,
                    0,
                    0,
                    4,
                    10,
                    40,
                    40,
                    handler));
        }
    }

    private void spawnBasicRangedZombies(int amount) {
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            handler.addObject(new RangedZombie(
                    r.nextInt(Game.WIDTH + Game.additionalX - 30),
                    r.nextInt(Game.HEIGHT + Game.additionalY - 30),
                    ID.BasicRangedZombie,
                    0,
                    0,
                    1,
                    10,
                    40,
                    40,
                    handler));
        }
    }

    private void spawnMutatedZombies(int amount) {
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            handler.addObject(new Zombie(
                    r.nextInt(Game.WIDTH + Game.additionalX - 30),
                    r.nextInt(Game.HEIGHT + Game.additionalY - 30),
                    ID.MutatedZombie,
                    0,
                    0,
                    6,
                    10,
                    50,
                    50,
                    handler));
        }
    }

    private void spawnBossZombies(int difficulty) {
        Random r = new Random();
        handler.addObject(new BossZombie(
                r.nextInt(Game.WIDTH + Game.additionalX - 30),
                r.nextInt(Game.HEIGHT + Game.additionalY - 30),
                ID.BossZombie,
                0,
                0,
                difficulty,
                10,
                40,
                40,
                handler));

    }

    private void spawnMediumZombies(int amount) {
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            handler.addObject(new Zombie(
                    r.nextInt(Game.WIDTH + Game.additionalX - 30),
                    r.nextInt(Game.HEIGHT + Game.additionalY - 30),
                    ID.MediumZombie,
                    0,
                    0,
                    2,
                    30,
                    16,
                    16,
                    handler));
        }
    }

    private void spawnStrongZombies(int amount) {
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            handler.addObject(new Zombie(
                    r.nextInt(Game.WIDTH + Game.additionalX - 30),
                    r.nextInt(Game.HEIGHT + Game.additionalY - 30),
                    ID.StrongZombie,
                    0,
                    0,
                    3,
                    70,
                    16,
                    16,
                    handler));
        }
    }

}
