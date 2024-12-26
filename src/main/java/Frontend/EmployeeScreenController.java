package Frontend;

import Backend.Employee;
import Backend.SQLAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class EmployeeScreenController {

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, Integer> employeeIdColumn;

    @FXML
    private TableColumn<Employee, String> employeeNameColumn;

    @FXML
    private TableColumn<Employee, String> employeeEmailColumn;

    @FXML
    private TableColumn<Employee, String> employeePhoneColumn;

    @FXML
    private TableColumn<Employee, String> employeeRoleColumn;

    @FXML
    private TableColumn<Employee, String> employeeProjectsColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private ChoiceBox<String> roleBox;

    @FXML
    private TextField searchField;

    @FXML
    private TextArea historyArea;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    private SQLAccess sqlAccess;
    private ObservableList<Employee> employeeList;

    @FXML
    public void initialize() throws SQLException {
        // Initialize SQLAccess
        sqlAccess = new SQLAccess();

        // Set up TableView columns
        employeeIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        employeeNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        employeeEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        employeePhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        employeeRoleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        employeeProjectsColumn.setCellValueFactory(cellData -> cellData.getValue().projectsProperty());

        // Load employee data
        loadEmployeeData();
    }

    private void loadEmployeeData() {
        employeeList = FXCollections.observableArrayList(sqlAccess.getAllEmployees());
        employeeTable.setItems(employeeList);
    }
    @FXML
    private void handleAddEmployee() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String role = roleBox.getValue();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || role == null) {
            showAlert("Error", "Please fill out all fields.");
            return;
        }

        Employee newEmployee = new Employee(0, name, email, phone, role, ""); // Assume ID is auto-generated
        sqlAccess.addEmployee(newEmployee);
        employeeList.add(newEmployee);

        clearFields();
    }
    @FXML
    private void handleEditEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showAlert("Error", "Please select an employee to edit.");
            return;
        }

        selectedEmployee.setName(nameField.getText());
        selectedEmployee.setEmail(emailField.getText());
        selectedEmployee.setPhone(phoneField.getText());
        selectedEmployee.setRole(roleBox.getValue());

        sqlAccess.updateEmployee(selectedEmployee);
        employeeTable.refresh();

        clearFields();
    }
    @FXML
    private void handleDeleteEmployee() throws SQLException {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showAlert("Error", "Please select an employee to delete.");
            return;
        }

        sqlAccess.deleteEmployee(selectedEmployee.getId());
        employeeList.remove(selectedEmployee);
    }
    @FXML
    private void handleSearchEmployee() {
        String searchQuery = searchField.getText().toLowerCase();
        ObservableList<Employee> filteredList = FXCollections.observableArrayList();

        for (Employee employee : employeeList) {
            if (employee.getName().toLowerCase().contains(searchQuery) ||
                    employee.getEmail().toLowerCase().contains(searchQuery) ||
                    employee.getRole().toLowerCase().contains(searchQuery)) {
                filteredList.add(employee);
            }
        }

        employeeTable.setItems(filteredList);
    }
    private void clearFields() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        roleBox.setValue(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }






}
