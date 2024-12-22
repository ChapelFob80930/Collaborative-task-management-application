package Backend;

import java.sql.*;

public class Employee {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/employee_details",
                    "root",
                    "root"
            );

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");

            while (resultSet.next()){
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
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
