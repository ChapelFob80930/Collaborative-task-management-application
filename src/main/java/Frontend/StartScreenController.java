package Frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartScreenController {

    @FXML
    private Button adminButton;

    @FXML
    private Button employeeButton;

    @FXML
    public void initialize() {
        adminButton.setOnAction(event -> handleAdminClick());
        employeeButton.setOnAction(event -> handleEmployeeClick());
    }

    private void handleAdminClick() {
        System.out.println("Admin selected. Navigate to admin dashboard.");
    }

    private void handleEmployeeClick() {
        System.out.println("Employee selected. Navigate to employee dashboard.");
    }
}
