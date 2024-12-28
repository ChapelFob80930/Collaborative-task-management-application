package Backend;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Project {
    private final int projectId;
    private String name;
    private String description;
    private final Date startDate;
    private Date endDate;
    private final float budget;
    private String status;
    protected List<Integer> teamMembersId;
    protected List<String> tasks;

    public Project(int projectId, String status, float budget, Date endDate, String description, Date startDate, String name) {
        this.projectId = projectId;
        this.status = status;
        this.budget = budget;
        this.endDate = endDate;
        this.description = description;
        this.startDate = new Date();
        this.name = name;
        this.teamMembersId = new ArrayList<Integer>();
        this.tasks = new ArrayList<String>();
    }

    public void createProject(int projectId, String status, float budget, Date endDate, String description, Date startDate, String name){
        Project project = new Project(projectId,status,budget,endDate,description,startDate,name);
        try{
            SQLAccess db = new SQLAccess();
            db.insertProject(projectId,name,description, (java.sql.Date) startDate, (java.sql.Date) endDate,budget,status,project);
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProjectDetails(int projectId, String description, Date endDate, String status, String name){
        try{
            SQLAccess db=new SQLAccess();
            Project pr=db.selectParticularProject(projectId);
            pr.setDescription(description);
            pr.setEndDate(endDate);
            pr.setName(name);
            pr.setStatus(status);
            db.updateProject(projectId,description, (java.sql.Date) endDate,status,pr,name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void assignTeamMember(int projectId,int teamMemberId){

        try{
            SQLAccess db= new SQLAccess();
            Project project = db.selectParticularProject(projectId);
            project.teamMembersId.add(teamMemberId);
            db.updateProjectJSON(projectId,project);
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeTeamMember(int projectId,int teamMemberId){

        try{
            SQLAccess db= new SQLAccess();
            Project project = db.selectParticularProject(projectId);
            project.teamMembersId.remove(teamMemberId);
            db.updateProjectJSON(projectId,project);
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeProject(int projectId){
        try{
            SQLAccess db= new SQLAccess();
            db.deleteProject(projectId);
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public int getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public float getBudget() {
        return budget;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }
}
