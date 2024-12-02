package org.fantasy.hopitalfantastique;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaminationTest {
    private Orque orque;
    private Zombie zombie;
    private Lycanthrope lycanthrope;
    private ServiceMedical<BaseCreature> serviceMedical;

    @BeforeEach
    void setUp() {
        orque = new Orque("Gruk", SEXE.HOMME, 120, 2.0, 40, 7);
        zombie = new Zombie("Walker", SEXE.HOMME, 50, 1.75, 25, 5);
        lycanthrope = new Lycanthrope("Fenrir", SEXE.HOMME, 90, 1.85, 30, 8);
        serviceMedical = new CentreQuarantaine<>("Quarantaine Beta", 150, 3, Budget.FAIBLE, true);
        serviceMedical.ajouterCreature(orque);
        serviceMedical.ajouterCreature(zombie);
        serviceMedical.ajouterCreature(lycanthrope);
    }

    @Test
    void testOrqueContamination() {
        orque.tomberMalade("FOMO");
        orque.contaminer();
        assertFalse(zombie.getMaladies().isEmpty(), "Le zombie devrait être contaminé par l'orque.");
        assertEquals("FOMO", zombie.getMaladies().get(0).getNomCourt(), "Le zombie devrait avoir la maladie FOMO.");
        assertFalse(lycanthrope.getMaladies().isEmpty(), "Le lycanthrope devrait être contaminé par l'orque.");
        assertEquals("FOMO", lycanthrope.getMaladies().get(0).getNomCourt(), "Le lycanthrope devrait avoir la maladie FOMO.");
    }

    @Test
    void testLycanthropeContamination() {
        lycanthrope.tomberMalade("PEC");
        lycanthrope.contaminer();
        assertFalse(zombie.getMaladies().isEmpty(), "Le zombie devrait être contaminé par le lycanthrope.");
        assertEquals("PEC", zombie.getMaladies().get(0).getNomCourt(), "Le zombie devrait avoir la maladie PEC.");
    }
}
