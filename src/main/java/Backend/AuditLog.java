package Backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AuditLog {
    private int logId;
    private String action;
    private Date timestamp;
    private int performedBy;

    public AuditLog(int logId, String action, Date timestamp, int performedBy) {
        this.logId = logId;
        this.action = action;
        this.timestamp = timestamp;
        this.performedBy = performedBy;
    }

    public void createLog(int logId, String action, Date timestamp, int performedBy) {
        AuditLog auditLog = new AuditLog(logId,action,timestamp,performedBy);
        try {
            SQLAccess db = new SQLAccess();
            db.insertAuditLog(logId, action, (java.sql.Date) timestamp, performedBy, auditLog);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error creating audit log", e);
        }
    }

    public void deleteLog(){
        try {
            SQLAccess db = new SQLAccess();
            db.deleteAuditLog(logId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAuditLog(){
        try {
            SQLAccess db = new SQLAccess();
            ResultSet rs= db.selectAuditLogs();
            //frontend stuff to show auditlog
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}