package org.fantasy.hopitalfantastique;

enum MALADIES {
    MDC("Maladie Débilitante Chronique", "MDC"),
    FOMO("Syndrome Fear of Missing Out", "FOMO"),
    DRS("Dépendance aux Réseaux Sociaux", "DRS"),
    PEC("Porphyrie Érythropoïétique Congénitale", "PEC"),
    ZPL("Zoopathie Paraphrénique Lycanthropique", "ZPL"),
    NDMAD("NDMAD JSP", "NDMAD"),
    BG("Beau Gosse", "BG"),
    DEFINIR("À Définir", "DEFINIR");

    private final String nomComplet;
    private final String nomCourt;

    MALADIES(String nomComplet, String nomCourt) {
        this.nomComplet = nomComplet;
        this.nomCourt = nomCourt;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public String getNomCourt() {
        return nomCourt;
    }
}

enum NIVEAU_MALADIE {
    NUL,
    SEVERE,
    LETAL
}

public class Maladie {
    private final MALADIES maladie;
    private NIVEAU_MALADIE niveau;

    public Maladie(MALADIES maladie) {
        this.maladie = maladie;
        this.niveau = NIVEAU_MALADIE.NUL; // Niveau initial
    }

    public Maladie(String nomMaladie) {
        this.maladie = MALADIES.valueOf(nomMaladie.toUpperCase());
        this.niveau = NIVEAU_MALADIE.NUL;
    }

    public String getNomComplet() {
        return maladie.getNomComplet();
    }

    public String getNomCourt() {
        return maladie.getNomCourt();
    }

    public NIVEAU_MALADIE getNiveau() {
        return niveau;
    }

    public void diminuerNiveau() {
        if (this.niveau.ordinal() > 0) {
            this.niveau = NIVEAU_MALADIE.values()[this.niveau.ordinal() - 1];
        }
    }

    public void augmenterNiveau() {
        if (this.niveau.ordinal() < NIVEAU_MALADIE.values().length - 1) {
            this.niveau = NIVEAU_MALADIE.values()[this.niveau.ordinal() + 1];
        }
    }

    public void changerNiveau(NIVEAU_MALADIE nouveauNiveau) {
        if (nouveauNiveau.ordinal() >= 0 && nouveauNiveau.ordinal() <= this.niveau.ordinal()) {
            this.niveau = nouveauNiveau;
        } else {
            throw new IllegalArgumentException("Niveau de maladie invalide.");
        }
    }

    public boolean estLethal() {
        return this.niveau == NIVEAU_MALADIE.LETAL;
    }

    @Override
    public String toString() {
        return maladie.getNomComplet() + " (Niveau: " + niveau + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Maladie)) return false;
        Maladie other = (Maladie) obj;
        return this.maladie == other.maladie && this.niveau == other.niveau;
    }

    @Override
    public int hashCode() {
        return maladie.hashCode() * 31 + niveau.hashCode();
    }
}
