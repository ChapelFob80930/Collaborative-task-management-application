package Backend;

import java.sql.SQLException;
import java.util.Date;

class AuditLog {
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

    public void createLog() {
        try {
            SQLAccess db = new SQLAccess();
            db.insertAuditLog(logId, action, (java.sql.Date) timestamp, performedBy);
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
}