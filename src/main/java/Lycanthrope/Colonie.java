package lycanthrope;
 
import java.util.ArrayList;
import java.util.List;
 
public class Colonie {
    private List<Meute> meutes = new ArrayList<>();
 
    public void ajouterMeute(Meute meute) {
        meutes.add(meute);
    }
 
    public void afficherCaracteristiques() {
        meutes.forEach(Meute::afficherCaracteristiques);
    }
 
    public void simuler() {
        System.out.println("Simulation en cours...");
    }
}
