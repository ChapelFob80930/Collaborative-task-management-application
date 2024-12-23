package Backend;

import java.sql.*;

public class SQLStuff_temporary {
    public void sql_queries() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/employee_details",
                    "root",
                    "root"
            );


            String sql = "INSERT INTO employees (employeeId, name, email, role, projectId) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);


//            pstmt.setInt(1,102);
//            pstmt.setString(2,"insertTestName");
//            pstmt.setString(3,"insertTestEmail");
//            pstmt.setString(4,"insertTestRole");
//            pstmt.setInt(5,103);
//            int rowsInserted = pstmt.executeUpdate();
//            System.out.println("Rows inserted: " + rowsInserted);

            String selectSql = "SELECT * FROM employees";
            PreparedStatement statement = connection.prepareStatement(selectSql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.print(resultSet.getInt("employeeId"));
                System.out.print("\t");
                System.out.print(resultSet.getString("name"));
                System.out.print("\t");
                System.out.print(resultSet.getString("email"));
                System.out.print("\t");
                System.out.print(resultSet.getString("role"));
                System.out.print("\t");
                System.out.print(resultSet.getInt("projectId"));
                System.out.println();
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
