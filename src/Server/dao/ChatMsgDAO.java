package Server.dao;

import Server.dto.ChatMsgDTO;
import Server.dto.Database;

import java.sql.*;
import java.time.LocalTime;

public class ChatMsgDAO {


    static Database db = new Database();
    private static Connection connection= db.con;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public static void chatmsg(int Chat_index, String Msg_sender, String Msg) throws SQLException {
        ChatMsgDTO chatMsgDTO;
        chatMsgDTO = new ChatMsgDTO();
        int chatidx = Chat_index;
        String Msgsender = Msg_sender;
        String msg = Msg;
//        LocalTime msgtime = MsgTime;


        String sql = "INSERT INTO chatmsg(chat_index, msg_sender,msg) VALUE(?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        try{

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,chatidx);
            pstmt.setString(2,Msgsender);
            pstmt.setString(3,msg);
//            pstmt.setTime(4, Time.valueOf(msgtime)); //mysql 의 시간 형식지정자

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
