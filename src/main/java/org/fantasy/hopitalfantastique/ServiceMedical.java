package org.fantasy.hopitalfantastique;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

enum Budget {
    INEXISTANT, MEDIOCRE, INSUFFISANT, FAIBLE
}

abstract class ServiceMedical<T extends BaseCreature> {
    protected String nom;
    protected double superficie;
    protected int capaciteMax;
    protected List<T> creatures;
    protected Budget budget;



    public ServiceMedical(String nom, double superficie, int capaciteMax, Budget budget) {
        this.nom = nom;
        this.superficie = superficie;
        this.capaciteMax = capaciteMax;
        this.budget = budget;
        this.creatures = new ArrayList<>();
    }

    public void afficherCaracteristiques() {
        System.out.println("Nom du service : " + nom);
        System.out.println("Superficie : " + superficie + " m²");
        System.out.println("Capacité maximum : " + capaciteMax);
        System.out.println("Budget : " + budget);
        System.out.println("Nombre de créatures présentes : " + creatures.size());
        System.out.println("Liste des créatures : ");
        for (T creature : creatures) {
            System.out.println(" - " + creature.nom + ", Moral : " + creature.moral);
        }
    }

    public boolean ajouterCreature(BaseCreature creature) {
        if (creatures.size() < capaciteMax) {
            creatures.add((T) creature);
            creature.serviceMedical = this;
            System.out.println(creature.nom + " a été ajouté au service " + nom + ".");
            return true;
        } else {
            System.out.println("Service complet. Impossible d'ajouter " + creature.nom + ".");
            return false;
        }
    }

    public boolean enleverCreature(T creature) {
        if (creatures.remove(creature)) {
            System.out.println(creature.nom + " a été retiré du service " + nom + ".");
            creature.serviceMedical = null;
            return true;
        } else {
            System.out.println(creature.nom + " n'est pas présent dans le service " + nom + ".");
            return false;
        }
    }

    public void soignerCreatures() {
        for (T creature : creatures) {
            System.out.println("Avant soin : " + creature.nom + " - Moral : " + creature.moral);
            creature.soigner();
            System.out.println("Après soin : " + creature.nom + " - Moral : " + creature.moral);
        }
    }


    public List<T> getCreatures() {
        return creatures;
    }

    public abstract void reviserBudget();

    public String getNom() {
        return this.nom;
    }

    public int getNombreDeCreatures() {
        return this.creatures.size();
    }

    public void afficherCreatures() {
        for (T creature : creatures) {
            System.out.println("Créature : " + creature.getNom());
            System.out.println("Moral : " + creature.moral);
            System.out.println("  Maladies: " + (creature.maladies.isEmpty() ? "Aucune" : creature.maladies.stream().map(Maladie::getNomCourt).collect(Collectors.joining(", "))));

        }
    }

}

class CentreQuarantaine<T extends BaseCreature> extends ServiceMedical<T> {
    private boolean isolation;

    public CentreQuarantaine(String nom, double superficie, int capaciteMax, Budget budget, boolean isolation) {
        super(nom, superficie, capaciteMax, budget);
        this.isolation = isolation;
    }

    @Override
    public void reviserBudget() {
        System.out.println("Révision du budget pour le Centre de Quarantaine.");
        System.out.println("Isolation : " + (isolation ? "Activée" : "Désactivée"));
        // Logique de révision spécifique au centre de quarantaine
        if (isolation && budget.ordinal() < Budget.FAIBLE.ordinal()) {
            System.out.println("Budget insuffisant pour assurer l'isolation.");
        } else {
            System.out.println("Budget du centre de quarantaine révisé avec succès.");
        }
    }

    public List<T> getCreaturesProches(BaseCreature source, double rayon) {
        return creatures.stream()
                .filter(c -> !c.equals(source) && source.distanceTo(c) <= rayon)
                .collect(Collectors.toList());
    }


    @Override
    public boolean ajouterCreature(BaseCreature creature) {
        if (creature.maladies.size() > 0) { // Vérifie si la créature est contagieuse
            return super.ajouterCreature(creature);
        } else {
            System.out.println(creature.nom + " n'est pas contagieux et ne peut pas être ajouté au Centre de Quarantaine.");
            return false;
        }
    }
    public void modifierIsolation(boolean etat) {
        this.isolation = etat;
        System.out.println("Isolation du " + getNom() + " : " + (isolation ? "Activée" : "Désactivée"));
    }
    public boolean isIsolation() {
        return isolation;
    }
}

class Crypte<T extends BaseCreature> extends ServiceMedical<T> {
    private int niveauVentilation;
    private int temperature;

    public Crypte(String nom, double superficie, int capaciteMax, Budget budget, int niveauVentilation, int temperature) {
        super(nom, superficie, capaciteMax, budget);
        this.niveauVentilation = niveauVentilation;
        this.temperature = temperature;
    }

    public void modifierTemperature(int nouvelleTemperature) {
        this.temperature = nouvelleTemperature;
        System.out.println("Température de la crypte " + getNom() + " réglée à : " + temperature + "°C");
    }

    @Override
    public void reviserBudget() {
        System.out.println("Révision du budget pour la Crypte.");
        System.out.println("Niveau de ventilation : " + niveauVentilation);
        System.out.println("Température : " + temperature + " °C");
        // Logique de révision spécifique à la crypte
        if (temperature < 10 || niveauVentilation < 2) {
            System.out.println("Conditions inadéquates pour la crypte. Révision du budget nécessaire.");
        } else {
            System.out.println("Budget de la crypte révisé avec succès.");
        }
    }

    @Override
    public boolean ajouterCreature(BaseCreature creature) {
        if (creature instanceof Vampire || creature instanceof Zombie) { // Vérifie si la créature peut se régénérer
            return super.ajouterCreature(creature);
        } else {
            System.out.println(creature.nom + " ne peut pas se régénérer et ne peut pas être ajouté à la Crypte.");
            return false;
        }
    }

    public int getTemperature() {
        return temperature;
    }
}

