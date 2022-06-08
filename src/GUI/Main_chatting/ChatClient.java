package GUI.Main_chatting;

import GUI.Main_Window_GUI.*;
import GUI.Log_in_GUI.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ChatClient {
    LoginView loginView;
    private static final String server_ip = "127.0.0.1"; //서버 ip주소
    private static final int server_port = 7777; //포트번호

    // db연결
    Connection con = null;
    Statement st;
    PreparedStatement ps;
    ResultSet rs;

    public void main(String[] args){
        String nickName = loginView.nickText.getText().trim(); //로그인 시 사용자에게 입력받은 nickname을 사용

        Socket socket = new Socket();
        try{
            socket.connect(new InetSocketAddress(server_ip,server_port));
            consolLog("채팅방에 입장하였습니다.");
            System.out.println("name : "+nickName);
            new MainChatView(nickName,socket).showMainChatFrame(); ////고쳐야돼 해당되는거

            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8),true);
            String request = "join:"+nickName+"\r\n";
            printWriter.println(request);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void consolLog(String log){
        System.out.println(log);
    }

}