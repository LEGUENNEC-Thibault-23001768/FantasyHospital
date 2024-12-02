package org.fantasy.hopitalfantastique;

import java.util.ArrayList;
import java.util.List;

enum Budget {
    INEXISTANT, MEDIOCRE, INSUFFISANT, FAIBLE
}
abstract class ServiceMedical<T extends BaseCreature> {
    protected final String nom;
    protected final double superficie;
    protected final int capaciteMax;



    protected final List<T> creatures;
    protected Budget budget;

    public ServiceMedical(String nom, double superficie, int capaciteMax, Budget budget) {
        this.nom = nom;
        this.superficie = superficie;
        this.capaciteMax = capaciteMax;
        this.budget = budget;
        this.creatures = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }
    public List<T> getCreatures() {
        return creatures;
    }

    public boolean ajouterCreature(BaseCreature creature) {
        if (creatures.size() >= capaciteMax) {
            System.out.println("Service complet. Impossible d'ajouter " + creature.getNom() + ".");
            return false;
        }
        if (!creatures.isEmpty() && !creatures.get(0).getClass().isAssignableFrom(creature.getClass())) {
            System.out.println("Erreur : Le type de " + creature.getNom() + " n'est pas compatible avec ce service.");
            return false;
        }
        creatures.add((T) creature);
        creature.setServiceMedical(this);
        System.out.println(creature.getNom() + " a été ajouté au service " + nom + ".");
        return true;
    }

    public boolean enleverCreature(T creature) {
        if (creatures.remove(creature)) {
            creature.setServiceMedical(null);
            System.out.println(creature.getNom() + " a été retiré du service " + nom + ".");
            return true;
        } else {
            System.out.println(creature.getNom() + " n'est pas présent dans le service " + nom + ".");
            return false;
        }
    }

    public void afficherCaracteristiques() {
        System.out.println("Service : " + nom);
        System.out.println("Superficie : " + superficie + " m²");
        System.out.println("Capacité max : " + capaciteMax);
        System.out.println("Budget : " + budget);
        System.out.println("Nombre de créatures : " + creatures.size());
        System.out.println("Créatures présentes :");
        for (T creature : creatures) {
            System.out.println(" - " + creature.getNom() + " (Moral : " + creature.getMoral() + ")");
        }
    }

    public abstract void reviserBudget();

    public void soignerCreatures() {
        for (T creature : creatures) {
            creature.soigner();
        }
    }

    public int getNombreDeCreatures() {
        return creatures.size();
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
        if (isolation && budget.ordinal() < Budget.FAIBLE.ordinal()) {
            System.out.println("Budget insuffisant pour assurer l'isolation.");
        } else {
            System.out.println("Budget du centre de quarantaine révisé avec succès.");
        }
    }
    @Override
    public boolean ajouterCreature(BaseCreature creature) {
        if (creature.maladies.size() > 0) {
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

    @Override
    public boolean ajouterCreature(BaseCreature creature) {
        if (!(creature instanceof ComportementRegeneration)) {
            System.out.println(creature.getNom() + " ne peut pas se régénérer et ne peut pas être ajouté à la crypte.");
            return false;
        }
        return super.ajouterCreature(creature);
    }

    @Override
    public void reviserBudget() {
        System.out.println("Révision du budget pour la crypte :");
        if (niveauVentilation < 2 || temperature < 10) {
            System.out.println("Conditions inadéquates pour maintenir la crypte.");
        } else {
            System.out.println("Budget de la crypte révisé avec succès.");
        }
    }

    public void ajusterTemperature(int nouvelleTemperature) {
        temperature = nouvelleTemperature;
        System.out.println("Température ajustée à " + temperature + "°C dans la crypte " + nom + ".");
    }

    public int getNiveauVentilation() {
        return niveauVentilation;
    }

    public int getTemperature() {
        return temperature;
    }
}



