package Frontend;

import java.util.Date;

public class Notifications {
    private int empId;
    //SQLAccess db = new SQLAccess();
    //Employee recipient = db.selectParticularEmployee(empId);
    public Date date;
    boolean isRead;

    public void sendNotification(int empId){
        Date currDate= new Date();
        int currentDate = currDate.getDate();
        //if((Date dt))
    }
}
