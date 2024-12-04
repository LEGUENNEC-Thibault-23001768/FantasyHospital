/**
 * Module principal pour la simulation de l'hôpital fantastique.
 */
module org.fantasy.hopitalfantastique {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;

    opens org.fantasy.hopitalfantastique to com.almasb.fxgl.core;
    exports org.fantasy.hopitalfantastique;
}