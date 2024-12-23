package Backend;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Project {
    private int projectId;
    String name;
    String description;
    Date startDate;
    Date endDate;
    float budget;
    String status;
    List<Integer> teamMembersId;
    List<String> tasks;

    public void createProject(){
        //SQL
        //JavaFX probably in separate project page controller
    }

    public void updateProjectDetails(){
        //SQL
        //JavaFX probably in separate project page controller
    }

    public void assignTeamMember(){
        //SQL
        //JavaFX probably in separate project page controller
    }

    public void closeProject(){
        //SQL
        //JavaFX probably in separate project page controller
    }
}
