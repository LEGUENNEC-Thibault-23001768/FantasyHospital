package org.fantasy.hopitalfantastique;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

interface BaseComportement {
    void attendre();
    void hurler();
    void sEmporter();
    void tomberMalade(String nomMaladie);
    void soigner();
    void trépasser();
}
interface ComportementDemoralisation extends BaseComportement {
    void démoraliser(); // Affecte le moral des autres créatures
}

interface ComportementContamination extends BaseComportement {
    void contaminer(); // Transmet une maladie à une autre créature
}

interface ComportementRegeneration extends BaseComportement {
    void régénérer(); // Restaure l'état après trépas
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


abstract class BaseCreature implements BaseComportement {
    protected String nom;
    protected SEXE sexe;
    protected double poids;
    protected double taille;
    protected int age;
    protected int moral;

    public List<Maladie> getMaladies() {
        return maladies;
    }

    public void setMaladies(List<Maladie> maladies) {
        this.maladies = maladies;
    }

    protected List<Maladie> maladies;

    protected ServiceMedical<?> serviceMedical;

    public BaseCreature(String nom, SEXE sexe, double poids, double taille, int age, int moral) {
        this.nom = nom;
        this.sexe = sexe;
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.moral = moral;
        this.maladies = new ArrayList<>();
    }

    public void setServiceMedical(ServiceMedical<?> service) {
        this.serviceMedical = service;
    }

    public String getNom() {
        return nom;
    }

    public int getMoral() {
        return moral;
    }

    public void afficherEtat() {
        System.out.println("État de " + nom + " :");
        System.out.println("  Moral : " + moral);
        System.out.println("  Maladies : " + (maladies.isEmpty() ? "Aucune" : maladies.stream()
                .map(Maladie::getNomCourt)
                .collect(Collectors.joining(", "))));
    }

    @Override
    public void attendre() {
        moral = Math.max(0, moral - 1);
        System.out.println(nom + " attend.");
    }

    @Override
    public void hurler() {
        if (moral <= 3) {
            System.out.println(nom + " hurle désespérément !");
        }
    }

    @Override
    public void sEmporter() {
        if (moral <= 3) {
            System.out.println(nom + " s'emporte violemment !");
        }
    }

    @Override
    public void tomberMalade(String nomMaladie) {
        Maladie maladie = new Maladie(MALADIES.valueOf(nomMaladie.toUpperCase()));
        maladies.add(maladie);
        moral = Math.max(0, moral - 2);
        System.out.println(nom + " est tombé malade de " + maladie.getNomComplet() + ".");
    }

    @Override
    public void soigner() {
        if (!maladies.isEmpty()) {
            Maladie maladie = maladies.remove(0);
            moral = Math.min(10, moral + 2);
            System.out.println(nom + " a été soigné de " + maladie.getNomComplet());
        } else {
            System.out.println(nom + " n'est pas malade.");
        }
    }

    @Override
    public void trépasser() {
        System.out.println(nom + " a trépassé...");
    }
}

// =================================== LES AUTRES CREATURES ===================================

class Elfe extends BaseCreature implements ComportementDemoralisation {
    public Elfe(String nom, SEXE sexe, double poids, double taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
    }

    @Override
    public void démoraliser() {
        if (serviceMedical != null) {
            for (BaseCreature creature : serviceMedical.getCreatures()) {
                creature.moral = Math.max(0, creature.moral - 1);
            }
            System.out.println("Les créatures du service médical sont démoralisées par le trépas de " + nom + ".");
        }
    }
}

class Nain extends BaseCreature {
    public Nain(String nom, SEXE sexe, double poids, double taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
    }

    @Override
    public void attendre() {
        moral = Math.max(0, moral - 2);
        System.out.println(nom + " (VIP) s'impatiente rapidement !");
    }
}


class Orque extends BaseCreature implements ComportementContamination {
    public Orque(String nom, SEXE sexe, double poids, double taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
    }

    @Override
    public void attendre() {
        if (serviceMedical != null && serviceMedical.getCreatures().stream().anyMatch(c -> c instanceof Orque)) {
            moral = Math.max(0, moral - 1); // Attente moins stressante
            System.out.println(nom + " attend patiemment avec ses semblables.");
        } else {
            moral = Math.max(0, moral - 2);
            System.out.println(nom + " attend nerveusement.");
        }
    }

    @Override
    public void contaminer() {
        if (serviceMedical != null) {
            if (maladies.isEmpty()) {
                System.out.println(nom + " ne peut pas contaminer car il n'a pas de maladies.");
                return;
            }
            for (BaseCreature creature : serviceMedical.getCreatures()) {
                if (!creature.equals(this)) {
                    Maladie maladie = maladies.get(0); // Transmettre la première maladie
                    creature.tomberMalade(maladie.getNomCourt());
                    System.out.println(nom + " a contaminé " + creature.getNom() + " avec " + maladie.getNomComplet() + ".");
                }
            }
        } else {
            System.out.println(nom + " n'est pas dans un service médical.");
        }
    }
}



class HommeBete extends BaseCreature implements ComportementContamination {
    public HommeBete(String nom, SEXE sexe, double poids, double taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
    }

    @Override
    public void contaminer() {
        if (serviceMedical != null) {
            for (BaseCreature creature : serviceMedical.getCreatures()) {
                if (!creature.equals(this)) {
                    Maladie maladie = maladies.isEmpty() ? null : maladies.get(0);
                    if (maladie != null) {
                        creature.tomberMalade(maladie.getNomCourt());
                        System.out.println(nom + " a contaminé " + creature.getNom() + " avec " + maladie.getNomComplet() + ".");
                    }
                }
            }
        }
    }
}


class Zombie extends BaseCreature implements ComportementRegeneration, ComportementContamination {
    public Zombie(String nom, SEXE sexe, double poids, double taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
    }

    @Override
    public void régénérer() {
        moral = Math.min(10, moral + 5);
        System.out.println(nom + " s'est régénéré après trépas !");
    }

    @Override
    public void contaminer() {
        if (serviceMedical != null) {
            for (BaseCreature creature : serviceMedical.getCreatures()) {
                if (!creature.equals(this)) {
                    Maladie maladie = maladies.isEmpty() ? null : maladies.get(0);
                    if (maladie != null) {
                        creature.tomberMalade(maladie.getNomCourt());
                        System.out.println(nom + " a contaminé " + creature.getNom() + " avec " + maladie.getNomComplet() + ".");
                    }
                }
            }
        }
    }

    @Override
    public void attendre() {
        if (serviceMedical != null && serviceMedical.getCreatures().stream().anyMatch(c -> c instanceof Zombie)) {
            moral = Math.max(0, moral - 1); // Attente moins stressante
            System.out.println(nom + " attend patiemment avec ses semblables.");
        } else {
            moral = Math.max(0, moral - 2);
            System.out.println(nom + " attend nerveusement.");
        }
    }
}



class Vampire extends BaseCreature implements ComportementContamination, ComportementDemoralisation, ComportementRegeneration {
    public Vampire(String nom, SEXE sexe, double poids, double taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
    }

    @Override
    public void contaminer() {
        if (serviceMedical != null) {
            for (BaseCreature creature : serviceMedical.getCreatures()) {
                if (!creature.equals(this)) {
                    Maladie maladie = maladies.isEmpty() ? null : maladies.get(0);
                    if (maladie != null) {
                        creature.tomberMalade(maladie.getNomCourt());
                        System.out.println(nom + " a contaminé " + creature.getNom() + " avec " + maladie.getNomComplet() + ".");
                    }
                }
            }
        }
    }

    @Override
    public void démoraliser() {
        if (serviceMedical != null) {
            for (BaseCreature creature : serviceMedical.getCreatures()) {
                creature.moral = Math.max(0, creature.moral - 2);
            }
            System.out.println("Les créatures du service médical sont profondément démoralisées par le trépas de " + nom + ".");
        }
    }

    @Override
    public void régénérer() {
        moral = Math.min(10, moral + 5);
        System.out.println(nom + " s'est régénéré après trépas !");
    }
}


class Lycanthrope extends BaseCreature implements ComportementContamination {
    public Lycanthrope(String nom, SEXE sexe, double poids, double taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
    }

    @Override
    public void contaminer() {
        if (serviceMedical != null) {
            for (BaseCreature creature : serviceMedical.getCreatures()) {
                if (!creature.equals(this)) {
                    Maladie maladie = maladies.isEmpty() ? null : maladies.get(0);
                    if (maladie != null) {
                        creature.tomberMalade(maladie.getNomCourt());
                        System.out.println(nom + " a contaminé " + creature.getNom() + " avec " + maladie.getNomComplet() + ".");
                    }
                }
            }
        }
    }
}


class Reptilien extends BaseCreature {
    public Reptilien(String nom, SEXE sexe, double poids, double taille, int age, int moral) {
        super(nom, sexe, poids, taille, age, moral);
    }

    @Override
    public void attendre() {
        moral = Math.max(0, moral - 2);
        System.out.println(nom + " (VIP) s'impatiente rapidement !");
    }
}

