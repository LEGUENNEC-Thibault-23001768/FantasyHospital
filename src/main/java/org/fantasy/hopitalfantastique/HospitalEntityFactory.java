package org.fantasy.hopitalfantastique;

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

import java.util.Optional;

import static com.almasb.fxgl.dsl.FXGL.getNotificationService;

enum EntityType {
    SERVICE_MEDICAL, CREATURE
}

public class HospitalEntityFactory implements EntityFactory {

    @Spawns("serviceMedical")
    public Entity createServiceMedical(SpawnData data) {
        return createService(data, Color.LIGHTBLUE, "Service Médical", 10);
    }

    @Spawns("crypte")
    public Entity createCrypte(SpawnData data) {
        return createService(data, Color.DARKRED, "Crypte", 5);
    }

    @Spawns("quarantaine")
    public Entity createQuarantaine(SpawnData data) {
        return createService(data, Color.DARKGREEN, "Centre Quarantaine", 15);
    }

    private Entity createService(SpawnData data, Color color, String type, int capacity) {
        var service = FXGL.entityBuilder(data)
                .type(EntityType.SERVICE_MEDICAL)
                .view(new Rectangle(120, 120, color))
                .with(new DraggableComponent())
                .build();

        service.setProperty("type", type);
        service.setProperty("capacity", capacity);

        service.getViewComponent().addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Centre Médical");
            alert.setHeaderText(type);
            alert.setContentText("Capacité: " + capacity);
            alert.show();
        });

        return service;
    }

    @Spawns("elfe")
    public Entity createElfe(SpawnData data) {
        return createCreature(data, Color.BLUE, "Elfe");
    }

    @Spawns("orque")
    public Entity createOrque(SpawnData data) {
        return createCreature(data, Color.RED, "Orque");
    }

    @Spawns("zombie")
    public Entity createZombie(SpawnData data) {
        return createCreature(data, Color.GREEN, "Zombie");
    }

    @Spawns("vampire")
    public Entity createVampire(SpawnData data) {
        return createCreature(data, Color.PURPLE, "Vampire");
    }

    @Spawns("lycanthrope")
    public Entity createLycanthrope(SpawnData data) {
        return createCreature(data, Color.ORANGE, "Lycanthrope");
    }

    private Entity createCreature(SpawnData data, Color color, String type) {
        var creature = FXGL.entityBuilder(data)
                .type(EntityType.CREATURE)
                .view(new Rectangle(40, 40, color))
                .with(new DraggableComponent())
                .build();

        creature.setProperty("type", type);
        creature.setProperty("sick", false);

        creature.getViewComponent().addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            String status = creature.getBoolean("sick") ? "Malade" : "En bonne santé";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Informations sur la créature");
            alert.setHeaderText(type);
            alert.setContentText("État: " + status + "\nVoulez-vous soigner cette créature ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK && creature.getBoolean("sick")) {
                creature.setProperty("sick", false);
                creature.getViewComponent().clearChildren();
                creature.getViewComponent().addChild(new Rectangle(40, 40, color));
                getNotificationService().pushNotification(type + " a été soigné !");
            }
        });

        return creature;
    }
}
