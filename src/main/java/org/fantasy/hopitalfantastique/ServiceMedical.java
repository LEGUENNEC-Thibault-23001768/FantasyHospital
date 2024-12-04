package org.fantasy.hopitalfantastique;

import java.util.ArrayList;
import java.util.List;

/**
 * Énumération Budget.
 * Définit les différents niveaux de budget pour un service médical.
 */
enum Budget {
    INEXISTANT,   // Aucun budget
    MEDIOCRE,     // Budget très faible
    INSUFFISANT,  // Budget insuffisant
    FAIBLE        // Budget minimal acceptable
}

/**
 * Classe abstraite ServiceMedical.
 * Représente un service médical générique pour la gestion des créatures.
 *
 * @param <T> Type de créatures gérées par ce service (doit être une sous-classe de BaseCreature).
 */
abstract class ServiceMedical<T extends BaseCreature> {
    protected final String nom; // Nom du service
    protected final double superficie; // Superficie du service en m²
    protected final int capaciteMax; // Capacité maximale du service
    protected final List<T> creatures; // Liste des créatures dans le service
    protected Budget budget; // Niveau de budget du service

    /**
     * Constructeur de ServiceMedical.
     *
     * @param nom          Nom du service
     * @param superficie   Superficie du service
     * @param capaciteMax  Capacité maximale
     * @param budget       Niveau de budget
     */
    public ServiceMedical(String nom, double superficie, int capaciteMax, Budget budget) {
        this.nom = nom;
        this.superficie = superficie;
        this.capaciteMax = capaciteMax;
        this.budget = budget;
        this.creatures = new ArrayList<>();
    }

    /**
     * Récupère le nom du service.
     * @return Nom du service
     */
    public String getNom() {
        return nom;
    }

    /**
     * Récupère la liste des créatures dans le service.
     * @return Liste des créatures
     */
    public List<T> getCreatures() {
        return creatures;
    }

    /**
     * Ajoute une créature au service.
     *
     * @param creature Créature à ajouter
     * @return true si l'ajout est réussi, false sinon
     */
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

    /**
     * Retire une créature du service.
     *
     * @param creature Créature à retirer
     * @return true si la créature a été retirée, false sinon
     */
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

    /**
     * Affiche les caractéristiques du service.
     */
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

    /**
     * Révise le budget du service.
     */
    public abstract void reviserBudget();

    /**
     * Soigne toutes les créatures du service.
     */
    public void soignerCreatures() {
        for (T creature : creatures) {
            creature.soigner();
        }
    }

    /**
     * Récupère le nombre de créatures dans le service.
     * @return Nombre de créatures
     */
    public int getNombreDeCreatures() {
        return creatures.size();
    }
}

/**
 * Classe CentreQuarantaine.
 * Représente un service médical dédié à l'isolation des créatures contagieuses.
 *
 * @param <T> Type de créatures gérées par ce service
 */
class CentreQuarantaine<T extends BaseCreature> extends ServiceMedical<T> {
    private boolean isolation; // État de l'isolation

    /**
     * Constructeur de CentreQuarantaine.
     *
     * @param nom          Nom du centre
     * @param superficie   Superficie du centre
     * @param capaciteMax  Capacité maximale
     * @param budget       Niveau de budget
     * @param isolation    État de l'isolation
     */
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

    /**
     * Modifie l'état de l'isolation.
     *
     * @param etat Nouvel état de l'isolation
     */
    public void modifierIsolation(boolean etat) {
        this.isolation = etat;
        System.out.println("Isolation du " + getNom() + " : " + (isolation ? "Activée" : "Désactivée"));
    }

    /**
     * Vérifie si l'isolation est activée.
     *
     * @return true si l'isolation est activée, false sinon
     */
    public boolean isIsolation() {
        return isolation;
    }
}

/**
 * Classe Crypte.
 * Représente un service médical pour les créatures pouvant se régénérer.
 *
 * @param <T> Type de créatures gérées par ce service
 */
class Crypte<T extends BaseCreature> extends ServiceMedical<T> {
    private int niveauVentilation; // Niveau de ventilation
    private int temperature; // Température actuelle

    /**
     * Constructeur de Crypte.
     *
     * @param nom               Nom de la crypte
     * @param superficie        Superficie de la crypte
     * @param capaciteMax       Capacité maximale
     * @param budget            Niveau de budget
     * @param niveauVentilation Niveau de ventilation
     * @param temperature       Température
     */
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

    /**
     * Ajuste la température de la crypte.
     *
     * @param nouvelleTemperature Nouvelle température
     */
    public void ajusterTemperature(int nouvelleTemperature) {
        temperature = nouvelleTemperature;
        System.out.println("Température ajustée à " + temperature + "°C dans la crypte " + nom + ".");
    }

    /**
     * Récupère le niveau de ventilation.
     * @return Niveau de ventilation
     */
    public int getNiveauVentilation() {
        return niveauVentilation;
    }

    /**
     * Récupère la température actuelle.
     * @return Température actuelle
     */
    public int getTemperature() {
        return temperature;
    }
}
