package org.fantasy.hopitalfantastique;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class HopitalFantastique {
    private final String nom;
    private final int maxServices;
    private final List<ServiceMedical<? extends BaseCreature>> services;
    private final List<Medecin<? extends BaseCreature>> medecins;

    public HopitalFantastique(String nom, int maxServices) {
        this.nom = nom;
        this.maxServices = maxServices;
        this.services = new ArrayList<>();
        this.medecins = new ArrayList<>();
    }

    public void ajouterService(ServiceMedical<? extends BaseCreature> service) {
        if (services.size() < maxServices) {
            services.add(service);
            System.out.println("Service ajouté : " + service.getNom());
        } else {
            System.out.println("Capacité maximale de services atteinte.");
        }
    }

    public void ajouterMedecin(Medecin<? extends BaseCreature> medecin) {
        medecins.add(medecin);
        System.out.println("Médecin ajouté : " + medecin.getNom());
    }

    public void afficherNombreDeCreatures() {
        int totalCreatures = services.stream()
                .mapToInt(ServiceMedical::getNombreDeCreatures)
                .sum();
        System.out.println("Nombre total de créatures dans l'hôpital : " + totalCreatures);
    }

    public void afficherToutesLesCreatures() {
        System.out.println("\n--- Liste des créatures dans tous les services ---");
        for (ServiceMedical<? extends BaseCreature> service : services) {
            System.out.println("Service : " + service.getNom());
            service.afficherCaracteristiques();
        }
    }

    public void ajouterCreatureAuService(BaseCreature creature, int index) {
        if (index >= 0 && index < services.size()) {
            ServiceMedical<? extends BaseCreature> service = services.get(index);
            if (service.ajouterCreature(creature)) {
                System.out.println("Créature " + creature.getNom() + " ajoutée au service " + service.getNom());
            }
        } else {
            System.out.println("Index de service invalide.");
        }
    }

    public void lancerSimulation() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int intervalleTemps = 1;

        while (true) {
            System.out.println("\n--- Intervalle de temps : " + intervalleTemps + " ---");

            // Modifier l'état des créatures
            for (ServiceMedical<? extends BaseCreature> service : services) {
                for (BaseCreature creature : service.getCreatures()) {
                    if (random.nextDouble() < 0.3) { // 30% de chance de tomber malade
                        creature.tomberMalade("Maladie" + random.nextInt(10));
                    }
                    if (random.nextDouble() < 0.5) { // 50% de chance de perdre du moral
                        creature.attendre();
                    }
                }
            }

            // Modifier l'état des services
            for (ServiceMedical<? extends BaseCreature> service : services) {
                if (random.nextDouble() < 0.2) { // 20% de chance de réviser le budget
                    service.reviserBudget();
                }
                if (service instanceof CentreQuarantaine<?> quarantaine && random.nextDouble() < 0.1) {
                    quarantaine.modifierIsolation(!quarantaine.isIsolation());
                }
                if (service instanceof Crypte<?> crypte && random.nextDouble() < 0.1) {
                    crypte.ajusterTemperature(random.nextInt(5) + 15);
                }
            }

            // Interagir avec les médecins
            for (Medecin<? extends BaseCreature> medecin : medecins) {
                int actions = 3;
                while (actions > 0) {
                    System.out.println("\nMédecin " + medecin.getNom() + " : Actions restantes : " + actions);
                    System.out.println("1. Examiner un service médical");
                    System.out.println("2. Soigner les créatures dans un service médical");
                    System.out.println("3. Réviser le budget d'un service médical");
                    System.out.println("0. Passer au médecin suivant");

                    int choix = scanner.nextInt();
                    switch (choix) {
                        case 1 -> {
                            System.out.println("Examiner le service (0-" + (services.size() - 1) + ") : ");
                            int index = scanner.nextInt();
                            if (index >= 0 && index < services.size()) {
                                medecin.examinerService(services.get(index));
                            } else {
                                System.out.println("Service invalide.");
                            }
                            actions--;
                        }
                        case 2 -> {
                            System.out.println("Soigner les créatures du service (0-" + (services.size() - 1) + ") : ");
                            int index = scanner.nextInt();
                            if (index >= 0 && index < services.size()) {
                                medecin.soignerService(services.get(index));
                            } else {
                                System.out.println("Service invalide.");
                            }
                            actions--;
                        }
                        case 3 -> {
                            System.out.println("Réviser le budget du service (0-" + (services.size() - 1) + ") : ");
                        }
                    }
                }
            }
        }
    }
}


