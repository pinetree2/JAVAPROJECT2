package Server.dto;

import Server.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class MainToDoDAO {
    static Database db = new Database();
    private static Connection connection= db.con;
    private PreparedStatement pstmt;
    private ResultSet rs;


    public static void Maintododao(Main maintodo) throws SQLException {
        MainToDoDTO mainToDoDTO;

        String Mainindex; //메인 인덱스
        String MainTask; //메인 태스크
        Date MainDate; //메인 데드라인
        int Chat_index; //메인에 해당하는 채팅방인덱스


        //sql
        String sql = "INSERT INTO chatmainsub(M_idx,M_Task,Deadline,Chat_index) VALUE(?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        mainToDoDTO = new MainToDoDTO();

        //불러와서 지정해주기
        Mainindex = maintodo.mainindex;
        MainTask = maintodo.maintask;
        MainDate =maintodo.maindate;
        Chat_index = maintodo.chat_index;

        try{
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,Mainindex);
            pstmt.setString(2,MainTask);
            pstmt.setDate(3, (java.sql.Date) MainDate);
            pstmt.setInt(4,Chat_index);
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // DB close 필수!
            // 접속이 된 것
            try {
                if(connection != null) {
                    connection.close();
                }
                if(pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }


    }

}
