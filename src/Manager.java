import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public interface Manager {
    enum taskType{
        TASK,
        SUBTASK,
        EPIC
    }

    static HashMap<Integer, Task> loadTaskFromFile() {
        String file;
        HashMap<Integer, Task> taskList = new HashMap<>();

        try {
            file = Files.readString(Path.of("C:\\Users\\Admin\\IdeaProjects\\Sprint2\\TaskList.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (file != null) {
            String[] lines = file.split("\\n");
            if (!Objects.equals(lines[0], "")) {
                for (String line : lines) {
                    Task currentTask = new Task();
                    String[] currentValue = line.split(",");
                    currentTask.id = Integer.parseInt(currentValue[0]);
                    currentTask.status = currentValue[1];
                    currentTask.name = currentValue[2];
                    currentTask.overview = currentValue[3];
                    currentTask.taskType = currentValue[4];
                    taskList.put(currentTask.id, currentTask);
                }
            }
        }
        return taskList;
    }
    static HashMap<Integer, Epic> loadEpicFromFile() {
        String file;
        HashMap<Integer, Epic> epicList = new HashMap<>();

        try {
            file = Files.readString(Path.of("C:\\Users\\Admin\\IdeaProjects\\Sprint2\\EpicList.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (file != null) {
            String[] lines = file.split("\\n");
            if (!Objects.equals(lines[0], "")) {
                for (String line : lines) {
                    Epic currentEpic = new Epic();
                    String[] currentValue = line.split(",");
                    currentEpic.id = Integer.parseInt(currentValue[0]);
                    currentEpic.status = currentValue[1];
                    currentEpic.name = currentValue[2];
                    currentEpic.overview = currentValue[3];
                    currentEpic.taskType = currentValue[4];
                    epicList.put(currentEpic.id, currentEpic);
                }
            }
        }
        return epicList;
    }
    static HashMap<Integer, Subtask> loadSubtaskFromFile() {
        String file;
        HashMap<Integer, Subtask> subtaskList = new HashMap<>();

        try {
            file = Files.readString(Path.of("C:\\Users\\Admin\\IdeaProjects\\Sprint2\\SubtaskList.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (file != null) {
            String[] lines = file.split("\\n");
            if (!Objects.equals(lines[0], "")) {
                for (String line : lines) {
                    Subtask currentSubtask = new Subtask();
                    String[] currentValue = line.split(",");
                    currentSubtask.id = Integer.parseInt(currentValue[0]);
                    currentSubtask.status = currentValue[1];
                    currentSubtask.name = currentValue[2];
                    currentSubtask.overview = currentValue[3];
                    currentSubtask.taskType = currentValue[4];
                    currentSubtask.parentID = Integer.parseInt(currentValue[5]);
                    subtaskList.put(currentSubtask.id, currentSubtask);
                }
            }
        }
        return subtaskList;
    }
    static int loadIdFromFile () throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("CurrentMaxTaskID.txt"));
        String maxIndex = reader.readLine();
        reader.close();
        return  Integer.parseInt(maxIndex);
    }
    static ArrayList<String> loadTaskHistoryFromFile() {
        String file;
        ArrayList<String> taskHistory = new ArrayList<>();

        try {
            file = Files.readString(Path.of("C:\\Users\\Admin\\IdeaProjects\\Sprint2\\TaskHistory.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (file != null) {
            String[] lines = file.split("\\n");
            if (!Objects.equals(lines[0], "")) {
                taskHistory.addAll(Arrays.asList(lines));
            }
        }
        return taskHistory;
    }
    static void saveTaskListToFile(HashMap<Integer, Task> taskList) throws IOException {
        FileWriter cleaner = new FileWriter("TaskList.txt",false);
        cleaner.write("");
        cleaner.close();
        FileWriter writer = new FileWriter("TaskList.txt", true);
        for (Integer key : taskList.keySet()) {
            writer.write(taskList.get(key).id + "," + taskList.get(key).status + "," + taskList.get(key).name + "," +
                    taskList.get(key).overview + "," + taskList.get(key).taskType + "\n");
        }
        writer.close();
    }
    static void saveEpicListToFile(HashMap<Integer, Epic> epicList) throws IOException {
        FileWriter cleaner = new FileWriter("EpicList.txt",false);
        cleaner.write("");
        cleaner.close();
        FileWriter writer = new FileWriter("EpicList.txt", true);
        for (Integer key : epicList.keySet()) {
            writer.write(epicList.get(key).id + "," + epicList.get(key).status + "," + epicList.get(key).name +
                    "," + epicList.get(key).overview + "," + epicList.get(key).taskType + "\n");
        }
        writer.close();
    }
    static void saveSubtaskListToFile(HashMap<Integer, Subtask> subtaskList) throws IOException {
        FileWriter cleaner = new FileWriter("SubtaskList.txt",false);
        cleaner.write("");
        cleaner.close();
        FileWriter writer = new FileWriter("SubtaskList.txt", true);
        for (Integer key : subtaskList.keySet()) {
            writer.write(subtaskList.get(key).id + "," + subtaskList.get(key).status + "," + subtaskList.get(key).name +
                    "," + subtaskList.get(key).overview + "," + subtaskList.get(key).taskType + "," + subtaskList.get(key).parentID + "\n");
        }
        writer.close();
    }
    static void saveIdToFile(int id) throws IOException {
        FileWriter writer = new FileWriter("CurrentMaxTaskID.txt", false);
        writer.write(String.valueOf(id));
        writer.close();
    }
    static void saveTaskHistoryToFile(ArrayList<String> history) throws IOException {
        FileWriter cleaner = new FileWriter("TaskHistory.txt",false);
        cleaner.write("");
        cleaner.close();
        FileWriter writer = new FileWriter("TaskHistory.txt", true);
        for (String currView: history) {
            writer.write(currView +'\n');
        }
        writer.close();
    }

    void showTaskViewsHistory();
    Task createTask(int maxId, String name, String overview);
    void showTask(Task task);
    void showAllTasks (HashMap<Integer,Task> taskList);
    void deleteTask (Integer taskToDelete, HashMap<Integer, Task> currentTaskList);
    void deleteAllTasks (HashMap<Integer,Task> taskListToClear);
    void updateTask (Integer taskToUpdateID, HashMap<Integer, Task> currentTaskList, String completeCommand, String updateCommand, String newOverview);
    Epic createEpic (String name, String overview, int maxId);
    void showEpic(Epic epic, HashMap<Integer, Subtask> subtaskList);
    void showAllEpics(HashMap<Integer,Epic> epicList, HashMap<Integer,Subtask> subtaskList);
    void deleteEpic (Integer epicToDelete, HashMap <Integer, Epic> epicList,
                                   HashMap<Integer, Subtask> subtaskList, String subtaskIncluding);
    void deleteAllEpics (HashMap<Integer,Epic> epicListToClear, HashMap<Integer,Subtask> subtaskListToClear);
    void updateEpic (Integer epicToUpdateID, HashMap<Integer, Epic> epicList, String newOverview);
    Subtask createSubtask (int maxId, int parentID, HashMap<Integer,Epic> epicList, String name, String overview);
    void showSubtask(Subtask subtask);
    void showAllSubtasks(HashMap<Integer,Subtask> subtaskList);
    void deleteSubtask (Integer subtaskToDelete,
                                      HashMap <Integer, Subtask> currentSubtaskList,
                                      HashMap <Integer, Epic> currentEpicList);
    void deleteAllSubtasks (HashMap<Integer,Subtask> subtaskListToClear);

    void updateSubtask (Integer subtaskToUpdateID, HashMap<Integer, Subtask> currentSubtaskList,
                                      HashMap <Integer, Epic> currentEpicList, String completeCommand, String changeCommand, String newOverview);
}