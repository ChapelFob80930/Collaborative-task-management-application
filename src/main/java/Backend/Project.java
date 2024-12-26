package Backend;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        this.startDate = startDate;
        this.name = name;
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

    public void updateProjectDetails(){
        //SQL
        //JavaFX probably in separate project page controller
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

    public void closeProject(){
        //SQL
        //JavaFX probably in separate project page controller
    }
}
