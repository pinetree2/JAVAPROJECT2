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

    public static void Sub_Check(int idx) {


        int Subindex = idx;

        String sql = "UPDATE chatmainsub SET S_check = " + TRUE + "WHERE (S_idx REGEXP '^[" + idx + "]+$')";
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
