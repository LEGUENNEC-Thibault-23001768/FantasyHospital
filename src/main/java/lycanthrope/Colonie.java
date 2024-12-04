package lycanthrope;

import java.util.ArrayList;
import java.util.List;

public class Colonie {
    private List<Meute> meutes;

    public Colonie() {
        meutes = new ArrayList<>();
    }

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
        System.out.println("Début de la simulation...");
    }
}
