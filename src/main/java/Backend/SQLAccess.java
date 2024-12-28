package Backend;

import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void updateEmployee(int empId, String name, String email, String role, Employee employee){
        String sql = "UPDATE employee SET name = ?, email = ?, role = ?, employeeJson = ? WHERE employeeId = ?";
        try {
            Gson gson =new Gson();
            String empJson = gson.toJson(employee);
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3,role);
            pstmt.setString(4, empJson);
            pstmt.setInt(5, empId);

            pstmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


    // ============================== TASK METHODS ===============================
    public void insertTask(int taskId, String title, String description, String priority, String status, Date dueDate, int assignedEmployeeId, Task task) throws SQLException {
        String sql = "INSERT INTO Task (taskId, title, description, priority, status, dueDate, assignedEmployeeId, taskJSON) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new Gson();
            String taskJSON = gson.toJson(task);
            pstmt.setInt(1, taskId);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, priority);
            pstmt.setString(5, status);
            pstmt.setDate(6, dueDate);
            pstmt.setInt(7, assignedEmployeeId);
            pstmt.setString(8, taskJSON);
            pstmt.executeUpdate();
        }
    }

    public ResultSet selectTasks() throws SQLException {
        String sql = "SELECT * FROM Task";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public void updateTaskStatus(int taskId, String newStatus, Task task) throws SQLException {
        String sql = "UPDATE Task SET status = ?, taskJSON = ?  WHERE taskId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new Gson();
            String taskJSON = gson.toJson(task);
            pstmt.setString(1, newStatus);
            pstmt.setString(2, taskJSON);
            pstmt.setInt(3, taskId);
            pstmt.executeUpdate();
        }
    }

    public void updateTaskDetails(int taskId, String title, String description, String priority, String status, Date dueDate, int assignedEmployeeId, Task task) {
        String sql = "UPDATE employee SET title = ?, description = ?, priority = ?, status = ?, dueDate = ?, assignedEmployeeId = ?, taskJSON = ? WHERE taskId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new Gson();
            String taskJSON = gson.toJson(task);
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, priority);
            pstmt.setString(4, status);
            pstmt.setDate(5, dueDate);
            pstmt.setInt(6, assignedEmployeeId);
            pstmt.setString(7, taskJSON);
            pstmt.setInt(8, taskId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTask(int taskId) throws SQLException {
        String sql = "DELETE FROM Task WHERE taskId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            pstmt.executeUpdate();
        }
    }

    public Task getTaskJson(int taskId) {
        String sql = "SELECT FROM Task WHERE taskId = ?";
        try {
            Gson gson = new Gson();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();
            return  gson.fromJson((resultSet.getString("taskJSON")),Task.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public void updateProject(int projectId, String description, Date endDate, String status, Project project, String name) throws SQLException {
        String sql = "UPDATE project SET description = ?, endDate = ?, Status = ?, projectJSON = ?, name =? WHERE projectId = ?";
        try {
            Gson gson =new Gson();
            String projectJson = gson.toJson(project);
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, description);
            pstmt.setDate(2,endDate);
            pstmt.setString(3, status);
            pstmt.setString(4, projectJson);
            pstmt.setString(5, name);
            pstmt.setInt(6, projectId);
            pstmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
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

    // ============================ FILE MANAGER METHODS ============================
    public void insertFile(int id, String file_name, int uploaded_by, Date upload_date, int project_id, String file_type, long file_size, byte[] file_content,FileManager fileManager) throws SQLException {
        String sql = "INSERT INTO filemanager (fileId, fileName, uploadedBy, uploadDate, projectId, fileType, fileSize, fileContent, filemanagerJSON) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new Gson();
            String filemanagerJSON = gson.toJson(fileManager);
            pstmt.setInt(1, id);
            pstmt.setString(2, file_name);
            pstmt.setInt(3, uploaded_by);
            pstmt.setTimestamp(4, new Timestamp(upload_date.getTime()));
            pstmt.setInt(5, project_id);
            pstmt.setString(6, file_type);
            pstmt.setLong(7, file_size);
            pstmt.setBytes(8, file_content);
            pstmt.setString(9, filemanagerJSON);
            pstmt.executeUpdate();
        }
    }

    public void deleteFile(int id) throws SQLException {
        String sql = "DELETE FROM filemanager WHERE fileId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public byte[] selectFileContent(int fileId) throws SQLException {
        String sql = "SELECT fileContent FROM filemanager WHERE fileId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, fileId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBytes("fileContent");
            } else {
                throw new SQLException("File content not found for fileId: " + fileId);
            }
        }
    }

    public FileManager getFileManagerJSON(int fileId){
        String sql = "SELECT FROM filemanager WHERE fileId = ?";
        try{
            Gson gson = new Gson();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,fileId);
            ResultSet resultSet = pstmt.executeQuery();
            return gson.fromJson(resultSet.getString("filemanagerJSON"),FileManager.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    // ============================= NOTIFICATION METHODS =============================
    public void insertNotification(int notiId, int employeeId, String message, Date date, boolean isRead, Notifications notifications) throws SQLException {
        String sql = "INSERT INTO notifications (notificationId, empId, message, date, isRead, notificationJSON) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new Gson();
            String notificationJSON = gson.toJson(notifications);
            pstmt.setInt(1, notiId);
            pstmt.setInt(2, employeeId);
            pstmt.setString(3, message);
            pstmt.setTimestamp(4, new Timestamp(date.getTime()));
            pstmt.setBoolean(5, isRead);
            pstmt.setString(6, notificationJSON);
            pstmt.executeUpdate();
        }
    }

    public void updateNotificationReadStatus(int notiId, boolean isRead) throws SQLException {
        String sql = "UPDATE notifications SET isRead = ? WHERE notificationId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBoolean(1, isRead);
            pstmt.setInt(2, notiId);
            pstmt.executeUpdate();
        }
    }


//    // ================================ TEAM METHODS ================================
//
//    public void createTeam(int teamId, String teamName, int projectId, Team team) {
//    }
//
//    public void insertTeamMember(int teamId, int employeeId) throws SQLException {
//        String sql = "INSERT INTO team_members (teamId, employeeId) VALUES (?, ?)";
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setInt(1, teamId);
//            pstmt.setInt(2, employeeId);
//            pstmt.executeUpdate();
//        }
//    }
//
//    public void deleteTeamMember(int teamId, int employeeId) throws SQLException {
//        String sql = "DELETE FROM team_members WHERE teamId = ? AND employeeId = ?";
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setInt(1, teamId);
//            pstmt.setInt(2, employeeId);
//            pstmt.executeUpdate();
//        }
//    }
//
//    public List<Integer> selectTeamMembers(int teamId) throws SQLException {
//        String sql = "SELECT employeeId FROM team_members WHERE teamId = ?";
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setInt(1, teamId);
//            ResultSet rs = pstmt.executeQuery();
//            List<Integer> memberIds = new ArrayList<>();
//            while (rs.next()) {
//                memberIds.add(rs.getInt("employeeId"));
//            }
//            return memberIds;
//        }
//    }
//
//
//
//    public Team selectTeam(int teamId) {
//        return null;
//    }


    // ================================ AUDITLOG METHODS ================================
    public void insertAuditLog(int logId, String action, Date timestamp, int performedBy, AuditLog auditLog) throws SQLException {
        String sql = "INSERT INTO auditlog (logId, action, timestamp, performedBy, auditlogJSON) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Gson gson = new Gson();
            String auditLogJSON = gson.toJson(auditLog);
            pstmt.setInt(1, logId);
            pstmt.setString(2, action);
            pstmt.setTimestamp(3, new Timestamp(timestamp.getTime()));
            pstmt.setInt(4, performedBy);
            pstmt.setString(5, auditLogJSON);
            pstmt.executeUpdate();
        }
    }

    public ResultSet selectAuditLogs() throws SQLException {
        String sql = "SELECT * FROM auditlog";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public void deleteAuditLog(int logId) throws SQLException {
        String sql = "DELETE FROM auditlog WHERE logId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, logId);
            pstmt.executeUpdate();
        }
    }



}
