package Server.dto;

public class Main_CheckDTO {

    static int Mainindex;
    static int Chatindex;
    static Boolean Check;

    public int getMainindex() {
        return Mainindex;
    }

    public static void setMainindex(int mainindex) {
        Mainindex = mainindex;
    }

    public int getChatindex() {
        return Chatindex;
    }

    public static void setChatindex(int chatindex) {
        Chatindex = chatindex;
    }

    public Boolean getCheck() {
        return Check;
    }

    public static void setCheck(Boolean check) {
        Check = check;
    }
}
