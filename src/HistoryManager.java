import java.util.ArrayList;

public interface HistoryManager {
    void add(Task task);
    void remove (Task task);
    ArrayList<String> getHistory(ArrayList<String> history);
}
