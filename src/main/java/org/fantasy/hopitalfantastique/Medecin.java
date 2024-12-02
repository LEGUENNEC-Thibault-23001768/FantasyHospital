package org.fantasy.hopitalfantastique;

class Medecin<T extends BaseCreature> {
    private final String nom;
    private final SEXE sexe;
    private final int age;

    public Medecin(String nom, SEXE sexe, int age) {
        this.nom = nom;
        this.sexe = sexe;
        this.age = age;
    }

    public String getNom() {
        return nom;
    }

    public void examinerService(ServiceMedical<? extends BaseCreature> service) {
        System.out.println("Examen du service médical " + service.getNom() + " :");
        service.afficherCaracteristiques();
    }

    public void soignerService(ServiceMedical<? extends BaseCreature> service) {
        System.out.println("Soins des créatures dans le service " + service.getNom() + "...");
        service.soignerCreatures();
    }

    public void reviserBudget(ServiceMedical<? extends BaseCreature> service) {
        System.out.println("Révision du budget pour le service " + service.getNom() + "...");
        service.reviserBudget();
    }

    public <U extends BaseCreature> void transfererCreature(
            T creature,
            ServiceMedical<? super T> source,
            ServiceMedical<? super U> destination) {
        if (destination.getCreatures().isEmpty() ||
                destination.getCreatures().get(0).getClass().isAssignableFrom(creature.getClass())) {
            if (source.enleverCreature(creature)) {
                if (!destination.ajouterCreature(creature)) {
                    System.out.println("Échec du transfert : le service " + destination.getNom() + " est complet.");
                    source.ajouterCreature(creature); // Replacer la créature dans le service source
                } else {
                    System.out.println(creature.getNom() + " a été transféré de " + source.getNom() + " à " + destination.getNom() + ".");
                }
            } else {
                System.out.println("La créature " + creature.getNom() + " n'est pas présente dans le service " + source.getNom() + ".");
            }
        } else {
            System.out.println("Erreur : Les services gèrent des types incompatibles.");
        }
    }
}
