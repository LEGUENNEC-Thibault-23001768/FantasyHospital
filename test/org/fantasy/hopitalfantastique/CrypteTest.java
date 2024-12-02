package org.fantasy.hopitalfantastique;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrypteTest {
    private Crypte<BaseCreature> crypte;
    private Vampire vampire;
    private Zombie zombie;
    private Elfe elfe;

    @BeforeEach
    void setUp() {
        crypte = new Crypte<>("Crypte Alpha", 100, 2, Budget.FAIBLE, 2, 15);
        vampire = new Vampire("Vlad", SEXE.HOMME, 70, 1.85, 300, 10);
        zombie = new Zombie("Walker", SEXE.HOMME, 50, 1.75, 25, 5);
        elfe = new Elfe("Legolas", SEXE.HOMME, 60, 1.80, 150, 8);
    }

    @Test
    void testAjoutCreature() {
        assertTrue(crypte.ajouterCreature(vampire), "Le vampire devrait pouvoir être ajouté.");
        assertTrue(crypte.ajouterCreature(zombie), "Le zombie devrait pouvoir être ajouté.");
        assertFalse(crypte.ajouterCreature(elfe), "L'elfe ne devrait pas pouvoir être ajouté car il ne se régénère pas.");
    }

    @Test
    void testReviserBudget() {
        crypte.reviserBudget();
        // La sortie console vérifiera si le budget est révisé correctement.
    }

    @Test
    void testAjusterTemperature() {
        crypte.ajusterTemperature(12);
        assertEquals(12, crypte.getTemperature(), "La température devrait être ajustée à 12.");
    }
}
