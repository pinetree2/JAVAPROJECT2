package Server.dao;

import Server.dto.Database;
import Server.dto.SubToDoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SubToDoDAO {
    static Database db = new Database();
    private static Connection connection= db.con;
    private PreparedStatement pstmt;
    private ResultSet rs;


    public static void Subtododao(int Mainindex, int Subindex, String SubTask, Date SubDate, int Chat_index, int SubNum) throws SQLException {
        SubToDoDTO subToDoDTO;
        int Mainidx = Mainindex; //메인인덱스
        int Subidx = Subindex; //서브 인덱스
        String subTask = SubTask; //서브 태스크
        Date subDate = SubDate; //서브 데드라인
        int chat_index = Chat_index; //서브에 해당하는 채팅방인덱스
        int subNum = SubNum; // 총 서브 태스크 개수 이거는 User 통합 코드에서 계산해서 전달받아야되는디요

        //sql
        String sql = "INSERT INTO chatmainsub(M_idx,S_idx,Sub_Task,Sub_Deadline,Chat_index,SubNum) VALUE(?,?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        subToDoDTO = new SubToDoDTO();



        try{
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,Mainidx);
            pstmt.setInt(2,Subidx);
            pstmt.setString(3,subTask);
            pstmt.setDate(4, (java.sql.Date) subDate);
            pstmt.setInt(5,chat_index);
            pstmt.setInt(6,subNum);
        }
        catch (SQLException e) {
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
