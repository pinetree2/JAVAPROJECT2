package Server.dao;

import Server.dto.Database;
import Server.dto.LogOutDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Boolean.FALSE;

public class LogOutDAO {


    //쿼리문으로 인덱스에 해당하는 유저 id 찾아가지고 status변경하는 코드 짜야함.

    static Database db = new Database();
    private static Connection connection= db.con;
    private static PreparedStatement pstmt;
    private ResultSet rs;


    public static void logoutdao(String UserID){
        LogOutDTO logOutDTO;
        logOutDTO = new LogOutDTO();

        //user_id 행의 열 값중 user_id와 일치하는것을 찾고, 그것과 같은 행의 status값 변경

        String sql ="UPDATE user_table SET status = "+FALSE+"WHERE user_id LIKE "+UserID;

        try {

            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            LogOutDTO.setUser_id(UserID);



        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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
