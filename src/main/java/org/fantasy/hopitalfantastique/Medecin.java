package org.fantasy.hopitalfantastique;

class Medecin<T extends BaseCreature> extends BaseCreature {
    public Medecin(String nom, SEXE sexe, int age) {
        super(nom, sexe, 0, 0, age, 0);
    }
    public void examinerService(ServiceMedical<? extends BaseCreature> service) {
        System.out.println("Examen du service médical :");
        service.afficherCaracteristiques();
    }

    public void soignerService(ServiceMedical<? extends BaseCreature> service) {
        System.out.println("Soins des créatures dans le service " + service.nom + "...");
        service.soignerCreatures();
    }

    public void reviserBudget(ServiceMedical<? extends BaseCreature> service) {
        System.out.println("Révision du budget pour le service " + service.nom + "...");
        service.reviserBudget();
    }

    public <U extends BaseCreature> void transfererCreature(T creature, ServiceMedical<T> source, ServiceMedical<U> destination) {
        if (!destination.getClass().equals(source.getClass())) {
            System.out.println("Erreur : Impossible de transférer, les services contiennent des types de créatures différents");
            return;
        }

        if (source.enleverCreature(creature)) {
            if (destination.ajouterCreature((U) creature)) {
                System.out.println(creature.nom + " a été transféré de " + source.nom + " à " + destination.nom);
            } else {
                System.out.println("Échec du transfert : le service " + destination.nom + " est complet");
                source.ajouterCreature(creature);
            }
        } else {
            System.out.println("La créature " + creature.nom + " n'est pas présente dans le service " + source.nom);
        }
    }

    @Override
    public void demoraliser(){};
    @Override
    public void contaminer(){};
    @Override
    public void regenerer(){};
}

