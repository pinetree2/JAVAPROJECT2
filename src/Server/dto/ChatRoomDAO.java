package Server.dto;

import Server.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatRoomDAO {
    static Database db = new Database();
    private static Connection connection= db.con;
    private PreparedStatement pstmt;
    private ResultSet rs;




    public static void chatroomdao(Room myRoom) throws SQLException {
        ChatRoomDTO chatRoomDTO;
        int count =0;

        //sql
        String sql = "INSERT INTO chatroom"+"VALUE(?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        chatRoomDTO = new ChatRoomDTO();
        String Roomname = myRoom.title;

        try {
            pstmt = connection.prepareStatement(sql);
            // 데이터를 넣기
            pstmt.setString(1, Roomname);
            pstmt.setInt(2,1);
            chatRoomDTO.setRoomname(Roomname);
            chatRoomDTO.setNum_Members(1);
            count = pstmt.executeUpdate();//변경된 데이터 개수 리턴

        } catch (SQLException e) {
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

