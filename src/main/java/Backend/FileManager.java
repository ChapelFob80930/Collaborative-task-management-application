package Backend;

import java.sql.SQLException;
import java.util.Date;

public class FileManager {
    private final int id;
    private String file_name;
    private final int uploaded_by;
    private Date upload_date;
    private final int project_id;
    private final String file_type;
    private final long file_size;
    private byte[] file_content;

    public FileManager(int id, String file_name, int uploaded_by, int project_id, String file_type, long file_size, byte[] file_content) {
        this.id = id;
        this.file_name = file_name;
        this.uploaded_by = uploaded_by;
        this.project_id = project_id;
        this.file_type = file_type;
        this.file_size = file_size;
        this.file_content = file_content;
        this.upload_date = new Date();
    }

    public void uploadFile(int id, String file_name, int uploaded_by, int project_id, String file_type, long file_size, byte[] file_content) {
        try {
            FileManager fileManager = new FileManager(id, file_name, uploaded_by, project_id, file_type, file_size, file_content);
            SQLAccess db = new SQLAccess();
            db.insertFile(id, file_name, uploaded_by, (java.sql.Date) fileManager.upload_date, project_id, file_type, file_size, file_content,fileManager);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file", e);
        }
    }

    public void deleteFile() {
        try {
            SQLAccess db = new SQLAccess();
            db.deleteFile(id);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting file", e);
        }
    }

    public byte[] getFileContent() {
        try {
            SQLAccess db = new SQLAccess();
            byte[] content = db.selectFileContent(id);
            db.close();
            return content;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching file content", e);
        }
    }

    public void getFileDetails(int fileId){
        try{
            SQLAccess db = new SQLAccess();
            FileManager fileManager = db.getFileManagerJSON(fileId);
            //whatever details we need to show on the frontend
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}