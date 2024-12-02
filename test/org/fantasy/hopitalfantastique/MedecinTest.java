package org.fantasy.hopitalfantastique;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedecinTest {
    private Medecin<BaseCreature> medecin;
    private ServiceMedical<BaseCreature> service1;
    private ServiceMedical<BaseCreature> service2;
    private Vampire vampire;

    @BeforeEach
    void setUp() {
        medecin = new Medecin<>("Dr. Strange", SEXE.HOMME, 45);
        service1 = new Crypte<>("Crypte Alpha", 100, 2, Budget.FAIBLE, 2, 15);
        service2 = new CentreQuarantaine<>("Quarantaine Beta", 150, 10, Budget.INSUFFISANT, false);
        vampire = new Vampire("Vlad", SEXE.HOMME, 70, 1.85, 300, 10);
    }

    @Test
    void testExaminerService() {
        service1.ajouterCreature(vampire);
        medecin.examinerService(service1);
        // Vérifiez la sortie console pour confirmer l'examen.
    }

    @Test
    void testSoignerService() {
        vampire.tomberMalade("FOMO");
        service1.ajouterCreature(vampire);
        medecin.soignerService(service1);
        assertTrue(vampire.getMaladies().isEmpty(), "Le vampire devrait être guéri après les soins.");
    }

    @Test
    void testTransfererCreature() {
        vampire.tomberMalade("FOMO"); // Ajout d'une maladie pour rendre le vampire contagieux
        assertTrue(service1.ajouterCreature(vampire), "Le vampire devrait être ajouté au service 1.");
        medecin.transfererCreature(vampire, service1, service2);
        assertTrue(service2.getCreatures().contains(vampire), "Le vampire devrait être dans le service 2 après le transfert.");
        assertFalse(service1.getCreatures().contains(vampire), "Le vampire ne devrait plus être dans le service 1 après le transfert.");
    }

}
