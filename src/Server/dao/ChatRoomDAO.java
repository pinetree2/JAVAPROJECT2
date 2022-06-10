package Server.dao;

import Server.Room;
import Server.dto.ChatRoomDTO;
import Server.dto.Database;

import java.sql.*;

public class ChatRoomDAO {


    public static void chatroomdao(Room myRoom) throws SQLException {

        Connection con = null;

        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "newuser"; //  접속자 id
        String password = "@123456789"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        ResultSet rs = null;

        ChatRoomDTO chatRoomDTO;
        int count =0;



        try {
            chatRoomDTO = new ChatRoomDTO();
            String Roomname = myRoom.title;
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/project_table?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");
//sql
            String sql = "INSERT INTO chatroom"+"VALUE(?,?)";
            stmt = con.prepareStatement(sql);
            // 데이터를 넣기
            stmt.setString(1, Roomname);
            stmt.setInt(2,1);
            stmt.executeUpdate();

            chatRoomDTO.setRoomname(Roomname);
            chatRoomDTO.setNum_Members(1);


            //채팅방 인덱스 리턴하기


        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        } finally {
            // DB close 필수!
            // 접속이 된 것
            try {
                if(con != null) {
                    con.close();
                }
                if(stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }



    }
}
