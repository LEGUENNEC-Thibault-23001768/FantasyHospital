module org.fantasy.hopitalfantastique {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;

    opens org.fantasy.hopitalfantastique to javafx.fxml;
    exports org.fantasy.hopitalfantastique;
}