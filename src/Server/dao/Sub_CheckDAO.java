package Server.dao;

import Server.dto.Database;
import Server.dto.Sub_CheckDTO;

import java.sql.*;

import static java.lang.Boolean.TRUE;

public class Sub_CheckDAO {


    public static void Sub_Check(int idx,int Chat_index, int Main_index,String check) {

        Connection con = null;

        String url = "127.0.0.1:3306"; // 서버 주소
        String user_name = "newuser"; //  접속자 id
        String password = "@123456789"; // 접속자 pw
        PreparedStatement stmt =null;
        // Statement statement = null;
        ResultSet rs = null;


        Sub_CheckDTO sub_checkDTO;
        sub_checkDTO = new Sub_CheckDTO();

        int Subindex = idx;
        int Mainindex = Main_index;
        int Chatindex = Chat_index;
        Boolean Check;
        Check = Boolean.parseBoolean(check);

        String sql = "UPDATE chatmainsub SET S_check ="+ Check +"WHERE (M_idx  REGEXP '^["+ Mainindex+"]+$') AND (Chat_index REGEXP '^["+Chatindex+"]+$') AND (S_idx REGEXP '^["+Subindex+"]+$' )";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/project_table?serverTimezone=UTC", user_name, password);
            System.out.println("Connect Success!");
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

            sub_checkDTO.setSubindex(idx);
            sub_checkDTO.setMainindex(Mainindex);
            sub_checkDTO.setChatindex(Chatindex);

        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        } finally {
            // DB close 필수!
            // 접속이 된 것
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
}
