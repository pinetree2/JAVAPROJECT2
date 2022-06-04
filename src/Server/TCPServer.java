package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer {
    public static final int PORT = 7777;
    Vector<User> Userlist;
    Vector<User> MainUser;
    Vector<Room> Chatroom;
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        TCPServer server = new TCPServer();
        server.Userlist = new Vector<>();
        server.MainUser = new Vector<>();
        server.Chatroom = new Vector<>();

        try {
// 1. 서버 소켓 생성
            serverSocket = new ServerSocket();

// 2. 바인딩
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            serverSocket.bind( new InetSocketAddress(hostAddress, PORT) );
            consoleLog("연결 기다림 - " + hostAddress + ":" + PORT);

// 3. 요청 대기
            while(true) {
                Socket socket = serverSocket.accept();
                User c = new User(socket, server);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if( serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consoleLog(String log) {
        System.out.println("[server " + Thread.currentThread().getId() + "] " + log);
    }
}