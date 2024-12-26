package Backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employee {
    private final int id;
    private String name;
    private String email;
    private final String role;
    protected List<Project> assignedProjects;

    public Employee(int id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        List<Project> assignedProjects = new ArrayList<>();
    }

    public void addEmployee(int id, String name, String email, String role){
        Employee employee = new Employee(id,name,email,role);
        try{
            SQLAccess db = new SQLAccess();
            db.insertEmployee(id,name,email,role,null,employee);
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public void editEmployee(){
//
//    }

    public void removeEmployee(int empId){
        try{
            SQLAccess db = new SQLAccess();
            db.deleteEmployee(empId);
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void viewEmployeeDetails(int empId){
        try{
            SQLAccess db = new SQLAccess();
            Employee emp= db.selectParticularEmployee(empId);
            db.close();
            System.out.print(emp.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void assignProject(int projectId, int empId){
        try{
            SQLAccess db =new SQLAccess();
            db.setEmployeeProject(projectId,empId);
            Employee employee=db.selectParticularEmployee(empId);
            Project project = db.selectParticularProject(projectId);
            employee.assignedProjects.add(project);
            db.updateEmployeeJSON(empId,employee);
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", assignedProjects=" + assignedProjects +
                '}';
    }//i dont think it will work that way we have to do everthing in @fxml then all this because we have to get this on label on our page

}
