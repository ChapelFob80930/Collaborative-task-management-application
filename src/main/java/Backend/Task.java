package Backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task {
    private int taskId;
    private String title;
    private String description;
    private String priority;
    private  String status;
    private String category;
    private Date dueDate;
    List<Employee> tasks;


    public Task(int taskId, String title, String description, String priority, String status, String category, Date dueDate) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.category = category;
        this.dueDate = dueDate;
        List<Employee> tasks = new ArrayList<>();
    }

    private void createTask(){


    }
    private  void updateTask(){

    }
    private void assignEmployee(){

    }
    private void setPriority(){

    }

}
