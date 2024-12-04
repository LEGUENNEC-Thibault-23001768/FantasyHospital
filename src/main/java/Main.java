 
package lycanthrope;
 
public class Main {
    public static void main(String[] args) {
        Colonie colonie = new Colonie();
 
        Meute meute1 = new Meute("La chaussette noire");
        Meute meute2 = new Meute("Le soleil");
 
        Lycanthrope lycan1 = new Lycanthrope("Mâle", "Adulte", 50, 10, "α", 5, meute1);
        Lycanthrope lycan2 = new Lycanthrope("Femelle", "Adulte", 45, 8, "β", 4, meute1);
 
        meute1.ajouterLycanthrope(lycan1);
        meute1.ajouterLycanthrope(lycan2);
 
        colonie.ajouterMeute(meute1);
        colonie.ajouterMeute(meute2);
 
        colonie.afficherCaracteristiques();
        lycan1.hurler("Je domine");
        colonie.simuler();
    }
}
