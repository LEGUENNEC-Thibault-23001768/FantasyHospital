package org.fantasy.hopitalfantastique;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CrypteTest {
    @Test
    void testAjouterCreature() {
        Crypte<BaseCreature> crypte = new Crypte<>("Crypte froide et chaude", 500, 10, Budget.FAIBLE, 3, 15);
        BaseCreature vampire = new Vampire("Dracula", SEXE.HOMME, 500, 100, 0, 10);
        BaseCreature orque = new Orque("Chicken", SEXE.FEMME, 200, 150, 15, 10);

        assertTrue(crypte.ajouterCreature(vampire), "Le vampire devrait être ajouté");
        assertFalse(crypte.ajouterCreature(orque), "L'humain ne devrait pas pouvoir être ajouté");
    }

    @Test
    void testModifierTemperature() {
        Crypte<BaseCreature> crypte = new Crypte<>("Crypte de la chaleur froide ou pas", 500, 10, Budget.FAIBLE, 3, 15);
        crypte.modifierTemperature(5);

        assertEquals(5, crypte.getTemperature(), "La température de la crypte devrait être mise à jour");
    }

    @Test
    void testCapaciteMaximale() {
        Crypte<BaseCreature> crypte = new Crypte<>("Crypte des tenebres ambulant de marseille", 500, 2, Budget.FAIBLE, 3, 15);

        BaseCreature vampire1 = new Vampire("drac", SEXE.HOMME, 100, 50, 0, 5);
        BaseCreature vampire2 = new Vampire("ula", SEXE.FEMME, 150, 70, 0, 7);
        BaseCreature zombie = new Zombie("charclo", SEXE.HOMME, 50, 20, 0, 5);

        assertTrue(crypte.ajouterCreature(vampire1), "Le premier vampire devrait être ajouté");
        assertTrue(crypte.ajouterCreature(vampire2), "Le deuxième vampire devrait être ajouté");
        assertFalse(crypte.ajouterCreature(zombie), "Le zombie ne devrait pas être ajouté car la crypte est pleine");
    }

    @Test
    void testEnleverCreature() {
        Crypte<BaseCreature> crypte = new Crypte<>("Crypte du cheval flottant", 500, 10, Budget.FAIBLE, 3, 15);

        BaseCreature vampire = new Vampire("kirikou", SEXE.HOMME, 500, 100, 0, 10);

        crypte.ajouterCreature(vampire);

        assertTrue(crypte.enleverCreature(vampire), "Le vampire devrait être retiré");
        assertFalse(crypte.enleverCreature(vampire), "Le vampire ne devrait plus être présent");
    }

}
