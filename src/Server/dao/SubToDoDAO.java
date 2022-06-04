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


    public static void Subtododao(Sub subtodo) throws SQLException {
        SubToDoDTO subToDoDTO;
        String Subindex; //서브 인덱스
        String SubTask; //서브 태스크
        Date SubDate; //서브 데드라인
        int Chat_index; //서브에 해당하는 채팅방인덱스
        int SubNum; // 총 서브 태스크 개수 이거는 User 통합 코드에서 계산해서 전달받아야되는디요

        //sql
        String sql = "INSERT INTO chatmainsub(S_idx,Sub_Task,Sub_Deadline,Chat_index,SubNum) VALUE(?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        subToDoDTO = new SubToDoDTO();

        //불러와서 지정해주기
        Subindex = subtodo.subindex;
        SubTask = subtodo.subtask;
        SubDate =subtodo.subdate;
        Chat_index = subtodo.chat_index;
        SubNum = subtodo.subnum;




        try{
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,Subindex);
            pstmt.setString(2,SubTask);
            pstmt.setDate(3, (java.sql.Date) SubDate);
            pstmt.setInt(4,Chat_index);
            pstmt.setInt(5,SubNum);
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