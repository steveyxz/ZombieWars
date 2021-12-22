package com.games.zombieWars;

import com.games.zombieWars.screens.*;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class Game extends Canvas implements Runnable, Serializable {

	@Serial
    private static final long serialVersionUID = -7327527015232288507L;
	public static Game game;
	public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running = false;
    private final Handler handler;
    private HUD hud;
    public static boolean isPre = true;
    private Spawn spawn;
    private Camera cam;
    private ShopScreenPowerups menu;
    private MeleePowerups menu2;
    private RangedPowerups menu3;
    private StartScreen startScreen;
    private HelpMenu helpMenu;
    private End end;
    public static KeyInput saverKeyInput;
    private MouseInput mouseInput;
    private int tickPowerTickTimer = 0;
    private int tickPowerTimer = 0;
    public static boolean isLoading = false;
    public WindowAdapter adapter = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            Saver saver = new Saver(handler.gameObjects, game, hud, saverKeyInput, mouseInput, handler, spawn, cam);
        }
    };
    public static int additionalX = 120;
    public static int additionalY = 120;
    public static LinkedList<JSONObject> allRanged = new LinkedList<>();
    public static LinkedList<JSONObject> allMelee = new LinkedList<>();
    public static LinkedList<JSONObject> allStackPowers = new LinkedList<>();
    public static int FPS = 0;
    public Sound sound;
    private Paused paused;
    private BufferedImage spriteSheet;

    public enum STATE {
        PowerupsScreen,
        MeleeScreen,
        RangedScreen,
        Game,
        Start,
        Help,
        End
    }

    public STATE gameState = STATE.Start;

    public Game() {

        isLoading = true;

        handler = new Handler(this);
        cam = new Camera(0, 0, handler);
        BufferedImageLoader bufferedImageLoader = new BufferedImageLoader();
        spriteSheet = bufferedImageLoader.loadImage("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\resources\\Images\\meleeSprites.png");
        mouseInput = new MouseInput(handler, cam, this, new SpriteSheet(spriteSheet));
        this.addMouseListener(mouseInput);

        hud = new HUD(handler, this);
        spawn = new Spawn(handler, hud);
        menu = new ShopScreenPowerups(this, handler);
        startScreen = new StartScreen(this, handler);
        helpMenu = new HelpMenu(this, handler);
        end = new End(this, handler);
        paused = new Paused();
        menu2 = new MeleePowerups(this, handler);
        menu3 = new RangedPowerups(this, handler);
        this.addMouseListener(menu3);
        this.addMouseListener(menu2);
        this.addMouseListener(menu);
        this.addMouseListener(startScreen);
        this.addMouseListener(helpMenu);
        this.addMouseListener(end);
        this.addMouseListener(hud);
        JSONParser parser = new JSONParser();

        Iterator it = FileUtils.iterateFiles(new File("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\melee"), null, false);
        while(it.hasNext()){
            allMelee.add(ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\melee\\" + ((File) it.next()).getName()));
        }

        it = FileUtils.iterateFiles(new File("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged"), null, false);
        while(it.hasNext()){
            allRanged.add(ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\weapons\\ranged\\" + ((File) it.next()).getName()));
        }

        it = FileUtils.iterateFiles(new File("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\stackBoosts"), null, false);
        while(it.hasNext()){
            allStackPowers.add(ShopScreenPowerups.openJSONFile("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\data\\stackBoosts\\" + ((File) it.next()).getName()));
        }

        for (int i = 0; i < allRanged.size(); i++) {
            MouseInput.bulletsRemaining.put((String) allRanged.get(i).get("name"), (long) allRanged.get(i).get("ammo"));
        }
        new Window(HEIGHT, WIDTH, "Zombie Wars (1.0.1beta)", this);

        load();
        sound = new Sound("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\resources\\Sounds\\footsteps.wav", "C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\resources\\Sounds\\theme.wav");
        //Music from (Stock music by TCMerrill / MotionElements.com)
        sound.playMusic();


    }

    private void load() {
        //Complete LOAD
        isLoading = false;
    }


    public static void main(String[] args) {
        new Game();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        this.requestFocus();
        long lastTime  = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            if (!isLoading) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                while (delta >= 1) {
                    tick();
                    delta--;
                }
                if (running) {
                    render();
                }
                frames++;

                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    System.out.println("FPS: " + frames);
                    FPS = frames;
                    frames = 0;
                }
            }
        }
        stop();

    }

    public static float clamp(float var, float min, float max) {
        if (var >= max) {
            return var = max;
        }
        else if (var <= min) {
            return var = min;
        }
        return var;
    }

    public static void changeAdditionalValues(int x, int y) {
        additionalX = x;
        additionalY = y;
    }

    private void tick() {
        if (!KeyInput.paused){
            game = this;
            handler.tick();

            if (gameState == STATE.Game) {
                hud.tick();
                spawn.tick();
                startScreen.updateKeyInput();
                for (int i = 0; i < handler.gameObjects.size(); i++) {
                    if (handler.gameObjects.get(i).getId() == ID.Player1) {
                        cam.tick(handler.gameObjects.get(i));
                    }
                }
            } else if (gameState == STATE.PowerupsScreen) {
                menu.tick();
            } else if (gameState == STATE.Start) {
                startScreen.tick();
            } else if (gameState == STATE.End) {
                end.tick();
            } else if (gameState == STATE.MeleeScreen) {
                menu2.tick();
            } else if (gameState == STATE.RangedScreen) {
                menu3.tick();
            }
            doTickPowerups();
        }

    }

    private void doTickPowerups() {
        int regenIndex = 0;
        for (int i = 0; i < allStackPowers.size(); i++) {
            if (allStackPowers.get(i).get("id").equals("regenStack")) {
                regenIndex = i;
            }
        }
        //Regeneration
        if (tickPowerTickTimer % ((((Long) (allStackPowers.get(regenIndex).get("timeDelay"))).intValue())*60) == 0) {
            HUD.PLAYER_1_HP += 5;
            if (HUD.PLAYER_1_HP > 100) {
                HUD.PLAYER_1_HP = 100;
            }
        }
        if (tickPowerTickTimer > 2141398440) {
            tickPowerTickTimer = 0;
        }
        tickPowerTickTimer++;
    }


    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2D = (Graphics2D) g;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (!KeyInput.paused){
            if (KeyInput.shopOpen) {
                if (gameState == STATE.PowerupsScreen) {
                    menu.render(g);
                } else if (gameState == STATE.MeleeScreen) {
                    menu2.render(g);
                } else if (gameState == STATE.RangedScreen) {
                    menu3.render(g);
                }
            } else {
                g2D.translate(cam.getX(), cam.getY());

                handler.render(g);

                g2D.translate(-cam.getX(), -cam.getY());

                if (gameState == STATE.Game) {
                    hud.render(g);
                } else if (gameState == STATE.Start) {
                    startScreen.render(g);
                } else if (gameState == STATE.Help) {
                    helpMenu.render(g);
                } else if (gameState == STATE.End) {
                    end.render(g);
                }
            }
        } else {
            paused.render(g);
        }

        g.dispose();
        bs.show();
    }
}

