package lycanthrope;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une meute de lycanthropes.
 */
public class Meute {
    private String nom;
    private Lycanthrope maleAlpha;
    private Lycanthrope femelleAlpha;
    private List<Lycanthrope> membres;

    // Constructeur
    public Meute(String nom) {
        this.nom = nom;
        this.membres = new ArrayList<>();
    }

    // Méthodes
    public String getNom() {
        return nom;
    }

    public void ajouterLycanthrope(Lycanthrope lycanthrope) {
        membres.add(lycanthrope);
        System.out.println(lycanthrope + " a rejoint la meute " + nom);
        if (maleAlpha == null || lycanthrope.getForce() > maleAlpha.getForce()) maleAlpha = lycanthrope;
        if (femelleAlpha == null || lycanthrope.getForce() > femelleAlpha.getForce()) femelleAlpha = lycanthrope;
    }

    public void retirerLycanthrope(Lycanthrope lycanthrope) {
        membres.remove(lycanthrope);
        System.out.println(lycanthrope + " a quitté la meute " + nom);
    }

    public void afficherCaracteristiques() {
        System.out.println("Meute : " + nom);
        System.out.println("Mâle α : " + (maleAlpha != null ? maleAlpha : "Aucun"));
        System.out.println("Femelle α : " + (femelleAlpha != null ? femelleAlpha : "Aucune"));
        System.out.println("Membres :");
        for (Lycanthrope membre : membres) {
            membre.afficherCaracteristiques();
        }
    }

    public void reagirHurlement(Lycanthrope auteur, String typeHurlement) {
        for (Lycanthrope membre : membres) {
            if (!membre.equals(auteur)) {
                membre.hurler(typeHurlement);
            }
        }
    }
}
