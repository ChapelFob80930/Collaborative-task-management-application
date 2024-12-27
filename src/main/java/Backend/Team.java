package Backend;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class Team {
    private int teamId;
    private String teaName;
    private List<Integer> member_ids;
    private int projectId;

    public Team(int teamId, String teamName, int projectId) {
        this.teamId = teamId;
        this.teaName = teamName;
        this.projectId = projectId;
        this.member_ids = new ArrayList<>();
    }

    public void createTeam(int teamId, String teamName, int projectId){
        Team team = new Team(teamId,teamName,projectId);
        try{
            SQLAccess db = new SQLAccess();
            db.createTeam(teamId,teamName,projectId,team);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addMember(int teamId,int employeeId) {
        try {
            SQLAccess db = new SQLAccess();
            Team team = db.selectTeam(teamId);
            db.insertTeamMember(teamId, employeeId);
            team.member_ids.add(employeeId);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error adding team member", e);
        }
    }

    public void removeMember(int teamId,int employee_id) {
        try {
            SQLAccess db = new SQLAccess();
            db.deleteTeamMember(teamId, employee_id);
            member_ids.remove((Integer) employee_id);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error removing team member", e);
        }
    }

    public List<Integer> getMembers() {
        try {
            SQLAccess db = new SQLAccess();
            List<Integer> members = db.selectTeamMembers(teamId);
            db.close();
            return members;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching team members", e);
        }
    }
}