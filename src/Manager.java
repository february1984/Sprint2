import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Manager {
    static Scanner scanner = new Scanner(System.in);
    static int id = 1;
    //Загрузка данных из файла и в файл
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
    public static void saveTaskToFile(Task task) throws IOException {
        FileWriter writer = new FileWriter("TaskList.txt", true);
        writer.write(task.id + "," + task.status + "," + task.name + "," + task.overview + "," + task.taskType + "\n");
        writer.close();
    }
    public static void saveEpicToFile(Epic epic) throws IOException {
        FileWriter writer = new FileWriter("EpicList.txt", true);
        writer.write(epic.id + "," + epic.status + "," + epic.name + "," + epic.overview + "," + epic.taskType + "\n");
        writer.close();
    }
    public static void saveSubtaskToFile(Subtask subtask) throws IOException {
        FileWriter writer = new FileWriter("SubtaskList.txt", true);
        writer.write(subtask.id + "," + subtask.status + "," + subtask.name + "," + subtask.overview + "," +
                subtask.taskType + "," + subtask.parentID + "\n");
        writer.close();
    }


    public static Task createTask () throws IOException {
        Task taskToSet = new Task();

        System.out.println("Заводим новую задачу:\n");
        //Сразу присваиваем новой задаче ID из файла и увеличиваем для следующей
        BufferedReader reader = new BufferedReader(new FileReader("CurrentMaxTaskID.txt"));
        String maxIndex = reader.readLine();
        reader.close();
        id = Integer.parseInt(maxIndex);
        taskToSet.id = id;
        id++;
        //Ввод параметров задачи. Статус всегда NEW
        System.out.println("Как назовем задачу?");
        taskToSet.name = scanner.nextLine();
        System.out.println("Что будем делать?");
        taskToSet.overview = scanner.nextLine();
        taskToSet.status = "NEW";
        taskToSet.taskType = "Task";
        // Записываем в файл максимальный индекс
        FileWriter idChanger = new FileWriter("CurrentMaxTaskID.txt");
        idChanger.write(String.valueOf(id));
        idChanger.close();
        return taskToSet;
    }
    public static void showTask(Task task) {
        System.out.println(task.id + "," + task.status + "," + task.name + "," + task.overview + "," + task.taskType + "\n");
    }
    public static void deleteAllTasks (HashMap<Integer,Task> taskToClear) throws IOException {
        FileWriter taskCleaner = new FileWriter("TaskList.txt");
        taskCleaner.write("");
        taskCleaner.close();
        taskToClear.clear();
    }
    public static void printAllTasks(HashMap<Integer,Task> taskList){
        for (Integer key : taskList.keySet()) {
        Manager.showTask(taskList.get(key));
        }
    }
    public static HashMap<Integer, Task> deleteTask (Integer taskToDelete, HashMap<Integer, Task> currentTaskList) throws IOException {
        HashMap<Integer, Task> taskListDeleteFrom = loadTaskFromFile();
        taskListDeleteFrom.remove(taskToDelete);
        Manager.deleteAllTasks(currentTaskList);
        for (Integer key : taskListDeleteFrom.keySet()) {
            Manager.saveTaskToFile(taskListDeleteFrom.get(key));
        }
        return taskListDeleteFrom;
    }
    public static HashMap<Integer,Task> updateTask (Integer taskToUpdateName, HashMap<Integer, Task> currentTaskList) throws IOException{
        HashMap<Integer, Task> taskListToUpdate = loadTaskFromFile();
        Task taskToUpdate = taskListToUpdate.get(taskToUpdateName);
        System.out.println("Что делаем теперь?");
        taskToUpdate.overview = scanner.nextLine();
        taskListToUpdate = deleteTask(taskToUpdateName, currentTaskList);
        taskListToUpdate.put(taskToUpdateName, taskToUpdate);
        Manager.saveTaskToFile(taskToUpdate);
        return taskListToUpdate;
    }
    public static Epic createEpic () throws IOException {
        Epic epicToSet = new Epic();

        System.out.println("Заводим новую задачу:\n");
        //Сразу присваиваем новой задаче ID из файла и увеличиваем для следующей
        BufferedReader reader = new BufferedReader(new FileReader("CurrentMaxTaskID.txt"));
        String maxIndex = reader.readLine();
        reader.close();
        id = Integer.parseInt(maxIndex);
        epicToSet.id = id;
        id++;
        //Ввод параметров задачи. Статус всегда NEW
        System.out.println("Как назовем эпик?");
        epicToSet.name = scanner.nextLine();
        System.out.println("Что будем делать?");
        epicToSet.overview = scanner.nextLine();
        epicToSet.status = "NEW";
        epicToSet.taskType = "Epic";
        // Записываем в файл максимальный индекс
        FileWriter idChanger = new FileWriter("CurrentMaxTaskID.txt");
        idChanger.write(String.valueOf(id));
        idChanger.close();
        return epicToSet;
    }
    public static Subtask createSubtask (HashMap<Integer,Epic> epicList) throws IOException {
        Subtask subtaskToSet = new Subtask();
        System.out.println("Подзадача какого эпика?");
        subtaskToSet.parentID = Integer.parseInt(scanner.nextLine());
        if (!epicList.containsKey(subtaskToSet.parentID)){
            System.out.println("Нет такого эпика. Список доступных эпиков:\n" + epicList.keySet());
        } else {
            System.out.println("Заводим новую подзадачу:\n");
            //Сразу присваиваем новой задаче ID из файла и увеличиваем для следующей
            BufferedReader reader = new BufferedReader(new FileReader("CurrentMaxTaskID.txt"));
            String maxIndex = reader.readLine();
            reader.close();
            id = Integer.parseInt(maxIndex);
            subtaskToSet.id = id;
            id++;
            //Ввод параметров задачи. Статус всегда NEW
            System.out.println("Как назовем подзадачу?");
            subtaskToSet.name = scanner.nextLine();
            System.out.println("Что будем делать?");
            subtaskToSet.overview = scanner.nextLine();
            subtaskToSet.status = "NEW";
            subtaskToSet.taskType = "Subtask";
            // Записываем в файл максимальный индекс
            FileWriter idChanger = new FileWriter("CurrentMaxTaskID.txt");
            idChanger.write(String.valueOf(id));
            idChanger.close();
        }
            return subtaskToSet;
    }
}
