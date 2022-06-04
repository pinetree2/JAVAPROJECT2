package server;


import server.Room;
import server.dto.ChatRoomDAO;
import server.dto.Database;
import server.dto.MainUserDAO;
import server.dto.UserListDAO;

import java.io.*;
import java.net.*;
import java.util.*;
public class User extends Thread {

    Room myRoom; //채팅방
    Vector<User> Userlist; //서버에 접속해 있는 모든 user
    Vector<User> MainUser; //메인화면에 있는 user
    Vector<Room> Chatroom; //전체 서버에서 생성된 Room
    //Vector<Room> UserRoom; //사용자가 접속해있는 Room

    InputStream is;
    DataInputStream dis;
    OutputStream os;
    DataOutputStream dos;

    Database db = new Database();

    Socket s;
    String nickName;

    User(Socket s, TCPServer server){

        Userlist = server.Userlist;
        MainUser = server.MainUser;
        Chatroom = server.Chatroom;
        this.s = s;
        try {
            os = s.getOutputStream();
            dos = new DataOutputStream(os);
            is = s.getInputStream();
            dis = new DataInputStream(is);
            start();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        try {
            while (true) {
                String msg = dis.readUTF();
                if (msg == null) return;
                String[] msgs = msg.split("\\|");
                String protocol = msgs[0];

                switch (protocol) {
                    case "100": //user main에 입장 (
                        Userlist.add(this);
                        MainUser.add(this);

                        UserListDAO.userlistDAO(Userlist);
                        MainUserDAO.MainuserDAO(MainUser);


                        break;
                    case "150": //새로운 방 만들기 -> db에 추가하는 과정 추가 필요
                        myRoom = new Room();
                        myRoom.title = msgs[1];
                        myRoom.count = 1;
                        Chatroom.add(myRoom);
                        myRoom.u.add(this);
                        ChatRoomDAO.chatroomdao(Chatroom);
                        break;
                    case "200": //채팅방 입장하기
                        for (int i = 0; i < Chatroom.size(); i++) {
                            Room r = Chatroom.get(i);
                            if (r.title.equals(msgs[1])) {

                            }
                        }
                        break;
                    case "250": //채팅방 나가기
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }