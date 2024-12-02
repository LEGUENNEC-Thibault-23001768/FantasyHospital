package org.fantasy.hopitalfantastique;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CentreQuarantaineTest {
    private CentreQuarantaine<BaseCreature> quarantaine;
    private Zombie zombie;
    private Elfe elfe;

    @BeforeEach
    void setUp() {
        quarantaine = new CentreQuarantaine<>("Quarantaine Beta", 150, 2, Budget.INSUFFISANT, false);
        zombie = new Zombie("Walker", SEXE.HOMME, 50, 1.75, 25, 5);
        elfe = new Elfe("Legolas", SEXE.HOMME, 60, 1.80, 150, 8);
    }

    @Test
    void testAjoutCreature() {
        zombie.tomberMalade("FOMO"); // Zombie devient contagieux
        assertTrue(quarantaine.ajouterCreature(zombie), "Le zombie devrait pouvoir être ajouté car il est contagieux.");
        assertFalse(quarantaine.ajouterCreature(elfe), "L'elfe ne devrait pas pouvoir être ajouté car il n'est pas contagieux.");
    }

    @Test
    void testModifierIsolation() {
        quarantaine.modifierIsolation(true);
        assertTrue(quarantaine.isIsolation(), "L'isolation devrait être activée.");
        quarantaine.modifierIsolation(false);
        assertFalse(quarantaine.isIsolation(), "L'isolation devrait être désactivée.");
    }
}
