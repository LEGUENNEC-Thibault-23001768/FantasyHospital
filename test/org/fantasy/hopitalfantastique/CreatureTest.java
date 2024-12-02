package org.fantasy.hopitalfantastique;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreatureTest {
    private Vampire vampire;
    private Zombie zombie;
    private Orque orque;

    @BeforeEach
    void setUp() {
        vampire = new Vampire("Vlad", SEXE.HOMME, 70, 1.85, 300, 10);
        zombie = new Zombie("Walker", SEXE.HOMME, 50, 1.75, 25, 5);
        orque = new Orque("Gruk", SEXE.HOMME, 120, 2.0, 40, 7);
    }

    @Test
    void testAttendre() {
        vampire.attendre();
        assertEquals(9, vampire.getMoral(), "Le moral devrait diminuer après avoir attendu.");
    }

    @Test
    void testContaminer() {
        zombie.tomberMalade("FOMO");
        //zombie.contaminer();
    }

    @Test
    void testRegeneration() {
        zombie.trépasser();
        zombie.régénérer();
        assertEquals(10, zombie.getMoral(), "Le zombie devrait être régénéré avec un moral restauré.");
    }

    @Test
    void testDémoraliser() {
        vampire.trépasser();
        vampire.démoraliser();
        // Ce test nécessite un service médical pour vérifier l'effet sur les autres créatures.
    }
}
