package org.fantasy.hopitalfantastique;
/**
 * Énumération MALADIES.
 * Représente les maladies possibles avec leur nom complet et leur nom court.
 */
enum MALADIES {
    MDC("Maladie Débilitante Chronique", "MDC"),
    FOMO("Syndrome Fear of Missing Out", "FOMO"),
    DRS("Dépendance aux Réseaux Sociaux", "DRS"),
    PEC("Porphyrie Érythropoïétique Congénitale", "PEC"),
    ZPL("Zoopathie Paraphrénique Lycanthropique", "ZPL"),
    NDMAD("NDMAD JSP", "NDMAD"),
    BG("Beau Gosse", "BG"),
    DEFINIR("À Définir", "DEFINIR");

    private final String nomComplet; // Nom complet de la maladie
    private final String nomCourt; // Nom court de la maladie

    MALADIES(String nomComplet, String nomCourt) {
        this.nomComplet = nomComplet;
        this.nomCourt = nomCourt;
    }

    /**
     * Récupère le nom complet de la maladie.
     * @return Nom complet
     */
    public String getNomComplet() {
        return nomComplet;
    }

    /**
     * Récupère le nom court de la maladie.
     * @return Nom court
     */
    public String getNomCourt() {
        return nomCourt;
    }
}

/**
 * Énumération NIVEAU_MALADIE.
 * Définit les niveaux de gravité d'une maladie.
 */
enum NIVEAU_MALADIE {
    NUL,       // Aucun impact
    SEVERE,    // Impact sévère
    LETAL      // Impact mortel
}

/**
 * Classe Maladie.
 * Représente une maladie associée à une créature avec son niveau de gravité.
 */
public class Maladie {
    private final MALADIES maladie; // Type de maladie
    private NIVEAU_MALADIE niveau; // Niveau de gravité

    /**
     * Constructeur avec une maladie spécifique.
     * @param maladie Type de maladie
     */
    public Maladie(MALADIES maladie) {
        this.maladie = maladie;
        this.niveau = NIVEAU_MALADIE.NUL; // Niveau initial
    }

    /**
     * Constructeur avec le nom d'une maladie.
     * @param nomMaladie Nom de la maladie
     */
    public Maladie(String nomMaladie) {
        this.maladie = MALADIES.valueOf(nomMaladie.toUpperCase());
        this.niveau = NIVEAU_MALADIE.NUL;
    }

    /**
     * Récupère le nom complet de la maladie.
     * @return Nom complet
     */
    public String getNomComplet() {
        return maladie.getNomComplet();
    }

    /**
     * Récupère le nom court de la maladie.
     * @return Nom court
     */
    public String getNomCourt() {
        return maladie.getNomCourt();
    }

    /**
     * Récupère le niveau de gravité actuel.
     * @return Niveau de gravité
     */
    public NIVEAU_MALADIE getNiveau() {
        return niveau;
    }

    /**
     * Diminue le niveau de gravité d'une maladie.
     */
    public void diminuerNiveau() {
        if (this.niveau.ordinal() > 0) {
            this.niveau = NIVEAU_MALADIE.values()[this.niveau.ordinal() - 1];
        }
    }

    /**
     * Augmente le niveau de gravité d'une maladie.
     */
    public void augmenterNiveau() {
        if (this.niveau.ordinal() < NIVEAU_MALADIE.values().length - 1) {
            this.niveau = NIVEAU_MALADIE.values()[this.niveau.ordinal() + 1];
        }
    }

    /**
     * Change le niveau de gravité de la maladie.
     * @param nouveauNiveau Nouveau niveau à appliquer
     * @throws IllegalArgumentException Si le niveau est invalide
     */
    public void changerNiveau(NIVEAU_MALADIE nouveauNiveau) {
        if (nouveauNiveau.ordinal() >= 0 && nouveauNiveau.ordinal() <= this.niveau.ordinal()) {
            this.niveau = nouveauNiveau;
        } else {
            throw new IllegalArgumentException("Niveau de maladie invalide.");
        }
    }

    /**
     * Vérifie si le niveau est létal.
     * @return true si létal, false sinon
     */
    public boolean estLethal() {
        return this.niveau == NIVEAU_MALADIE.LETAL;
    }

    /**
     * Retourne une représentation de la maladie.
     * @return Description de la maladie
     */
    @Override
    public String toString() {
        return maladie.getNomComplet() + " (Niveau: " + niveau + ")";
    }

    /**
     * Vérifie l'égalité entre deux maladies.
     * @param obj Objet à comparer
     * @return true si égal, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Maladie)) return false;
        Maladie other = (Maladie) obj;
        return this.maladie == other.maladie && this.niveau == other.niveau;
    }

    /**
     * Génère un hashCode pour la maladie.
     * @return HashCode calculé
     */
    @Override
    public int hashCode() {
        return maladie.hashCode() * 31 + niveau.hashCode();
    }
}
