package Backend;

import com.google.gson.Gson;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SQLAccess {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/employee_details";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    public SQLAccess() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================= EMPLOYEE METHODS =============================
    public void insertEmployee(int id, String name, String email, String role, Integer projectId, Employee employee) throws SQLException {
        String sql = "INSERT INTO employee (employeeId, name, email, role, projectId, employeeJson) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new Gson();
            String employeeJson = gson.toJson(employee);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, role);
            pstmt.setInt(5, projectId);
            pstmt.setString(6, employeeJson);
            pstmt.executeUpdate();
        }
    }

    public ResultSet selectAllEmployees() throws SQLException {
        String sql = "SELECT * FROM employee";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public void setEmployeeProject(int projectId, int empId) throws SQLException {
        String sql = "UPDATE employee SET projectId  = ? WHERE employeeId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            pstmt.setInt(2, empId);
            pstmt.executeUpdate();
        }
    }

    public void updateEmployeeRole(int id, String newRole) throws SQLException {
        String sql = "UPDATE employee SET role = ? WHERE employeeId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newRole);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteEmployee(int empId) throws SQLException {
        String sql = "DELETE FROM employee WHERE employeeId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            pstmt.executeUpdate();
        }
    }

    public Employee selectParticularEmployee(int empId){
        String sql = "SELECT FROM employee WHERE employeeId =?";
        try{
            Gson gson = new Gson();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,empId);
            ResultSet resultSet = pstmt.executeQuery();
            return gson.fromJson(resultSet.getString("employeeJson"), Employee.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Employee,Project> getProjectDetails(int empId){
        String sql = "SELECT employees.employeeJson, projects.projectJSON FROM employees JOIN projects ON employees.projectId = projects.id WHERE employees.employeeId =?";
        Map<Employee,Project> result= new HashMap<Employee,Project>();
        try{
            Gson gson = new Gson();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,empId);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                result.put((gson.fromJson(resultSet.getString("employeeJson"), Employee.class)),(gson.fromJson(resultSet.getString("projectJSON"), Project.class)));
            }
            else {
                throw new RuntimeException("Error retrieving project details");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void updateEmployeeJSON(int employeeId,Employee employee) throws SQLException {
        String sql = "UPDATE project SET employeeJson = ? WHERE employeeId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new Gson();
            String employeeJson = gson.toJson(employee);
            pstmt.setString(1, employeeJson);
            pstmt.setInt(2, employeeId);
            pstmt.executeUpdate();
        }
    }

    public void updateEmployee(int empId, String name, String email, Employee employee){
        String sql = "UPDATE employee SET name = ?, email = ?, employeeJson = ? WHERE employeeId = ?";
        try {
            Gson gson =new Gson();
            String empJson = gson.toJson(employee);
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, empJson);
            pstmt.setInt(4, empId);

            pstmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


    // ============================== TASK METHODS ===============================
    public void insertTask(int id, String title, String description, String priority, String status, String category, Date dueDate, int assignedEmployee) throws SQLException {
        String sql = "INSERT INTO Task (id, title, description, priority, status, category, dueDate, assignedEmployee) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, priority);
            pstmt.setString(5, status);
            pstmt.setString(6, category);
            pstmt.setDate(7, dueDate);
            pstmt.setInt(8, assignedEmployee);
            pstmt.executeUpdate();
        }
    }

    public ResultSet selectTasks() throws SQLException {
        String sql = "SELECT * FROM Task";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public void updateTaskStatus(int id, String newStatus) throws SQLException {
        String sql = "UPDATE Task SET status = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteTask(int id) throws SQLException {
        String sql = "DELETE FROM Task WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // ============================ PROJECT METHODS =============================
    public void insertProject(int id, String name, String description, Date startDate, Date endDate, float budget, String status, Project project) throws SQLException {
        String sql = "INSERT INTO Project (projectId, description, startDate, endDate, budget, Status, projectJSON, name) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new Gson();
            String projectJSON = gson.toJson(project);
            pstmt.setInt(1, id);
            pstmt.setString(2, description);
            pstmt.setDate(3, startDate);
            pstmt.setDate(4, endDate);
            pstmt.setFloat(5, budget);
            pstmt.setString(6, status);
            pstmt.setString(7, projectJSON);
            pstmt.setString(8, name);
            pstmt.executeUpdate();
        }
    }

    public ResultSet selectAllProjects() throws SQLException {
        String sql = "SELECT * FROM Project";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public Project selectParticularProject(int projectId){
        String sql = "SELECT FROM project WHERE projectId =?";
        try{
            Gson gson = new Gson();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,projectId);
            ResultSet resultSet = pstmt.executeQuery();
            return gson.fromJson(resultSet.getString("projectJSON"), Project.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProjectStatus(int id, String newStatus) throws SQLException {
        String sql = "UPDATE Project SET status = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public void updateProjectJSON(int projectId,Project project) throws SQLException {
        String sql = "UPDATE project SET projectJSON = ? WHERE projectId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new Gson();
            String projectJson = gson.toJson(project);
            pstmt.setString(1, projectJson);
            pstmt.setInt(2, projectId);
            pstmt.executeUpdate();
        }
    }

    public void deleteProject(int projectId) throws SQLException {
        String sql = "DELETE FROM Project WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            pstmt.executeUpdate();
        }
    }

    // =========================== FILE MANAGER METHODS ==========================
    public void insertFile(int id, String fileName, int uploadedBy, Date uploadDate, int projectId) throws SQLException {
        String sql = "INSERT INTO FileManager (id, fileName, uploadedBy, uploadDate, projectId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, fileName);
            pstmt.setInt(3, uploadedBy);
            pstmt.setDate(4, uploadDate);
            pstmt.setInt(5, projectId);
            pstmt.executeUpdate();
        }
    }

    public ResultSet selectFilesByProject(int projectId) throws SQLException {
        String sql = "SELECT * FROM FileManager WHERE projectId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            return pstmt.executeQuery();
        }
    }

    public void deleteFile(int id) throws SQLException {
        String sql = "DELETE FROM FileManager WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void addEmployee(Employee newEmployee) {

    }

    public Employee getAllEmployees() {
        return null;
    }

    public void updateEmployee(Employee selectedEmployee) {

    }
}
