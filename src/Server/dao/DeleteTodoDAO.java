package Server.dao;

import Server.dto.Database;
import Server.dto.DeleteTodoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteTodoDAO {
    static Database db = new Database();
    private static Connection connection= db.con;
    private static PreparedStatement pstmt;
    private static ResultSet rs;

    public static void DeleteTodoDAO(int M_idx, int S_idx){

        int m_idx = M_idx;
        int s_idx = S_idx;

        if(S_idx == 0){
            //메인인덱스에 해당하는거 다 지움
            // delete
            String sql ="DELETE FROM chatmainsub WHERE M_idx = ?";

            try{
                pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1,M_idx);
                rs = pstmt.executeQuery();
                pstmt.executeUpdate(sql);
                DeleteTodoDTO.setM_idx(M_idx);
                DeleteTodoDTO.setS_idx(S_idx);

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

        }else{
            //메인, 서브 인덱스에 둘 다 해당하는 task 지움
            String sql = "DELETE FROM chatmainsub WHERE M_idx = ? AND S_idx =?";
            try{
                pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1,M_idx);
                pstmt.setInt(2,S_idx);
                rs = pstmt.executeQuery();
                pstmt.executeUpdate(sql);
                DeleteTodoDTO.setM_idx(M_idx);
                DeleteTodoDTO.setS_idx(S_idx);

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
}
