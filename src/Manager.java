import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Manager {
    static Scanner scanner = new Scanner(System.in);

    public static HashMap<Integer, Task> loadTaskFromFile() {
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
    public static HashMap<Integer, Epic> loadEpicFromFile() {
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
    public static HashMap<Integer, Subtask> loadSubtaskFromFile() {
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
    public static int loadIdFromFile () throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("CurrentMaxTaskID.txt"));
        String maxIndex = reader.readLine();
        reader.close();
        return  Integer.parseInt(maxIndex);
    }
    public static void saveTaskListToFile(HashMap<Integer, Task> taskList) throws IOException {
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
    public static void saveEpicListToFile(HashMap<Integer, Epic> epicList) throws IOException {
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
    public static void saveSubtaskListToFile(HashMap<Integer, Subtask> subtaskList) throws IOException {
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
    public static void saveIdToFile(int id) throws IOException {
        FileWriter writer = new FileWriter("CurrentMaxTaskID.txt", false);
        writer.write(String.valueOf(id));
        writer.close();
    }

    public static Task createTask(int maxId) {
        Task taskToSet = new Task();
        System.out.println("Заводим новую задачу:\n");
        taskToSet.id = maxId;
        System.out.println("Как назовем задачу?");
        taskToSet.name = scanner.nextLine();
        System.out.println("Что будем делать?");
        taskToSet.overview = scanner.nextLine();
        taskToSet.status = "NEW";
        taskToSet.taskType = "Task";
        return taskToSet;
    }
    public static void showTask(Task task) {
        System.out.println(task.id + "," + task.status + "," + task.name + "," + task.overview + "," + task.taskType + "\n");
    }
    public static void showAllTasks (HashMap<Integer,Task> taskList){
        for (Integer key : taskList.keySet()) {
            Manager.showTask(taskList.get(key));
        }
    }
    public static void deleteTask (Integer taskToDelete, HashMap<Integer, Task> currentTaskList) {
        currentTaskList.remove(taskToDelete);
    }
    public static void deleteAllTasks (HashMap<Integer,Task> taskListToClear) {
        taskListToClear.clear();
    }
    public static void updateTask (Integer taskToUpdateID, HashMap<Integer, Task> currentTaskList) {
        System.out.println("Выполнили задачу? Y/N");
        String command = scanner.nextLine();
        if (command.equals("Y")){
            if (currentTaskList.get(taskToUpdateID).status.equals("NEW")) {
                currentTaskList.get(taskToUpdateID).status = "DONE";
            } else {
                System.out.println("Задача уже выполнена");
            }
        }
        System.out.println("Задача изменилась?");
        command = scanner.nextLine();
        if (command.equals("Y")) {
            System.out.println("Что делаем теперь?");
            currentTaskList.get(taskToUpdateID).overview = scanner.nextLine();
        }
    }
    public static Epic createEpic (int maxId) {
        Epic epicToSet = new Epic();
        System.out.println("Заводим новый эпик:\n");
        epicToSet.id = maxId;
        System.out.println("Как назовем эпик?");
        epicToSet.name = scanner.nextLine();
        System.out.println("Что будем делать?");
        epicToSet.overview = scanner.nextLine();
        epicToSet.status = "NEW";
        epicToSet.taskType = "Epic";
        return epicToSet;
    }
    public static void showEpic(Epic epic, HashMap<Integer, Subtask> subtaskList) {
        System.out.println(epic.id + "," + epic.status + "," + epic.name + "," + epic.overview + "," + epic.taskType);
        for (Integer key : subtaskList.keySet()) {
            if (subtaskList.get(key).parentID == epic.id){
                System.out.println("\t" + subtaskList.get(key).id + "," + subtaskList.get(key).status + "," + subtaskList.get(key).name + "," + subtaskList.get(key).overview);
            }
        }
    }
    public static void showAllEpics(HashMap<Integer,Epic> epicList, HashMap<Integer,Subtask> subtaskList){
        for (Integer key : epicList.keySet()) {
            Manager.showEpic(epicList.get(key),subtaskList);
        }
    }
    public static void deleteEpic (Integer epicToDelete, HashMap <Integer, Epic> epicList,
                                        HashMap<Integer, Subtask> subtaskList, String subtaskIncluding) {
        ArrayList <Integer> subtasksIdToDelete = new ArrayList<>();
        if (subtaskIncluding.equals("withSubtasks")) {
            for (Integer key : subtaskList.keySet()) {
                if (subtaskList.get(key).parentID == epicToDelete) {
                    subtasksIdToDelete.add(key);
                }
            }
            for (Integer id : subtasksIdToDelete) {
                subtaskList.remove(id);
            }
        }
        epicList.remove(epicToDelete);
    }
    public static void deleteAllEpics (HashMap<Integer,Epic> epicListToClear, HashMap<Integer,Subtask> subtaskListToClear) {
        epicListToClear.clear();
        subtaskListToClear.clear();
    }
    public static void updateEpic (Integer epicToUpdateID, HashMap<Integer, Epic> epicList){
        while (!epicList.containsKey(epicToUpdateID)){
            System.out.println("Такого эпика нет. Список доступных эпиков: " + epicList.keySet());
            System.out.println("Пожалуйста введите эпик снова");
            epicToUpdateID = Integer.parseInt(scanner.nextLine());
        }
        System.out.println("Что делаем теперь?");
        epicList.get(epicToUpdateID).overview = scanner.nextLine();
    }
    public static Subtask createSubtask (int maxId, HashMap<Integer,Epic> epicList){
        Subtask subtaskToSet = new Subtask();
        System.out.println("Подзадача какого эпика?");
        subtaskToSet.parentID = Integer.parseInt(scanner.nextLine());
        while (!epicList.containsKey(subtaskToSet.parentID)){
            System.out.println("Такого эпика нет. Список доступных эпиков: " + epicList.keySet());
            System.out.println("Пожалуйста введите эпик снова");
            subtaskToSet.parentID = Integer.parseInt(scanner.nextLine());
        }
            System.out.println("Заводим новую подзадачу:\n");
            subtaskToSet.id = maxId;
            System.out.println("Как назовем подзадачу?");
            subtaskToSet.name = scanner.nextLine();
            System.out.println("Что будем делать?");
            subtaskToSet.overview = scanner.nextLine();
            subtaskToSet.status = "NEW";
            subtaskToSet.taskType = "Subtask";
            if (epicList.get(subtaskToSet.parentID).status.equals("DONE")){
                epicList.get(subtaskToSet.parentID).status = "IN_PROGRESS";
            }
        return subtaskToSet;
    }
    public static void showSubtask(Subtask subtask) {
        System.out.println(subtask.id + "," + subtask.status + "," + subtask.name + "," + subtask.overview + "," + subtask.taskType + "\n");
    }
    public static void showAllSubtasks(HashMap<Integer,Subtask> subtaskList){
        for (Integer key : subtaskList.keySet()) {
            Manager.showSubtask(subtaskList.get(key));
        }
    }
    public static void deleteSubtask (Integer subtaskToDelete,
                                      HashMap <Integer, Subtask> currentSubtaskList,
                                      HashMap <Integer, Epic> currentEpicList) {
        int subtaskDoneCounter = 0;
        int numberOfSubtasks = 0;
        for (Integer key : currentSubtaskList.keySet()) {
            if (currentSubtaskList.get(key).parentID == currentSubtaskList.get(subtaskToDelete).parentID &&
                    !key.equals(subtaskToDelete)) {
                if (currentSubtaskList.get(key).status.equals("DONE")) {
                    subtaskDoneCounter++;
                    numberOfSubtasks++;
                }
                subtaskDoneCounter--;
            }
        }
        if (subtaskDoneCounter == 0 && numberOfSubtasks != 0){
            currentEpicList.get(currentSubtaskList.get(subtaskToDelete).parentID).status = "DONE";
        } else{
            currentEpicList.get(currentSubtaskList.get(subtaskToDelete).parentID).status = "NEW";
        }
        currentSubtaskList.remove(subtaskToDelete);
    }
    public static void deleteAllSubtasks (HashMap<Integer,Subtask> subtaskListToClear) {
        subtaskListToClear.clear();
    }

    public static void updateSubtask (Integer subtaskToUpdateID,
                                      HashMap<Integer, Subtask> currentSubtaskList,
                                      HashMap <Integer, Epic> currentEpicList) {
        int subtaskDoneCounter = 0;

        System.out.println("Выполнили подзадачу? Y/N");
        String command = scanner.nextLine();
        if (command.equals("Y")){
            if (currentSubtaskList.get(subtaskToUpdateID).status.equals("NEW")) {
                currentSubtaskList.get(subtaskToUpdateID).status = "DONE";
                currentEpicList.get(currentSubtaskList.get(subtaskToUpdateID).parentID).status = "IN_PROGRESS";
                for (Integer key : currentSubtaskList.keySet()) {
                    if (currentSubtaskList.get(key).parentID == currentSubtaskList.get(subtaskToUpdateID).parentID) {
                        if (currentSubtaskList.get(key).status.equals("DONE")) {
                            subtaskDoneCounter++;
                        }
                        subtaskDoneCounter--;
                    }
                }
                if (subtaskDoneCounter == 0){
                    currentEpicList.get(currentSubtaskList.get(subtaskToUpdateID).parentID).status = "DONE";
                }
            } else {
                System.out.println("Задача уже выполнена");
            }
        }
        System.out.println("Задача изменилась?");
        command = scanner.nextLine();
        if (command.equals("Y")) {
            System.out.println("Что делаем теперь?");
            currentSubtaskList.get(subtaskToUpdateID).overview = scanner.nextLine();
        }
    }
}
