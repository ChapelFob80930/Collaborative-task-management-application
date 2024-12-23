package Backend;

import java.sql.*;

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
    public void insertEmployee(int id, String name, String email, String role) throws SQLException {
        String sql = "INSERT INTO Employee (id, name, email, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, role);
            pstmt.executeUpdate();
        }
    }

    public ResultSet selectEmployees() throws SQLException {
        String sql = "SELECT * FROM Employee";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public void updateEmployeeRole(int id, String newRole) throws SQLException {
        String sql = "UPDATE Employee SET role = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newRole);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM Employee WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
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
    public void insertProject(int id, String name, String description, Date startDate, Date endDate, float budget, String status) throws SQLException {
        String sql = "INSERT INTO Project (id, name, description, startDate, endDate, budget, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, description);
            pstmt.setDate(4, startDate);
            pstmt.setDate(5, endDate);
            pstmt.setFloat(6, budget);
            pstmt.setString(7, status);
            pstmt.executeUpdate();
        }
    }

    public ResultSet selectProjects() throws SQLException {
        String sql = "SELECT * FROM Project";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public void updateProjectStatus(int id, String newStatus) throws SQLException {
        String sql = "UPDATE Project SET status = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteProject(int id) throws SQLException {
        String sql = "DELETE FROM Project WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
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
}
