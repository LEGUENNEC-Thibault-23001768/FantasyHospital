package org.fantasy.hopitalfantastique;
/**
 * Classe Medecin.
 * Représente un médecin capable de gérer les créatures dans les services médicaux.
 *
 * @param <T> Type de créature que ce médecin peut gérer (doit être une sous-classe de BaseCreature).
 */
public class Medecin<T extends BaseCreature> {
    private final String nom; // Nom du médecin
    private final SEXE sexe; // Sexe du médecin
    private final int age; // Âge du médecin

    /**
     * Constructeur de la classe Medecin.
     *
     * @param nom  Nom du médecin
     * @param sexe Sexe du médecin
     * @param age  Âge du médecin
     */
    public Medecin(String nom, SEXE sexe, int age) {
        this.nom = nom;
        this.sexe = sexe;
        this.age = age;
    }

    /**
     * Retourne le nom du médecin.
     *
     * @return Nom du médecin
     */
    public String getNom() {
        return nom;
    }

    /**
     * Examine les caractéristiques des créatures d'un service médical.
     *
     * @param service Service médical à examiner
     */
    public void examinerService(ServiceMedical<? extends BaseCreature> service) {
        System.out.println("Examen du service médical " + service.getNom() + " :");
        service.afficherCaracteristiques();
    }

    /**
     * Soigne les créatures dans un service médical.
     *
     * @param service Service médical à soigner
     */
    public void soignerService(ServiceMedical<? extends BaseCreature> service) {
        System.out.println("Soins des créatures dans le service " + service.getNom() + "...");
        service.soignerCreatures();
    }

    /**
     * Révise le budget d'un service médical.
     *
     * @param service Service médical dont le budget doit être révisé
     */
    public void reviserBudget(ServiceMedical<? extends BaseCreature> service) {
        System.out.println("Révision du budget pour le service " + service.getNom() + "...");
        service.reviserBudget();
    }

    /**
     * Transfère une créature d'un service médical à un autre.
     *
     * @param creature   Créature à transférer
     * @param source     Service source de la créature
     * @param destination Service destination pour la créature
     * @param <U>        Type de créature supportée par le service destination
     */
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
