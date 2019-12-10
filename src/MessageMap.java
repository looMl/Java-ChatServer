
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Luca
 */
public class MessageMap {

    static ConcurrentHashMap map;
    private int size;
    private String value;

    public MessageMap() {
        this.map = new ConcurrentHashMap<String, String>();
        this.size = 0;
        this.value = "";
    }

    public void addClient(String user) {
        map.put(user, "");
    }

    public void addMessage(String user, String msg) {
        map.put(user, msg);
        this.size++;
    }

    public String toString(String user) {
        value = (String) map.get(user);
        return user + ": " + value;
    }

    public int getSize() {
        return size;
    }
    
}
