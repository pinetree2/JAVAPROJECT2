package Server.dao;

import Server.dto.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Boolean.TRUE;

public class Sub_CheckDAO {
    static Database db = new Database();
    private static Connection connection= db.con;
    private static PreparedStatement pstmt;
    private ResultSet rs;

    public static void Sub_Check(int idx,int Chat_index, int Main_index,String check) {


        int Subindex = idx;
        int Mainindex = Main_index;
        int Chatindex = Chat_index;
        Boolean Check;
        Check = Boolean.parseBoolean(check);

        String sql = "UPDATE chatmainsub SET S_check ="+ Check +"WHERE (M_idx  REGEXP '^["+ Mainindex+"]+$') AND (Chat_index REGEXP '^["+Chatindex+"]+$') AND (S_idx REGEXP '^["+Subindex+"]+$' )";
        try {

            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // DB close 필수!
            // 접속이 된 것
            try {
                if (connection != null) {
                    connection.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
}
