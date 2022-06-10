package Server.dto;

import java.util.Date;

public class MainToDoDTO {
    private int Mainindex; //메인 인덱스
    private String MainTask; //메인 태스크
    private Date MainDate; //메인 데드라인
    private int Chat_index; //메인에 해당하는 채팅방인덱스
    private int SubNum;

    public MainToDoDTO(){

    }
    public MainToDoDTO(int Mainindex, String MainTask, Date MainDate, int Chat_index){
        this.Chat_index = Chat_index;
        this.MainDate = MainDate;
        this.Mainindex = Mainindex;
        this.MainTask = MainTask;

    }

    public int getMainindex() {
        return Mainindex;
    }

    public void setMainindex(int mainindex) {
        Mainindex = mainindex;
    }

    public String getMainTask() {
        return MainTask;
    }

    public void setMainTask(String mainTask) {
        MainTask = mainTask;
    }

    public Date getMainDate() {
        return MainDate;
    }

    public void setMainDate(Date mainDate) {
        MainDate = mainDate;
    }

    public int getChat_index() {
        return Chat_index;
    }

    public void setChat_index(int chat_index) {
        Chat_index = chat_index;
    }

    public int getSubNum() {
        return SubNum;
    }

    public void setSubNum(int subNum) {
        SubNum = subNum;
    }
}
