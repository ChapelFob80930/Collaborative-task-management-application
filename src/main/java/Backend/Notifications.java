package Backend;

import java.util.Date;

class Notifications {
    private final int notificationId;
    private final int empId;
    private String message;
    private Date date;
    private boolean isRead;

    public Notifications(int notificationId, int empId, String message) {
        this.notificationId = notificationId;
        this.empId = empId;
        this.message = message;
        this.date = new Date();
        this.isRead = false;
    }

    public void sendNotification() {
        try {
            SQLAccess db = new SQLAccess();
            db.insertNotification(notificationId, empId, message, (java.sql.Date) date, isRead);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error sending notification", e);
        }
    }

    public void markAsRead() {
        try {
            SQLAccess db = new SQLAccess();
            this.isRead = true;
            db.updateNotificationReadStatus(notificationId, true);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error marking notification as read", e);
        }
    }
}