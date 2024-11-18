package org.fantasy.hopitalfantastique;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Main extends GameApplication {

    private boolean gameResumed = false;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Fantasy Hospital");
        settings.setVersion("1.0");
    }

    @Override
    protected void initUI() {
        Button resumeButton = new Button("Resume");
        resumeButton.setTranslateX(350);
        resumeButton.setTranslateY(250);
        resumeButton.setOnAction(e -> {
            FXGL.getGameScene().removeUINode(resumeButton);
            onResume();
        });
        FXGL.getGameScene().addUINode(resumeButton);
    }

    @Override
    protected void initGame() {
        createMap();
        if (gameResumed) {
            initClickableArea();
        }
    }

    private void createMap() {
        Rectangle map = new Rectangle(800, 600, Color.LIGHTGRAY);
        FXGL.getGameScene().addUINode(map);
    }

    private void initClickableArea() {
        Rectangle serviceArea = new Rectangle(100, 100, Color.DARKGREEN);
        serviceArea.setTranslateX(350);
        serviceArea.setTranslateY(250);
        FXGL.getGameScene().addUINode(serviceArea);

        Input input = FXGL.getInput();
        input.addAction(new UserAction("Create Medical Service") {
            @Override
            protected void onActionBegin() {
                if (gameResumed && FXGL.getGameScene().getUINodes().contains(serviceArea)) {
                    Text message = new Text("Service Médical Créé!");
                    message.setTranslateX(350);
                    message.setTranslateY(400);
                    FXGL.getGameScene().addUINode(message);
                }
            }
        }, MouseButton.PRIMARY);
    }

    //@Override
    protected void onResume() {
        gameResumed = true;
        initClickableArea();
    }

    public static void main(String[] args) {
        launch(args);
    }
}