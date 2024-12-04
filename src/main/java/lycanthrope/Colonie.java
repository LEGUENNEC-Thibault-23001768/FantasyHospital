package lycanthrope;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une colonie de lycanthropes.
 */
public class Colonie {
    private List<Meute> meutes;

    // Constructeur
    public Colonie() {
        meutes = new ArrayList<>();
    }

    // Méthodes
    public void ajouterMeute(Meute meute) {
        meutes.add(meute);
    }

    public void afficherCaracteristiques() {
        System.out.println("Colonie de lycanthropes :");
        for (Meute meute : meutes) {
            meute.afficherCaracteristiques();
        }
    }

    public void simuler() {
        // Simulation d'événements
        System.out.println("Début de la simulation...");
        // Exemples : reproduction, hurlements, transformations
    }
}
