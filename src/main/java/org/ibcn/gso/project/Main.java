package org.ibcn.gso.project;

import java.awt.Color;
import java.io.FileInputStream;
import org.ibcn.gso.project.config.GameConfig;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.ibcn.gso.project.config.GraphicsConfig;
import org.slf4j.LoggerFactory;

public class Main extends Application {

    private final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) {
        try {
            //TODO: read your config here, we've provided a temporary stub
            GameConfig config = readConfig();
            GraphicsConfig gcf = config.get(GraphicsConfig.class);

            // Max resolution if fullscreen
            if (gcf.getFullscreen()) {
                Rectangle2D bounds = Screen.getPrimary().getBounds();
                gcf.setScreenWidth((int) bounds.getWidth());
                gcf.setScreenHeight((int) bounds.getHeight());
                primaryStage.setFullScreen(true);
                primaryStage.setFullScreenExitHint(null);//"Press Esc to exit the game");
                primaryStage.addEventFilter(EventType.ROOT, event -> {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        KeyEvent kev = (KeyEvent) event;
                        if (kev.getCode() == KeyCode.ESCAPE) {
                            Platform.exit();
                        }
                    }
                });
            }

            // Create game
            Game game = new Game(config);
            Canvas canvas = new Canvas(gcf.getScreenWidth(), gcf.getScreenHeight());
           
            
            //render system dat je als laatste oproept
            //render system praat met je IOC container 
            //worldcomponent, render system en world enity
            //component toekennen
            //worldentity sprite oproepen 
            //in game.java 

                GraphicsContext gc = canvas.getGraphicsContext2D(); 

                Image sprite = new Image("sprites/level1.png");

                gc.drawImage(sprite, 0, 0);
                Scene gameScene = new Scene(new Group(canvas));




            // Setup stage
            primaryStage.setScene(gameScene);
            
           
           
           
            primaryStage.show();
           

            
            // Start game
            game.start();
        } catch (Exception e) {
            LOGGER.error("Uncaught exception while starting game!", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    //Delete this method once you have your own Config reader / provider
    private GameConfig readConfig() {
        GameConfig cfg = new GameConfig();
        cfg.add(new GraphicsConfig());
        return cfg;
    }
}
