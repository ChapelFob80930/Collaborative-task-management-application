module org.cosmos.collaborativetaskmanagementapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires java.sql;

    opens Frontend to javafx.fxml;
    exports Frontend;
    exports Backend;
    opens Backend to javafx.fxml;
}