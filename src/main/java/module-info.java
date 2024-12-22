module org.cosmos.collaborativetaskmanagementapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.cosmos.collaborativetaskmanagementapplication to javafx.fxml;
    exports org.cosmos.collaborativetaskmanagementapplication;
}