package org.ibcn.gso.project;

import java.awt.Color;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

import org.ibcn.gso.project.config.GameConfig;
import org.ibcn.gso.project.config.GraphicsConfig;
import org.ibcn.gso.utils.entitysystemframework.impl.IoCEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game extends AnimationTimer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    private Scene scene;
    private Canvas canvas;
    private final IoCEngine engine = new IoCEngine();

    private final GameConfig config;

    public Game(GameConfig config) throws Exception {
        this.config = config;

        preload();

        initUI();
        initBackground();
        initPlayer();

        initSystems();
        initCamera();
    }

    private void preload() {
        //Prefetch resources that would take too long to load in-game.
    }

    private void initBackground() {
        //Initialize the world Entity
    }

    private void initPlayer() {
        // Initialize the player Entity
    }

    private void initUI() {
        // Set up graphics container
        canvas = new Canvas(config.get(GraphicsConfig.class).getScreenWidth(), config.get(GraphicsConfig.class).getScreenHeight());
        scene = new Scene(new Group(canvas));
        // Hides the OS cursor
        scene.setCursor(Cursor.NONE);

        // Set up input handling:
        EventHandler<? super MouseEvent> mouseHandler = e -> {
            // Mouse movement handler code
        };

        scene.setOnMouseMoved(mouseHandler);

        scene.setOnMouseDragged(mouseHandler);

        scene.setOnMousePressed(e -> {
            // Mouse pressed handler code
        });

        scene.setOnMouseReleased(e -> {
            // Mouse released handler code
        });

        scene.setOnKeyPressed(e -> {
            String inputChar = e.getCode().toString().toLowerCase();

            //Handle inputChar pressed
        });

        scene.setOnKeyReleased(e -> {
            String inputChar = e.getCode().toString().toLowerCase();

            //Handle inputChar released
        });

        initUIElements();
    }

    private void initUIElements() {
        //Initialize HUD: health & xp bar, weapon status, etc...
    }

    private void initCamera() {
        //Setup the camera that follows the player
    }

    private void initSystems() throws Exception {
        //Register the game systems with the Engine
    }

    public Scene getScene() {
        return scene;
    }

    //FPS counter variables
    private int counter;
    private long start = System.currentTimeMillis();

    /**
     * This method is executed ~60 times per second and as such implements the
     * game loop by calling update on the engine implementation.
     *
     * @param currentNanoTime The timestamp of the current frame given in
     * nanoseconds, can be ignored for this project.
     */
    @Override
    public void handle(long currentNanoTime) {
        counter++;

        engine.update();

        if ((System.currentTimeMillis() - start) > 3000) {
            LOGGER.info("FPS: " + (counter / 3.0));
            counter = 0;
            start = System.currentTimeMillis();
        }
    }

    void setFill(Color RED) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
