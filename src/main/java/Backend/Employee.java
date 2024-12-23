package Backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employee {
    private int id;
    private String name;
    private String email;
    private String role;
    List<Project> assignedProjects = new ArrayList<>();
    private static Map<Integer,Employee> registeredEmployees = new HashMap<Integer,Employee>(); //temporary will learn how to add java objects to database to make it much easier to access stuff

    public Employee(int id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public void addEmployee(){

    }

    public void editEmployee(){

    }

    public void removeEmployee(){

    }

    public void viewEmployeeDetails(){

    }
}
