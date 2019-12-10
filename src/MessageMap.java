
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that contains data and works with it
 *
 * @author Luca Landolfo
 */
public class MessageMap {

    static ConcurrentHashMap map;
    private int size;
    private String value;
    private String latest;

    public MessageMap() {
        this.map = new ConcurrentHashMap<String, String>();
        this.size = 0;
        this.value = null;
        this.latest = null;
    }

    public void addClient(String user) {
        map.put(user, "");
    }

    public void addMessage(String user, String msg) {
        setLatest(user);
        map.put(user, msg);
        incrementSize();
    }

    public void remove(String username) {
        map.remove(username);
        System.out.println(username + " successfully removed.");
    }

    public int getSize() {
        return this.size;
    }

    private void incrementSize() {
        this.size++;
    }

    private String getValue() {
        return value;
    }

    private void setValue() {
        this.value = (String) map.get(getLatest());
    }

    private String getLatest() {
        return latest;
    }

    private void setLatest(String latest) {
        this.latest = latest;
    }

    @Override
    public String toString() {
        setValue();
        return getLatest() + ": " + getValue();
    }

}
