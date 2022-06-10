package Server.dao;

import Server.dto.Database;
import Server.dto.Main_CheckDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Boolean.TRUE;

public class Main_CheckDAO {
    static Database db = new Database();
    private static Connection connection= db.con;
    private static PreparedStatement pstmt;
    private ResultSet rs;

    public static void Main_Check(int idx, int Chat_index, String check){
        Main_CheckDTO main_checkDTO;
        main_checkDTO = new Main_CheckDTO();

        int Mainindex =idx;
        int Chatindex = Chat_index;
        Boolean Check;
        Check = Boolean.parseBoolean(check);

        //채팅방 인덱스하고 메인인덱스
        String sql = "UPDATE chatmainsub SET M_check ="+ Check +"WHERE (M_idx  REGEXP '^["+Mainindex+"]+$') AND (Chat_index REGEXP '^["+Chatindex+"]+$')";



        try {

            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            pstmt.executeUpdate(sql);
            Main_CheckDTO.setMainindex(Mainindex);
            Main_CheckDTO.setChatindex(Chatindex);
            Main_CheckDTO.setCheck(Check);





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
