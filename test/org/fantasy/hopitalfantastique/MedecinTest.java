package org.fantasy.hopitalfantastique;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


class MedecinTest {

    @Test
    void testExaminerService() {
        Crypte<BaseCreature> crypte = new Crypte<>("Crypte froide et chaude", 500, 10, Budget.FAIBLE, 3, 15);
        Medecin<BaseCreature> marcus = new Medecin<>("le médécin", SEXE.HOMME, 19);

        crypte.ajouterCreature(new Vampire("polo", SEXE.HOMME, 500, 100, 0, 10));
        crypte.ajouterCreature(new Vampire("salut", SEXE.HOMME, 50, 20, 0, 5));

        marcus.examinerService(crypte);
    }

    @Test
    void testSoignerService() {
        Medecin<BaseCreature> medecin = new Medecin<>("Frank", SEXE.HOMME, 45);
        ServiceMedical<BaseCreature> crypte = new Crypte<>("crpto", 500, 10, Budget.FAIBLE, 3, 15);

        Zombie zomba = new Zombie("droc", SEXE.HOMME, 500, 50, 0, 10);
        Zombie zombie = new Zombie("gpludidé", SEXE.HOMME, 50, 20, 0, 8);

        crypte.ajouterCreature(zomba);
        crypte.ajouterCreature(zombie);

        medecin.soignerService(crypte);

        for (BaseCreature creature : crypte.getCreatures()) {
            System.out.println("Créature : " + creature.getNom() + ", Moral après soin : " + creature.getMoral());
        }

        assertEquals(10, zomba.getMoral(), "Le moral de zomba devrait être à 10 après les soins");
        assertEquals(10, zombie.getMoral(), "Le moral de zombie devrait être à 10 après les soins");
    }


}
