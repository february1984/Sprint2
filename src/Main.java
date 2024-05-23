import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) throws IOException {
        HashMap<String, Task> taskList;
        Scanner scanner = new Scanner(System.in);
        String command = "Start";
        Task currentTask;
        //стартовая загрузка из файла
        taskList = Manager.loadTaskFromFile();
        while (!command.equals("E")){
            System.out.println("""
                    1 - Ввести задачу
                    2 - Показать задачу
                    3 - Удалить все задачи
                    4 - Вывести все задачи
                    5- Удалить задачу
                    6- Обновить задачу
                    """);
            command = scanner.nextLine();
            switch (command) {
                case "1" -> {
                    Task newTask = Manager.createTask();
                    taskList.put(newTask.name, newTask);
                    Manager.saveTaskToFile(newTask);
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
                case "3" -> Manager.deleteAllTasks(taskList);
                case "4" -> Manager.printAllTasks(taskList);
                case "5" -> {
                    System.out.println("Какую задачу хотим удалить?");
                    String taskToDelete = scanner.nextLine();
                    taskList = Manager.deleteTask(taskToDelete, taskList);
                }
                case "6" -> {
                    System.out.println("Какую задачу хотим обновить?");
                    String taskToUpdate = scanner.nextLine();
                    taskList = Manager.updateTask(taskToUpdate, taskList);
                }
            }
        }
    }
}
