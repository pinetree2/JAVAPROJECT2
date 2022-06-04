package Server;
import java.util.Vector;
public class Room {
    public String title;
    public int RoomIdx;
    int count;
    Vector<User> u;
    public Room() {
        u = new Vector<>();
    }

}