package org.fantasy.hopitalfantastique;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.DraggableComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Optional;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Main extends GameApplication {

    @Override
    public void initSettings(GameSettings settings) {
        settings.setWidth(1000);
        settings.setHeight(600);
        settings.setTitle("Fantasy Hospital Simulation");
        settings.setVersion("1.1");
    }

    @Override
    public void initGame() {
        getGameScene().setBackgroundColor(Color.LIGHTGRAY);

        // Register the entity factory
        getGameWorld().addEntityFactory(new HospitalEntityFactory());

        // Create buttons for services
        createButton(50, 50, "Service Médical", Color.LIGHTBLUE, "serviceMedical");
        createButton(200, 50, "Crypte", Color.DARKRED, "crypte");
        createButton(350, 50, "Centre Quarantaine", Color.DARKGREEN, "quarantaine");

        // Create buttons for creatures
        createButton(50, 150, "Elfe", Color.BLUE, "elfe");
        createButton(200, 150, "Orque", Color.RED, "orque");
        createButton(350, 150, "Zombie", Color.GREEN, "zombie");
        createButton(500, 150, "Vampire", Color.PURPLE, "vampire");
        createButton(650, 150, "Lycanthrope", Color.ORANGE, "lycanthrope");

        // Random events for sickness
        run(() -> showRandomEvent(), Duration.seconds(50));
    }

    public void createButton(double x, double y, String label, Color color, String spawnType) {
        var button = entityBuilder()
                .at(x, y)
                .view(new Rectangle(120, 50, color))
                .buildAndAttach();

        var text = getUIFactoryService().newText(label, Color.BLACK, 14);
        text.setTranslateX(x + 10);
        text.setTranslateY(y + 30);
        addUINode(text);

        button.getViewComponent().addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            spawn(spawnType, new SpawnData(400, 300));
        });
    }

    public void showRandomEvent() {
        var creatures = getGameWorld().getEntitiesByType(EntityType.CREATURE);

        if (!creatures.isEmpty()) {
            Entity randomCreature = creatures.get(new Random().nextInt(creatures.size()));
            randomCreature.setProperty("sick", true);
            randomCreature.getViewComponent().clearChildren();
            randomCreature.getViewComponent().addChild(new Rectangle(40, 40, Color.RED));

            getNotificationService().pushNotification("Une créature est tombée malade !");
        }
    }

    @Override
    public void initUI() {
        var timerText = getUIFactoryService().newText("", Color.BLUE, 18);
        timerText.setTranslateX(850);
        timerText.setTranslateY(50);

        run(() -> timerText.setText("Temps: " + FXGL.getGameTimer().getNow()), Duration.seconds(1));
        addUINode(timerText);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
