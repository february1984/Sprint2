import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Manager {
    static Scanner scanner = new Scanner(System.in);
    static int id = 1;

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
        // Записываем в файл максимальный индекс
        FileWriter idChanger = new FileWriter("CurrentMaxTaskID.txt");
        idChanger.write(String.valueOf(id));
        idChanger.close();
        return taskToSet;
    }
    public static HashMap<String, Task> loadTaskFromFile() {
        String file;
        HashMap<String, Task> taskList = new HashMap<>();

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
                    taskList.put(currentTask.name, currentTask);
                }
            }
        }
        return taskList;
    }
    public static void showTask(Task task) {
        System.out.println(task.id + "," + task.status + "," + task.name + "," + task.overview + "\n");
    }
    public static void saveTaskToFile(Task task) throws IOException {
        FileWriter writer = new FileWriter("TaskList.txt", true);
        writer.write(task.id + "," + task.status + "," + task.name + "," + task.overview + "\n");
        writer.close();
    }
    public static void deleteAllTasks (HashMap<String,Task> taskToClear) throws IOException {
        FileWriter taskCleaner = new FileWriter("TaskList.txt");
        taskCleaner.write("");
        taskCleaner.close();
        FileWriter taskIdCleaner = new FileWriter("CurrentMaxTaskID.txt");
        taskIdCleaner.write("1");
        taskIdCleaner.close();
        taskToClear.clear();
    }
    public static void printAllTasks(HashMap<String,Task> taskList){
        for (String key : taskList.keySet()) {
        Manager.showTask(taskList.get(key));
        }
    }
}
