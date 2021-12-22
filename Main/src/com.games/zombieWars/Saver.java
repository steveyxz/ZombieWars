package com.games.zombieWars;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;

public class Saver {

    private Game game;
    private HUD hud;
    private KeyInput keyInput;
    private MouseInput mouseInput;
    private Handler handler;
    private Spawn spawn;
    private Camera camera;
    private LinkedList<GameObject> gameObjectLinkedList;

    public Saver(LinkedList<GameObject> gameObjectLinkedList,
                 Game game,
                 HUD hud,
                 KeyInput keyInput,
                 MouseInput mouseInput,
                 Handler handler,
                 Spawn spawn,
                 Camera camera) {
        this.game = game;
        this.hud = hud;
        this.keyInput = keyInput;
        this.mouseInput = mouseInput;
        this.spawn = spawn;
        this.camera = camera;
        this.gameObjectLinkedList = gameObjectLinkedList;
        this.handler = handler;

        saveAll();
    }

    private void saveAll() {
        JSONObject jsonObject = new JSONObject();
        for (Field field : game.getClass().getFields()) {
            try {
                if (!field.getName().equals("adapter") && !field.getName().equals("sound") && !field.getName().equals("gameState") && !field.getName().equals("game") && !field.getName().equals("saverKeyInput") && !field.getName().equals("isLoading")) {
                    jsonObject.put(field.getName(), field.get(field.getName()));
                } else {
                    if (field.getName().equals("gameState")) {
                        jsonObject.put(field.getName(), String.valueOf(game.gameState));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.out.println(jsonObject.toJSONString());
            }
        }
        try {
            FileWriter fileWriter = new FileWriter("C:\\MyStuff\\Java\\games\\src\\main\\java\\com\\games\\zombieWars\\savedData\\game.json");
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
