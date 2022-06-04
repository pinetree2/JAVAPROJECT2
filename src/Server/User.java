package Server;


import Server.dto.ChatRoomDAO;
import Server.dto.Database;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
public class User extends Thread {

    //Room myRoom; //채팅방
    Vector<User> Userlist; //서버에 접속해 있는 모든 user
    Vector<User> MainUser; //메인화면에 있는 user
    Vector<Room> Chatroom; //전체 서버에서 생성된 Room
    Vector<Room> myRoom; //사용자가 접속해있는 Room


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
                        nickName =msgs[1];


                        break;
                    case "150": //새로운 방 만들기 -> db에 추가하는 과정 추가 필요
                        Room r = new Room();
                        r.title = msgs[1];
                        r.count = 1; //접속
                        Chatroom.add(r);
                        r.u.add(this);
                        myRoom.add(r);
                        ChatRoomDAO.chatroomdao(r);
                        break;
                    case "200": //채팅방 입장하기
                        int ch = 0;

                        for (int i = 0; i < Chatroom.size(); i++) {
                            Room room = Chatroom.get(i);
                            if (room.title.equals(msgs[1])) {
                                myRoom.add(room);
                                room.count++;
                                room.u.add(this);
                                ch++;

                            }
                        }
                        if(ch == 0) { //현재 접속해 있는 인원 중 아무도 들어가 있지 않은 방일 경우
                            Room room = new Room();
                            room.title = msgs[1];
                            room.count = 1;
                            Chatroom.add(room);
                            room.u.add(this);
                            myRoom.add(room);
                        }
                        break;
                    case "250": //채팅방 나가기
                        for(int i = 0; i < myRoom.size(); i++) {
                            Room room = myRoom.get(i);
                            if(room.title.equals(msgs[1])) {
                                myRoom.remove(room);
                                room.count--;
                                if(room.count == 0) {
                                    Chatroom.remove(room);
                                }
                            }
                        }
                        break;
                    case "300": //메시지
                        break;
                    case "350": //초대
                        break;
                    case "400": //로그아웃
                        break;
                    case "450"://메인추가
                        break;
                        //클라이언트로부터 추가 요청 메시지 받음(인덱스,태스크,기한날짜,채팅방인덱스)

                    case "500" ://서브추가
                        break;
                    //클라로부터 추가 요청 메시지 받음 (서브 인덱스, 태스크, 기한날짜,채팅방인덱스)
                    // 작업안에 서브 리스트의 갯수를 취합해서 추가해야함(subnum)



                }
            }
        }catch(IOException e) {
            System.out.println("User class error");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }//run
    public void messageTo(String msg) throws IOException{
        dos.write((msg+"\n").getBytes()); //수정 필요
    }
    public void messageRoom(String msg) {

    }
}