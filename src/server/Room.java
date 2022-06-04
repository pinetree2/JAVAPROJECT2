package server;
import java.util.Vector;
public class Room {
    String title;
    int count;
    Vector<User> u;
    public Room() {
        u = new Vector<>();
    }
}