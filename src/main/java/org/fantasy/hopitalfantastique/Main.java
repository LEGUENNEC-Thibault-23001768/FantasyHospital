package org.fantasy.hopitalfantastique;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.time.TimerAction;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;
/**
 * Classe principale Main.
 * Cette classe représente l'application de simulation d'un hôpital fantastique utilisant le framework FXGL.
 */
public class Main extends GameApplication {

    private double speedMultiplier = 1.0; // Multiplicateur de vitesse du jeu
    private double gameTimer = 0; // Timer du jeu
    private TimerAction spawnTask; // Tâche de génération de créatures
    private TimerAction sickTask; // Tâche pour rendre les créatures malades
    private TimerAction moveTask; // Tâche pour déplacer les créatures
    private TimerAction gameTime; // Tâche pour suivre le temps de jeu
    private boolean isPaused = false; // Indique si le jeu est en pause

    /**
     * Constructeur par défaut pour la classe Main.
     */
    public Main() {
        super();
    }

    @Override
    public void initSettings(GameSettings settings) {
        settings.setWidth(1000);
        settings.setHeight(600);
        settings.setTitle("Fantasy Hospital Simulation");
        settings.setVersion("1.0");
        settings.setMainMenuEnabled(true);

    }

    @Override
    public void initGame() {
        getGameScene().setBackgroundColor(Color.LIGHTGRAY);

        // Enregistre la fabrique d'entités
        getGameWorld().addEntityFactory(new HospitalEntityFactory());

        // Crée les boutons pour les services et les créatures
        createServiceButtons();
        createCreatureButtons();

        // Crée les boutons de contrôle du temps
        createTimeControlButtons();

        // Initialise les tâches périodiques
        resetTasks(false);

        // Met à jour le timer personnalisé
        gameTime = getGameTimer().runAtInterval(this::updateGameTimer, Duration.seconds(0.1));
    }

    /**
     * Réinitialise ou crée les tâches périodiques pour le jeu.
     *
     * @param reset Indique si les tâches existantes doivent être réinitialisées
     */
    private void resetTasks(boolean reset) {
        if (reset) {
            if (spawnTask != null) spawnTask.expire();
            if (sickTask != null) sickTask.expire();
            if (moveTask != null) moveTask.expire();
        }

        spawnTask = getGameTimer().runAtInterval(this::spawnRandomCreature, Duration.seconds(20 / speedMultiplier));
        sickTask = getGameTimer().runAtInterval(this::makeRandomCreatureSick, Duration.seconds(15 / speedMultiplier));
        moveTask = getGameTimer().runAtInterval(this::moveCreaturesSmoothly, Duration.seconds(1 / speedMultiplier));
    }

    /**
     * Crée les boutons pour les services médicaux.
     */
    private void createServiceButtons() {
        createButton(50, 50, "Service Médical", Color.LIGHTBLUE, () -> spawn("serviceMedical", new SpawnData(400, 300)));
        createButton(150, 50, "Crypte", Color.DARKRED, () -> spawn("crypte", new SpawnData(400, 300)));
        createButton(250, 50, "Centre Quarantaine", Color.DARKGREEN, () -> spawn("quarantaine", new SpawnData(400, 300)));
    }

    /**
     * Crée les boutons pour générer des créatures.
     */
    private void createCreatureButtons() {
        createButton(50, 150, "Elfe", Color.BLUE, () -> spawn("elfe", new SpawnData(400, 300)));
        createButton(150, 150, "Orque", Color.RED, () -> spawn("orque", new SpawnData(400, 300)));
        createButton(250, 150, "Zombie", Color.GREEN, () -> spawn("zombie", new SpawnData(400, 300)));
        createButton(350, 150, "Vampire", Color.PURPLE, () -> spawn("vampire", new SpawnData(400, 300)));
        createButton(450, 150, "Lycanthrope", Color.ORANGE, () -> spawn("lycanthrope", new SpawnData(400, 300)));
    }

    /**
     * Crée les boutons de contrôle du temps.
     */
    private void createTimeControlButtons() {
        createButton(50, 550, "Pause", Color.LIGHTGRAY, this::pauseGame);
        createButton(150, 550, "1x", Color.LIGHTGREEN, () -> setSpeedMultiplier(1.0));
        createButton(250, 550, "2x", Color.YELLOW, () -> setSpeedMultiplier(2.0));
    }

    /**
     * Crée un bouton à une position donnée avec une action associée.
     *
     * @param x      Position X
     * @param y      Position Y
     * @param label  Texte affiché sur le bouton
     * @param color  Couleur du bouton
     * @param action Action à exécuter au clic
     */
    private void createButton(double x, double y, String label, Color color, Runnable action) {
        var button = entityBuilder()
                .at(x, y)
                .view(new Rectangle(100, 40, color))
                .buildAndAttach();

        var text = getUIFactoryService().newText(label, Color.BLACK, 14);
        text.setTranslateX(x + 15);
        text.setTranslateY(y + 25);
        addUINode(text);

        button.getViewComponent().addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> action.run());
    }

    /**
     * Génère une créature aléatoire sur le terrain.
     */
    private void spawnRandomCreature() {
        String[] creatureTypes = {"elfe", "orque", "zombie", "vampire", "lycanthrope"};
        String randomType = creatureTypes[new Random().nextInt(creatureTypes.length)];
        spawn(randomType, new SpawnData(new Random().nextInt(900), new Random().nextInt(500)));
    }

    /**
     * Rend une créature aléatoire malade.
     */
    private void makeRandomCreatureSick() {
        List<Entity> creatures = getGameWorld().getEntitiesByType(EntityType.CREATURE);

        if (!creatures.isEmpty()) {
            Entity randomCreature = creatures.get(new Random().nextInt(creatures.size()));
            randomCreature.setProperty("sick", true);
            applyRedAura(randomCreature);
            getNotificationService().pushNotification(randomCreature.getString("type") + " est tombé malade !");
        }
    }

    /**
     * Applique une aura rouge autour d'une créature malade.
     *
     * @param entity Entité représentant la créature
     */
    private void applyRedAura(Entity entity) {
        if (entity.getViewComponent().getChildren().stream().noneMatch(node -> node instanceof Rectangle && ((Rectangle) node).getFill() == Color.RED)) {
            var aura = new Rectangle(60, 60, Color.RED);
            aura.setTranslateX(-10);
            aura.setTranslateY(-10);
            aura.setOpacity(0.5);
            entity.getViewComponent().addChild(aura);
        }
    }

    /**
     * Déplace les créatures de manière aléatoire sur la scène.
     */
    private void moveCreaturesSmoothly() {
        List<Entity> creatures = getGameWorld().getEntitiesByType(EntityType.CREATURE);

        for (Entity creature : creatures) {
            if (!creature.getBoolean("sick")) {
                double dx = new Random().nextDouble() * 10 - 5;
                double dy = new Random().nextDouble() * 10 - 5;

                double newX = Math.max(0, Math.min(creature.getX() + dx, getAppWidth() - 40));
                double newY = Math.max(0, Math.min(creature.getY() + dy, getAppHeight() - 40));

                creature.translateTowards(new javafx.geometry.Point2D(newX, newY), 5);
            }
        }
    }

    /**
     * Définit le multiplicateur de vitesse du jeu.
     *
     * @param multiplier Nouveau multiplicateur
     */
    private void setSpeedMultiplier(double multiplier) {
        speedMultiplier = multiplier;
        resetTasks(false);
    }

    /**
     * Met le jeu en pause.
     */
    private void pauseGame() {
        if (spawnTask != null) spawnTask.pause();
        if (sickTask != null) sickTask.pause();
        if (moveTask != null) moveTask.pause();
        if (gameTime != null) gameTime.pause();
    }

    /**
     * Met à jour le timer du jeu.
     */
    private void updateGameTimer() {
        gameTimer += 0.1 * speedMultiplier;
    }

    @Override
    public void initUI() {
        var timerText = getUIFactoryService().newText("", Color.BLUE, 18);
        timerText.setTranslateX(850);
        timerText.setTranslateY(50);

        getGameTimer().runAtInterval(() -> timerText.setText("Temps: " + String.format("%.1f", gameTimer)), Duration.seconds(0.1));
        addUINode(timerText);
    }
    /**
     * Point d'entrée de l'application.
     *
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch(args);
    }
}
