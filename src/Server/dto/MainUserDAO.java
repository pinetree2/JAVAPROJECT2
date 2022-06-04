package Server.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainUserDAO {
    Database db = new Database();
    private Connection connection= db.con;
    private PreparedStatement pstmt;
    private ResultSet rs;

    String UserName;
    String UserPW;
    String UserID;

    public static void MainuserDAO(String nickName) {
    }
}


