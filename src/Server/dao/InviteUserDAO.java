package Server.dao;

import Server.Room;
import Server.dto.Database;
import Server.dto.InviteUserDTO;
import Server.dto.LogInDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InviteUserDAO {


    static Database db = new Database();
    private static Connection connection= db.con;
    private static PreparedStatement pstmt;
    private ResultSet rs;

    public static void InviteUser(String User_id,int Chat_index){
        InviteUserDTO inviteUserDTO;
        inviteUserDTO = new InviteUserDTO();

        int Memnum =0; //채팅방인원수
        String RoomName;



        try {
            //chatroom 에서 인덱스에 해당하느 채팅방인원수 가져옴 select로
            String sql0 = "SELECT Num_Members FROM chatroom WHERE (chat_index  REGEXP '^["+Chat_index+"]+$')";


            pstmt = connection.prepareStatement(sql0);
            ResultSet rs = pstmt.executeQuery();
            Memnum = rs.getInt("Num_Members");
            Memnum +=1;

            //그다음에 update로 +1 업데이트
            String sql1 = "UPDATE  chatroom SET Num_Members = "+Memnum+ "WHERE (chat_index REGEXP '^["+Chat_index+"]+$')";
            pstmt = connection.prepareStatement(sql1);

            //채팅방 이름도 받아옴
            String sql2 = "SELECT RoomName FROM chatroom WHERE chat_index = "+Chat_index;
            pstmt = connection.prepareStatement(sql2);
            ResultSet rs2 = pstmt.executeQuery();
            RoomName = rs2.getString("RoomName");


            //초대받은사람 id, 채팅방 인덱스를 받아오면 그 사람의 유저별채팅방에 채팅방 추가해주고
            String sql3 = "INSERT INTO userchatroominfo(Chat_index,ChatUser_id,ChatRoomName) VALUE(?,?,?)";
            pstmt = connection.prepareStatement(sql3);
            pstmt.setInt(1,Chat_index);
            pstmt.setString(2,User_id);
            pstmt.setString(3,RoomName);



            //채팅방별 유저에도 추가해줘야함
            String sql4 ="INSERT INTO chatroomuserinfo(chat_index,user_id,chat_name) VALUE(?,?,?)";
            pstmt = connection.prepareStatement(sql4);
            pstmt.setInt(1,Chat_index);
            pstmt.setString(2,User_id);
            pstmt.setString(3,RoomName);

            InviteUserDTO.setUser_id(User_id);
            InviteUserDTO.setChat_index(Chat_index);
            InviteUserDTO.setRoomName(RoomName);
            InviteUserDTO.setMemnum(Memnum);


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
