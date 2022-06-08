package Server.dao;


import Server.dto.Database;
import Server.dto.MainToDoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainToDoDAO {
    static Database db = new Database();
    private static Connection connection= db.con;
    private PreparedStatement pstmt;
    private ResultSet rs;


    public static void Maintododao(int idx, String Maintask, String Maindate, int chat_index) throws SQLException {
        MainToDoDTO mainToDoDTO;

        int Mainindex = idx; //메인 인덱스
        String MainTask = Maintask; //메인 태스크
        Date MainDate = null; //메인 데드라인
        int Chat_index = chat_index; //메인에 해당하는 채팅방인덱스
        int MainNum;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일");
        try {
            MainDate = formatter.parse(Maindate);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        //sql
        String sql = "INSERT INTO chatmainsub(M_idx,M_Task,Deadline,Chat_index) VALUE(?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        mainToDoDTO = new MainToDoDTO();



        //채팅방 인덱스에 맞는 메인태스크 개수를 chatroom테이블에 MainNum해서추가해줘야함


        try{
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,Mainindex);
            pstmt.setString(2,MainTask);
            pstmt.setDate(3, (java.sql.Date) MainDate);
            pstmt.setInt(4,Chat_index);

            MainNum = Mainindex;
            String sql2 = "UPDATE chatroom SET Main_Num ="+MainNum+"WHERE (chat_index REGEXP '^["+Chat_index+"]+$')";
            pstmt =connection.prepareStatement(sql2);


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