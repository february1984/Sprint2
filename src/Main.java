import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) throws IOException {
        HashMap<Integer, Task> taskList;
        HashMap<Integer, Epic> epicList;
        HashMap<Integer, Subtask> subtaskList;
        Scanner scanner = new Scanner(System.in);
        String command = "Start";
        Task currentTask;
        //стартовая загрузка из файла
        taskList = Manager.loadTaskFromFile();
        epicList = Manager.loadEpicFromFile();
        subtaskList = Manager.loadSubtaskFromFile();
        while (!command.equals("E")){
            System.out.println("""
                    1 - Ввести задачу
                    2 - Показать задачу
                    3 - Удалить все задачи
                    4 - Вывести все задачи
                    5- Удалить задачу
                    6- Обновить задачу
                    7 - Создать эпик
                    8 - Создать подзадачу
                    """);
            command = scanner.nextLine();
            switch (command) {
                case "1" -> {
                    Task newTask = Manager.createTask();
                    taskList.put(newTask.id, newTask);
                    Manager.saveTaskToFile(newTask);
                }
                case "2" -> {
                    System.out.println("Какую задачу хотим посмотреть?");
                    String taskID = scanner.nextLine();
                    try {
                        currentTask = taskList.get(Integer.parseInt(taskID));
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
                    taskList = Manager.deleteTask(Integer.parseInt(taskToDelete), taskList);
                }
                case "6" -> {
                    System.out.println("Какую задачу хотим обновить?");
                    String taskToUpdate = scanner.nextLine();
                    taskList = Manager.updateTask(Integer.parseInt(taskToUpdate), taskList);
                }
                case "7" -> {
                    Epic newEpic = Manager.createEpic();
                    epicList.put(newEpic.id, newEpic);
                    Manager.saveEpicToFile(newEpic);
                }
                case "8" -> {
                    Subtask newSubtask = Manager.createSubtask(epicList);
                    if (newSubtask.name != null){
                    subtaskList.put(newSubtask.id, newSubtask);
                    Manager.saveSubtaskToFile(newSubtask);
                    }
                }
            }
        }
    }
}
