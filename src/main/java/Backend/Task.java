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

    public void createTask(int taskId, int assignedEmployeeId, Date dueDate, String status, String priority, String description, String title) {
        try {
            Task task = new Task(taskId,assignedEmployeeId,dueDate,status,priority,description,title);
            SQLAccess db = new SQLAccess();
            db.insertTask(taskId, title, description, priority, status, (java.sql.Date) dueDate, assignedEmployeeId,task);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error creating task", e);
        }
    }

    public void updateTaskStatus(int taskId, int assignedEmployeeId, Date dueDate, String status, String priority, String description, String title) {
        try {
            SQLAccess db = new SQLAccess();
            Task task = db.getTaskJson(taskId);
            task.setStatus(status);
            db.updateTaskStatus(taskId, status, task);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error updating task", e);
        }
    }

    public void updateTaskDetails(int taskId, int assignedEmployeeId, Date dueDate, String status, String priority, String description, String title) {
        try {
            SQLAccess db = new SQLAccess();
            Task task = db.getTaskJson(taskId);
            task.setAssignedEmployeeId(assignedEmployeeId);
            task.setDueDate(dueDate);
            task.setStatus(status);
            task.setPriority(priority);
            task.setDescription(description);
            task.setTitle(title);
            db.updateTaskDetails(taskId,title,description,priority,status, (java.sql.Date) dueDate,assignedEmployeeId,task);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error updating task", e);
        }
    }

    public void assignEmployee(int employeeId) {
        this.assignedEmployeeId = employeeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setAssignedEmployeeId(int assignedEmployeeId) {
        this.assignedEmployeeId = assignedEmployeeId;
    }
}