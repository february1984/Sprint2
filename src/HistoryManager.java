import java.util.ArrayList;

public interface HistoryManager {
    void add(Task task);
    void removeNode (Task task);
    ArrayList<String> getHistory(ArrayList<String> history);
}
