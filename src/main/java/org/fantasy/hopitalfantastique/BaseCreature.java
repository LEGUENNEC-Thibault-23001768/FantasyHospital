package org.fantasy.hopitalfantastique;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

interface _Creature {
    public void attendre();
    public void hurler();
    public void emporter();
    public void soigner();
    public void tomber_malade(String nom_maladie);
    public void trepasser();
}

enum CREATURES {
    BASE,
    ELFES,
    NAINS,
    ORQUES,
    HOMMEBETTE,
    ZOMBIES,
    VAMPIRES,
    LYCANTHROPES,
    REPTILIEN
}
enum SEXE {
    HOMME,
    FEMME,
}


abstract class BaseCreature implements _Creature {

    protected String nom;
    protected SEXE sexe;
    protected double poids;
    protected int taille;
    protected int age;
    protected int moral; // entre 0 et 10
    protected List<Maladie> maladies;
    protected int compteurHurlements;
    protected int[] positions;

    protected boolean estCreatureBestiale;
    protected boolean estCreatureMorteVivante;
    protected boolean estHabitantDuTriage;
    protected boolean estClientVIP;
    protected ServiceMedical<?> serviceMedical;



    public BaseCreature(String nom, SEXE sexe, double poids, int taille, int age, int moral) {
        this.nom = nom;
        this.sexe = sexe;
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.moral = moral;
        this.maladies = new ArrayList<>();
        this.compteurHurlements = 0;

        this.estCreatureBestiale = false;
        this.estCreatureMorteVivante = false;
        this.estHabitantDuTriage = false;
        this.estClientVIP = false;
    }

    private int[] position = new int[]{0, 0}; // Par défaut en (0,0)

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.position[0] = x;
        this.position[1] = y;
    }

    public void setServiceMedical(ServiceMedical<?> service) {
        this.serviceMedical = service; // Associer le service médical
    }

    public ServiceMedical<?> getServiceMedical() {
        return this.serviceMedical; // Retourner le service médical
    }

    public double distanceTo(BaseCreature autreCreature) {
        int dx = position[0] - autreCreature.getPosition()[0];
        int dy = position[1] - autreCreature.getPosition()[1];
        return Math.sqrt(dx * dx + dy * dy); // Distance euclidienne
    }

    @Override
    public void attendre() {
        if (estClientVIP) {
            moral = Math.max(0, moral - 2);
            System.out.println(nom + " (VIP) s'impatiente rapidement !");
        } else if (estHabitantDuTriage) {
            moral = Math.max(0, moral - 1);
            System.out.println(nom + " attend patiemment avec ses semblables.");
        } else {
            moral = Math.max(0, moral - 1);
            System.out.println(nom + " attend.");
        }
    }

    @Override
    public void hurler() {
        if (moral < 4)
            System.out.print(this.nom + " hurle !");
    }

    @Override
    public void emporter() { // si +3 hurlements -> emporter()
        if (compteurHurlements >= 3) {
            System.out.println(nom + " s'emporte violemment !");
            moral = Math.max(0, moral - 2);
            compteurHurlements = 0;

            // Chance de contaminer une autre créature
            if (Math.random() < 0.3) {
                contaminer();
            }
        }
    }

    @Override
    public void soigner() {
        if (!maladies.isEmpty()) {
            Maladie maladie = maladies.remove(0);
            System.out.println(nom + " a été guéri de " + maladie.getNomComplet());
            moral = Math.min(10, moral + 2);
        } else {
            moral = Math.min(10, moral + 2);
        }
    }

    @Override
    public void tomber_malade(String nomMaladie) {
        try {
            MALADIES maladieEnum = MALADIES.valueOf(nomMaladie.toUpperCase());
            Maladie nouvelleMaladie = new Maladie(maladieEnum);
            if (!maladies.contains(nouvelleMaladie)) {
                maladies.add(nouvelleMaladie);
                moral = Math.max(0, moral - 2);
                System.out.println(nom + " est tombé malade de " + nouvelleMaladie.getNomComplet() + ".");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Maladie inconnue : " + nomMaladie);
        }
    }

    @Override
    public void trepasser() {
        if (maladies.stream().anyMatch(Maladie::estLethal)) {
            System.out.println(nom + " a trépassé...");
            if (estClientVIP && (this instanceof Elfe || this instanceof Vampire)) {
                demoraliser();
            }
            if (estCreatureMorteVivante) {
                regenerer();
            }
        }
    }


    public abstract void demoraliser();

    public abstract void contaminer();

    public abstract void regenerer();

    public String getNom() {
        return nom;
    }

    public int getMoral() {
        return moral;
    }

    public List<Maladie> getMaladies() {
        return new ArrayList<>(maladies);
    }

    public void afficherEtat() {
        System.out.println("État de " + nom + ":");
        System.out.println("  Moral: " + moral);
        System.out.println("  Maladies: " + (maladies.isEmpty() ? "Aucune" : maladies.stream().map(Maladie::getNomCourt).collect(Collectors.joining(", "))));
    }

}

// =================================== LES AUTRES CREATURES ===================================

class Elfe extends BaseCreature {
    public Elfe(String nom, SEXE sexe, double poids, int taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
        this.estClientVIP = true;
    }
    @Override
    public void demoraliser() {
        System.out.println("L'elfe " + nom + " démoralise les créatures autour de lui en trépassant.");
    }

    @Override
    public void contaminer() {
        System.out.println("Les elfes ne peuvent pas contaminer d'autres créatures.");
    }

    @Override
    public void regenerer() {
        System.out.println("Les elfes ne peuvent pas se régénérer.");
    }

}

class Nain extends BaseCreature {
    public Nain(String nom, SEXE sexe, double poids, int taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
        this.estClientVIP = true;
    }

    @Override
    public void demoraliser() {

    }

    @Override
    public void contaminer() {

    }

    @Override
    public void regenerer() {

    }

}

class Orque extends BaseCreature {
    public Orque(String nom, SEXE sexe, double poids, int taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
        this.estCreatureBestiale = true;
        this.estHabitantDuTriage = true;
    }

    public void demoraliser() {
        System.out.println("L'orque " + nom + " démoralise les créatures autour de lui");
    }

    @Override
    public void contaminer() {
        System.out.println("L'orque " + nom + " contamine les créatures");
    }

    @Override
    public void regenerer() {
        System.out.println("L'orque " + nom + " se régénère");
    }

}

class HommeBete extends BaseCreature {
    public HommeBete(String nom, SEXE sexe, double poids, int taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
        this.estCreatureBestiale = true;
        this.estHabitantDuTriage = true;
    }

    @Override
    public void demoraliser() {

    }

    @Override
    public void contaminer() {

    }

    @Override
    public void regenerer() {

    }
}

class Zombie extends BaseCreature {
    public Zombie(String nom, SEXE sexe, double poids, int taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
        this.estCreatureMorteVivante = true;
        this.estHabitantDuTriage = true;
    }

    @Override
    public void demoraliser() {

    }

    @Override
    public void contaminer() {

    }

    @Override
    public void regenerer() {

    }
}

class Vampire extends BaseCreature {
    public Vampire(String nom, SEXE sexe, double poids, int taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
        this.estCreatureMorteVivante = true;
        this.estHabitantDuTriage = true;
        this.estClientVIP = true;
    }

    @Override
    public void demoraliser() {

    }

    @Override
    public void contaminer() {

    }

    @Override
    public void regenerer() {

    }
}

class Lycanthrope extends BaseCreature {
    public Lycanthrope(String nom, SEXE sexe, double poids, int taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
        this.estCreatureBestiale = true;
        this.estHabitantDuTriage = true;
    }

    @Override
    public void demoraliser() {

    }

    @Override
    public void contaminer() {

    }

    @Override
    public void regenerer() {

    }
}

class Reptilien extends BaseCreature {
    public Reptilien(String nom, SEXE sexe, double poids, int taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
        this.estClientVIP = true;
    }

    @Override
    public void demoraliser() {

    }

    @Override
    public void contaminer() {

    }

    @Override
    public void regenerer() {

    }
}
