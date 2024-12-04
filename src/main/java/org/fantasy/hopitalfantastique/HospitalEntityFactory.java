package org.fantasy.hopitalfantastique;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.DraggableComponent;
import com.almasb.fxgl.dsl.components.view.TextViewComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;

enum EntityType {
    SERVICE_MEDICAL, CREATURE
}
/**
 * Classe HospitalEntityFactory.
 * Cette classe implémente l'interface EntityFactory de FXGL et fournit les méthodes pour
 * créer différentes entités utilisées dans un hôpital fantastique, comme des services médicaux et des créatures.
 */
public class HospitalEntityFactory implements EntityFactory {

    private static final String[] NAMES = {"Alaric", "Bryn", "Caelan", "Daria", "SEBAA", "Mickael", "Martin", "Tenders", "Hotwings", "Chicken", "Eryndor", "Faylen", "Gareth", "Halia"};
    private final Random random = new Random();

    /**
     * Crée une entité représentant un service médical générique.
     *
     * @param data Données de création de l'entité
     * @return Une entité représentant un service médical
     */
    @Spawns("serviceMedical")
    public Entity createServiceMedical(SpawnData data) {
        var service = FXGL.entityBuilder(data)
                .type(EntityType.SERVICE_MEDICAL)
                .view(new Rectangle(120, 120, Color.LIGHTBLUE))
                .with(new DraggableComponent())
                .with("capacity", 10)
                .with("current", 0)
                .build();

        var textView = new TextViewComponent(10, -20, "");
        textView.setY(-20);
        service.addComponent(textView);

        FXGL.run(() -> {
            service.getComponent(TextViewComponent.class).getText().setText(service.getInt("current") + "/" + service.getInt("capacity"));
        }, Duration.seconds(0.1));

        setupServicePopup(service, "Service Médical");
        return service;
    }
    /**
     * Crée une entité représentant une crypte.
     *
     * @param data Données de création de l'entité
     * @return Une entité représentant une crypte
     */
    @Spawns("crypte")
    public Entity createCrypte(SpawnData data) {
        var service = FXGL.entityBuilder(data)
                .type(EntityType.SERVICE_MEDICAL)
                .view(new Rectangle(120, 120, Color.DARKRED))
                .with(new DraggableComponent())
                .with("capacity", 5)
                .with("current", 0)
                .build();

        var capacityText = FXGL.getUIFactoryService().newText(
                service.getInt("current") + "/" + service.getInt("capacity"),
                Color.BLACK, 14
        );
        capacityText.setTranslateX(service.getX() + 40);
        capacityText.setTranslateY(service.getY() - 20);
        FXGL.addUINode(capacityText);

        service.setProperty("capacityText", capacityText);
        setupServicePopup(service, "Crypte");
        return service;
    }

    /**
     * Crée une entité représentant un centre de quarantaine.
     *
     * @param data Données de création de l'entité
     * @return Une entité représentant un centre de quarantaine
     */
    @Spawns("quarantaine")
    public Entity createQuarantaine(SpawnData data) {
        var service = FXGL.entityBuilder(data)
                .type(EntityType.SERVICE_MEDICAL)
                .view(new Rectangle(120, 120, Color.DARKGREEN))
                .with(new DraggableComponent())
                .with("capacity", 8)
                .with("current", 0)
                .build();

        var capacityText = FXGL.getUIFactoryService().newText(
                service.getInt("current") + "/" + service.getInt("capacity"),
                Color.BLACK, 14
        );
        capacityText.setTranslateX(service.getX() + 40);
        capacityText.setTranslateY(service.getY() - 20);
        FXGL.addUINode(capacityText);

        service.setProperty("capacityText", capacityText);

        setupServicePopup(service, "Quarantaine");
        return service;
    }
    private void updateServiceTextPosition(Entity service) {
        var capacityText = (javafx.scene.text.Text) service.getObject("capacityText");
        capacityText.setTranslateX(service.getX() + 40);
        capacityText.setTranslateY(service.getY() - 20);
    }

    /**
     * Crée une entité de type Elfe.
     *
     * @param data Données pour le spawn
     * @return Une entité représentant un Elfe
     */
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

    private void updateServiceCapacity(Entity service) {
        var capacityText = (javafx.scene.text.Text) service.getObject("capacityText");
        capacityText.setText(service.getInt("current") + "/" + service.getInt("capacity"));
    }
    /**
     * Crée une entité représentant une créature spécifique.
     *
     * @param data  Données de création de l'entité
     * @param color Couleur de la représentation graphique
     * @param type  Type de la créature (par exemple, "Elfe", "Orque")
     * @return Une entité représentant une créature
     */
    private Entity createCreature(SpawnData data, Color color, String type) {
        String name = NAMES[random.nextInt(NAMES.length)];
        SEXE sexe = random.nextBoolean() ? SEXE.HOMME : SEXE.FEMME;
        int age = random.nextInt(300) + 1; // 1 à 300 ans
        double taille = 1.2 + random.nextDouble() * 1.0; // 1.2m à 2.2m
        double poids = 50 + random.nextDouble() * 100; // 50kg à 150kg
        int moral = random.nextInt(10) + 1; // 1 à 10

        var creature = FXGL.entityBuilder(data)
                .type(EntityType.CREATURE)
                .view(new Rectangle(40, 40, color))
                .with(new DraggableComponent())
                .with("type", type)
                .with("name", name)
                .with("sexe", sexe)
                .with("age", age)
                .with("taille", taille)
                .with("poids", poids)
                .with("originalColor", color)
                .with("moral", moral)
                .with("inService", false)
                .with("sick", false)
                .build();

        creature.getViewComponent().addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                showPopupForCreature(creature);
            }
        });

        creature.getViewComponent().addEventHandler(javafx.scene.input.MouseEvent.MOUSE_DRAGGED, event -> {
            if (creature.getBoolean("inService")) {
                event.consume();
            }
        });

        creature.getViewComponent().addEventHandler(javafx.scene.input.MouseEvent.MOUSE_RELEASED, event -> {
            List<Entity> services = FXGL.getGameWorld().getEntitiesByType(EntityType.SERVICE_MEDICAL);

            for (Entity service : services) {
                if (service.getBoundingBoxComponent().isCollidingWith(creature.getBoundingBoxComponent())) {
                    int current = service.getInt("current");
                    int capacity = service.getInt("capacity");

                    if (current < capacity) {
                        service.setProperty("current", current + 1);
                        updateServiceCapacity(service);
                        creature.setProperty("inService", true);
                        creature.setPosition(service.getX() + 10, service.getY() + 10);
                        creature.removeComponent(DraggableComponent.class);

                        FXGL.runOnce(() -> {
                            creature.setProperty("sick", false);
                            service.setProperty("current", service.getInt("current") - 1);
                            updateServiceCapacity(service);

                            creature.getViewComponent().clearChildren();
                            creature.getViewComponent().addChild(new Rectangle(40, 40, creature.getObject("originalColor")));
                            getNotificationService().pushNotification(creature.getString("name") + " a été soigné !");
                        }, Duration.seconds(15));

                        return;
                    } else {
                        getNotificationService().pushNotification("Service médical plein !");
                    }
                }
            }
        });


        return creature;
    }
    /**
     * Configure les interactions pour un service.
     *
     * @param entity      Entité représentant un service
     * @param serviceType Type de service (par exemple, "Crypte")
     */
    private void setupServicePopup(Entity entity, String serviceType) {
        entity.getViewComponent().addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                showPopupForService(entity, serviceType);
            }
        });
    }

    /**
     * Affiche un popup avec les informations d'une créature.
     *
     * @param creature Entité représentant une créature
     */
    private void showPopupForCreature(Entity creature) {
        var popup = new VBox();
        popup.setStyle("-fx-background-color: lightgray; -fx-padding: 10; -fx-border-color: black;");
        popup.setTranslateX(300);
        popup.setTranslateY(200);

        var title = getUIFactoryService().newText("Créature : " + creature.getString("type"), Color.BLACK, 16);
        var details = getUIFactoryService().newText(
                "Nom: " + creature.getString("name") + "\n" +
                        "Sexe: " + creature.getObject("sexe") + "\n" +
                        "Âge: " + creature.getInt("age") + " ans\n" +
                        "Taille: " + String.format("%.2f", creature.getDouble("taille")) + " m\n" +
                        "Poids: " + String.format("%.2f", creature.getDouble("poids")) + " kg\n" +
                        "Moral: " + creature.getInt("moral") + "\n" +
                        "Malade: " + (creature.getBoolean("sick") ? "Oui" : "Non"),
                Color.BLACK, 14
        );

        var healButton = getUIFactoryService().newButton("Soigner");
        healButton.setOnAction(e -> {
            if (creature.getBoolean("sick")) {
                creature.setProperty("sick", false);
                creature.getViewComponent().clearChildren();
                creature.getViewComponent().addChild(new Rectangle(40, 40, creature.getObject("originalColor")));
                getNotificationService().pushNotification(creature.getString("name") + " a été soigné !");
            } else {
                getNotificationService().pushNotification(creature.getString("name") + " n'est pas malade !");
            }
        });

        var killButton = getUIFactoryService().newButton("Tuer");
        killButton.setOnAction(e -> {
            creature.removeFromWorld();
            getNotificationService().pushNotification(creature.getString("name") + " a été éliminé !");
            removeUINode(popup);
        });

        popup.getChildren().addAll(title, details, healButton, killButton);
        addUINode(popup);

        popup.setOnMouseClicked(e -> removeUINode(popup));
    }


    /**
     * Affiche un popup avec les informations d'un service.
     *
     * @param service     Entité représentant un service
     * @param serviceType Type de service
     */
    private void showPopupForService(Entity service, String serviceType) {
        var popup = new VBox();
        popup.setStyle("-fx-background-color: lightgray; -fx-padding: 10; -fx-border-color: black;");
        popup.setTranslateX(300);
        popup.setTranslateY(200);

        var title = getUIFactoryService().newText("Service : " + serviceType, Color.BLACK, 16);
        var details = getUIFactoryService().newText(
                "Capacité : " + service.getInt("current") + "/" + service.getInt("capacity"),
                Color.BLACK, 14
        );

        popup.getChildren().addAll(title, details);
        addUINode(popup);

        popup.setOnMouseClicked(e -> removeUINode(popup)); // Click on the popup to close it
    }
}
