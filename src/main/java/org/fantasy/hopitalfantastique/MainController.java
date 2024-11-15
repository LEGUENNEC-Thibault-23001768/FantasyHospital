package org.fantasy.hopitalfantastique;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private TableView<ServiceMedical<?>> serviceTable;

    @FXML
    private ListView<String> creatureList;

    private final List<ServiceMedical<?>> services = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialisation des données ou des listeners
    }

    @FXML
    private void onCreateService() {
        // Logique pour ouvrir une boîte de dialogue de création de service
        System.out.println("Créer un nouveau service médical");
    }

    @FXML
    private void onCreateCreature() {
        // Logique pour ouvrir une boîte de dialogue de création de créature
        System.out.println("Créer une nouvelle créature");
    }

    @FXML
    private void onHealCreatures() {
        // Soigner les créatures du service sélectionné
        ServiceMedical<?> selectedService = serviceTable.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            selectedService.soignerCreatures();
            updateCreatureList(selectedService);
        } else {
            System.out.println("Aucun service sélectionné pour soigner les créatures.");
        }
    }

    @FXML
    private void updateCreatureList(ServiceMedical<?> service) {
        creatureList.getItems().clear();
        service.getCreatures().forEach(creature ->
                creatureList.getItems().add(creature.getNom() + " - Moral: " + creature.getMoral())
        );
    }
}
