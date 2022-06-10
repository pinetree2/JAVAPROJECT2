package Server.dao;

import Server.dto.Database;
import Server.dto.SubToDoDTO;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SubToDoDAO {


    public static void Subtododao(int Mainindex, int Subindex, String SubTask, String SubDate, int Chat_index, int SubNum) throws SQLException {
        Connection con = null;

        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "newuser"; //  접속자 id
        String password = "@123456789"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        ResultSet rs = null;


        SubToDoDTO subToDoDTO;
        int Mainidx = Mainindex; //메인인덱스
        int Subidx = Subindex; //서브 인덱스
        String subTask = SubTask; //서브 태스크
        Date subDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일");
        try {
            subDate = formatter.parse(SubDate);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        int chat_index = Chat_index; //서브에 해당하는 채팅방인덱스
        int subNum = SubNum; // 총 서브 태스크 개수 이거는 User 통합 코드에서 계산해서 전달받아야되는디요

        //sql
        String sql = "INSERT INTO chatmainsub(M_idx,S_idx,Sub_Task,Sub_Deadline,Chat_index,SubNum) VALUE(?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        subToDoDTO = new SubToDoDTO();



        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/project_table?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,Mainidx);
            pstmt.setInt(2,Subidx);
            pstmt.setString(3,subTask);
            pstmt.setDate(4, (java.sql.Date) subDate);
            pstmt.setInt(5,chat_index);
            pstmt.setInt(6,subNum);
            pstmt.executeUpdate();

            subToDoDTO.setMainidx(Mainidx);
            subToDoDTO.setSubidx(Subidx);
            subToDoDTO.setSubTask(subTask);
            subToDoDTO.setSubDate(subDate);
            subToDoDTO.setChat_index(chat_index);
            subToDoDTO.setSubNum(subNum);

        }
        catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        } finally {
            // DB close 필수!
            // 접속이 된 것
            try {
                if(con != null) {
                    con.close();
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