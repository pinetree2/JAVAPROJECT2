package Server.dto;

public class LogOutDTO {
    static String User_id;
    static Boolean status;


    public LogOutDTO(){
        this.User_id = User_id;
        this.status = status;
    }

    public String getUser_id() {
        return User_id;
    }

    public static void setUser_id(String user_id) {
        User_id = user_id;
    }

    public Boolean getStatus() {
        return status;
    }

    public static void setStatus(Boolean status) {
        status = status;
    }
}
