
 
package lycanthrope;
 
import java.util.ArrayList;
import java.util.List;
 
public class Meute {
    private String nom;
    private Lycanthrope maleAlpha;
    private Lycanthrope femelleAlpha;
    private List<Lycanthrope> membres = new ArrayList<>();
 
    public Meute(String nom) {
        this.nom = nom;
    }
 
    public String getNom() {
        return nom;
    }
 
    public void ajouterLycanthrope(Lycanthrope lycanthrope) {
        membres.add(lycanthrope);
        if (maleAlpha == null || lycanthrope.getForce() > maleAlpha.getForce()) maleAlpha = lycanthrope;
        if (femelleAlpha == null || lycanthrope.getForce() > femelleAlpha.getForce()) femelleAlpha = lycanthrope;
    }
 
    public void retirerLycanthrope(Lycanthrope lycanthrope) {
        membres.remove(lycanthrope);
    }
 
    public void afficherCaracteristiques() {
        System.out.println("Meute : " + nom);
        System.out.println("Mâle α : " + (maleAlpha != null ? maleAlpha : "Aucun"));
        System.out.println("Femelle α : " + (femelleAlpha != null ? femelleAlpha : "Aucune"));
        membres.forEach(Lycanthrope::afficherCaracteristiques);
    }
 
    public void reagirHurlement(Lycanthrope auteur, String typeHurlement) {
        membres.stream()
                .filter(membre -> !membre.equals(auteur))
                .forEach(membre -> membre.hurler(typeHurlement));
    }
}
