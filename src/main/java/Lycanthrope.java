 
package lycanthrope;
 
public class Lycanthrope {
    private String sexe;
    private String categorieAge;
    private int force;
    private int facteurDomination;
    private String rang;
    private double niveau;
    private int facteurImpetuosite;
    private boolean isResponding = false;
    private Meute meute;
 
    public Lycanthrope(String sexe, String categorieAge, int force, int facteurDomination, String rang, int facteurImpetuosite, Meute meute) {
        this.sexe = sexe;
        this.categorieAge = categorieAge;
        this.force = force;
        this.facteurDomination = facteurDomination;
        this.rang = rang;
        this.facteurImpetuosite = facteurImpetuosite;
        this.meute = meute;
        calculerNiveau();
    }
 
    public void afficherCaracteristiques() {
        System.out.println("Sexe : " + sexe);
        System.out.println("Catégorie d'âge : " + categorieAge);
        System.out.println("Force : " + force);
        System.out.println("Facteur de domination : " + facteurDomination);
        System.out.println("Rang : " + rang);
        System.out.println("Niveau : " + niveau);
        System.out.println("Facteur d'impétuosité : " + facteurImpetuosite);
        System.out.println("Meute : " + (meute != null ? meute.getNom() : "Solitaire"));
    }
 
    public void hurler(String typeHurlement) {
        if (isResponding) return;
        isResponding = true;
        System.out.println("Hurlement : " + typeHurlement);
        if (meute != null) meute.reagirHurlement(this, typeHurlement);
        isResponding = false;
    }
 
    public void transformerEnHumain() {
        if (Math.random() * 100 < niveau) {
            if (meute != null) meute.retirerLycanthrope(this);
            meute = null;
        }
    }
 
    public void quitterMeute() {
        if (meute != null) {
            meute.retirerLycanthrope(this);
            meute = null;
        }
    }
 
    public int getForce() {
        return force;
    }
 
    private void calculerNiveau() {
        niveau = force * 0.6 + facteurDomination * 0.3 + (rang.equals("α") ? 20 : 10);
    }
}
