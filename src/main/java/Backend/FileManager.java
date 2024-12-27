package Backend;

import java.util.Date;

class FileManager {
    private int id;
    private String file_name;
    private int uploaded_by;
    private Date upload_date;
    private int project_id;
    private String file_type;
    private long file_size;
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

    public void uploadFile() {
        try {
            SQLAccess db = new SQLAccess();
            db.insertFile(id, file_name, uploaded_by, (java.sql.Date) upload_date, project_id, file_type, file_size, file_content);
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
}