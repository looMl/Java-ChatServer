
import java.util.Enumeration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Luca
 */
public class MessageMap {

    static ConcurrentHashMap map;
    private int size;

    public MessageMap() {
        this.map = new ConcurrentHashMap<String, String>();
    }

    public void addClient(String user){
        map.put(user, "");
        size++;
    }
    
    public void addMessage(String user, String msg){
        map.put(user, msg);
    }
    
    @Override
    public String toString(){
        String value = (String) map.get(map.size() - 1);
        return getKey() + ": " + value;
    }
    
    public String getKey(){
        Enumeration key = map.keys();
        String username = "";
        
        while(key.hasMoreElements()){
            username = (String) key.nextElement();
        }
        
        return username;
    }
    
    public Set entrySet(){
        return map.entrySet();
    }
    
    public int getSize(){
        return size;
    }
    
}
