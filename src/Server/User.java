package Server;


import Server.dao.ChatRoomDAO;
import Server.dao.LogInDAO;
import Server.dto.Database;
import Server.dao.LogOutDAO;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Server.dao.MainToDoDAO.Maintododao;


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
    String id;

    public User(Socket s, TCPServer server){

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
                    case "100": //user main에 입장(로그인) (
                        Userlist.add(this);
                        MainUser.add(this);
                        id = msgs[1]; //("100|id")
                        LogInDAO.logindao(id);
                        break;

                    case "150": //새로운 방 만들기 -> db에 추가하는 과정 추가 필요
                        Room r = new Room();
                        r.RoomIdx = Integer.parseInt(msgs[1]);
                        r.title = msgs[1];
                        r.count = 1;
                        Chatroom.add(r);
                        r.u.add(this);
                        myRoom.add(r);

                        ChatRoomDAO.chatroomdao(r);
                        break;
                    case "200": //채팅방 입장하기
                        int ch = 0;

                        for (int i = 0; i < Chatroom.size(); i++) {
                            Room room = Chatroom.get(i);
                            if(Integer.parseInt(msgs[1]) == room.RoomIdx) {
                                myRoom.add(room);
                                room.count++;
                                room.u.add(this);
                                ch++;
                                break;
                            }

                        }
                        if(ch == 0) { //현재 접속해 있는 인원 중 아무도 들어가 있지 않은 방일 경우
                            Room room = new Room();
                            room.RoomIdx = Integer.parseInt(msgs[1]);
                            room.title = msgs[2];
                            room.count = 1;
                            Chatroom.add(room);
                            room.u.add(this);
                            myRoom.add(room);
                        }

                        break;
                    case "250": ///채팅방 나가기 ("250|나가고자 하는 db 채팅방 인덱스")
                        for(int i = 0; i < Chatroom.size(); i++) { //채팅방의 유저리스트에서 사용자 삭제하기
                            Room room = Chatroom.get(i);
                            if(Integer.parseInt(msgs[1]) == room.RoomIdx) {
                                room.u.remove(this);
                                room.count--;
                                if(room.count==0) {
                                    Chatroom.remove(room);
                                }
                                break;
                            }

                        }
                        for(int i = 0; i < myRoom.size(); i++) { //사용자가 접속한 방 리스트에서 방을 제거
                            Room room = myRoom.get(i);
                            if(Integer.parseInt(msgs[1]) == room.RoomIdx) {
                                myRoom.remove(i);
                                break;
                            }
                        }
                        break;


                    case "300": //메시지
                        break;
                    case "350": //초대
                        //채텡방 내에 전체 메세지 보내주기
                        //db에 추가만 해주기

                        break;


                    case "400": //로그아웃 (유저 id를 받아와야할듯)
                        //status 바꾸는 함수
                        for(int i = 0; i < myRoom.size(); i++) { //현재 방 안의 접속자배열에서 삭제
                            for(int j = 0; j < myRoom.get(i).u.size(); j++) {
                                if(this.id.equals(myRoom.get(i).u.get(j).id)) {
                                    myRoom.get(i).u.remove(j);
                                }
                            }
                        }
                        Userlist.remove(this);
                        MainUser.remove(this);
                        String UserID = null;
                        id = msgs[1]; //("100|id")
                        LogOutDAO.logoutdao(id); //로그아웃 상태변경

                        break;


                    case "450": //메인 투두리스트 추가 ("450|Main_idx|Main_task|chat_index")
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일");
                        Date date = formatter.parse(msgs[3]);
                        Maintododao(Integer.parseInt(msgs[1]), msgs[2], date, Integer.parseInt(msgs[4]));

                        break;
                        //클라이언트로부터 추가 요청 메시지 받음(인덱스,태스크,기한날짜,채팅방인덱스)

                    case "500" ://서브추가
                        break;
                    //클라로부터 추가 요청 메시지 받음 (메인인덱스,서브 인덱스, 태스크, 기한날짜,채팅방인덱스)
                    // 이 작업안에 서브 리스트의 갯수를 취합해서 전달해야함(subnum)
                    case "550": // 완료
                        break;




                }
            }
        }catch(IOException e) {
            System.out.println("User class error");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }//run
    public void messageTo(String msg) throws IOException{
        dos.write((msg+"\n").getBytes()); //수정 필요
    }
    public void messageRoom(String msg) {

    }
}