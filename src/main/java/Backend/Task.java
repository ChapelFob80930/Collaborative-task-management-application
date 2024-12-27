package Backend;

import Backend.SQLAccess;

import java.util.Date;

public class Task {
    private final int taskId;
    private String title;
    private String description;
    private String priority;
    private String status;
    private Date dueDate;
    private int assignedEmployeeId;

    public Task(int taskId, int assignedEmployeeId, Date dueDate, String status, String priority, String description, String title) {
        this.taskId = taskId;
        this.assignedEmployeeId = assignedEmployeeId;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.description = description;
        this.title = title;
    }

    public void createTask() {
        try {
            SQLAccess db = new SQLAccess();
            db.insertTask(taskId, title, description, priority, status, (java.sql.Date) dueDate, assignedEmployeeId);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error creating task", e);
        }
    }

    public void updateTask(String title, String description, String priority, String status) {
        try {
            SQLAccess db = new SQLAccess();
            this.title = title;
            this.description = description;
            this.priority = priority;
            this.status = status;
            db.updateTaskStatus(id, status);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error updating task", e);
        }
    }

    public void assignEmployee(int employeeId) {
        this.assigned_employee_id = employeeId;
    }
}