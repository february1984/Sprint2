import java.util.HashMap;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        HashMap<String, Task> taskList = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        String command = "Start";
        Task currentTask;
        File file = new File("TaskList.txt");

        while (!command.equals("E")){
            System.out.println("""
                    1 - Ввод задачи
                    2 - Вывод задачи
                    """);
            command = scanner.nextLine();
            switch (command) {
                case "1" -> {
                    Task newTask = Manager.createTask();
                    taskList.put(newTask.name, newTask);
                }
                case "2" -> {
                    System.out.println("Какую задачу хотим посмотреть?");
                    String taskName = scanner.nextLine();
                    try {
                        currentTask = taskList.get(taskName);
                        Manager.showTask(currentTask);
                    } catch (Exception e) {
                        System.out.println("Такой задачи нет\n");
                    }
                }
            }
        }
    }
}
