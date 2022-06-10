package Server.dao;

import Server.dto.Database;
import Server.dto.Sub_CheckDTO;

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

        Sub_CheckDTO sub_checkDTO;
        sub_checkDTO = new Sub_CheckDTO();

        int Subindex = idx;
        int Mainindex = Main_index;
        int Chatindex = Chat_index;
        Boolean Check;
        Check = Boolean.parseBoolean(check);

        String sql = "UPDATE chatmainsub SET S_check ="+ Check +"WHERE (M_idx  REGEXP '^["+ Mainindex+"]+$') AND (Chat_index REGEXP '^["+Chatindex+"]+$') AND (S_idx REGEXP '^["+Subindex+"]+$' )";
        try {

            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int result = pstmt.executeUpdate();
            if(result == 1){
                System.out.println("서브 check 값 변경완료");
            }else {
                System.out.println("서브 check 값 변경실패");
            }

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
