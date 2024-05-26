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
        Epic currentEpic;
        //стартовая загрузка из файла
        taskList = Manager.loadTaskFromFile();
        epicList = Manager.loadEpicFromFile();
        subtaskList = Manager.loadSubtaskFromFile();
        while (!command.equals("E")){
            System.out.println("""
                    \t\t\t==================================================================================
                    \t\t\t|| Создать\t|| Показать\t|| Показать все\t|| Удалить\t|| Удалить все\t|| Обновить\t||
                    \t\t\t==================================================================================
                    Задачу\t\t|| код 1\t|| код 2\t|| код 3\t\t|| код 4\t|| код 5 \t\t||  код 6 \t||
                    \t\t\t==================================================================================
                    Эпик\t\t|| код 8\t|| код 9\t|| код 10\t\t|| код 11\t|| код 12\t\t||  код 13 \t||
                    \t\t\t==================================================================================
                    Подзадачу\t|| код 15\t|| ------\t|| ------\t\t|| код 18\t|| код 19\t\t||  код ?? \t||
                    \t\t\t==================================================================================
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
                case "5" -> Manager.deleteAllTasks(taskList);
                case "3" -> Manager.printAllTasks(taskList);
                case "4" -> {
                    System.out.println("Какую задачу хотим удалить?");
                    String taskToDelete = scanner.nextLine();
                    taskList = Manager.deleteTask(Integer.parseInt(taskToDelete), taskList);
                }
                case "6" -> {
                    System.out.println("Какую задачу хотим обновить?");
                    String taskToUpdate = scanner.nextLine();
                    taskList = Manager.updateTask(Integer.parseInt(taskToUpdate), taskList);
                }
                case "8" -> {
                    Epic newEpic = Manager.createEpic();
                    epicList.put(newEpic.id, newEpic);
                    Manager.saveEpicToFile(newEpic);
                }
                case "9" -> {
                    System.out.println("Какой эпик хотим посмотреть?");
                    String epicID = scanner.nextLine();
                    try {
                        currentEpic = epicList.get(Integer.parseInt(epicID));
                        Manager.showEpic(currentEpic, subtaskList);
                    } catch (Exception e) {
                        System.out.println("Такой задачи нет\n");
                    }
                }
                case "12" -> Manager.deleteAllEpics(epicList,subtaskList);
                case "10" -> Manager.printAllEpics(epicList,subtaskList);
                case "11" -> {
                    System.out.println("Какой эпик хотим удалить?");
                    String epicToDelete = scanner.nextLine();
                    Manager.deleteEpic(Integer.parseInt(epicToDelete), epicList, subtaskList, "withSubtasks");
                    epicList = Manager.loadEpicFromFile();
                    subtaskList = Manager.loadSubtaskFromFile();
                }
                case "13" -> {
                    System.out.println("Какой эпик хотим обновить?");
                    String taskToUpdate = scanner.nextLine();
                    epicList = Manager.updateEpic(Integer.parseInt(taskToUpdate), epicList, subtaskList);
                    subtaskList = Manager.loadSubtaskFromFile();
                }
                case "15" -> {
                    Subtask newSubtask = Manager.createSubtask(epicList);
                    if (newSubtask.name != null){
                    subtaskList.put(newSubtask.id, newSubtask);
                    Manager.saveSubtaskToFile(newSubtask);
                    }
                }
                case "18" -> {
                    System.out.println("Какую подзадачу хотим удалить?");
                    String subtaskToDelete = scanner.nextLine();
                    subtaskList = Manager.deleteSubtask(Integer.parseInt(subtaskToDelete),subtaskList);
                }
                case "19" -> Manager.deleteAllSubtasks(subtaskList);
            }
        }
    }
}
