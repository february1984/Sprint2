import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) throws IOException {
        HashMap<Integer, Task> taskList = Manager.loadTaskFromFile();
        HashMap<Integer, Epic> epicList = Manager.loadEpicFromFile();
        HashMap<Integer, Subtask> subtaskList = Manager.loadSubtaskFromFile();
        int currentID = Manager.loadIdFromFile();
        Scanner scanner = new Scanner(System.in);
        String command = "Start";

        while (!command.equals("E")){
            System.out.println("""
                    \t\t\t==================================================================================
                    \t\t\t|| Создать\t|| Показать\t|| Показать все\t|| Удалить\t|| Удалить все\t|| Обновить\t||
                    \t\t\t==================================================================================
                    Задачу\t\t|| код 1\t|| код 2\t|| код 3\t\t|| код 4\t|| код 5 \t\t||  код 6 \t||
                    \t\t\t==================================================================================
                    Эпик\t\t|| код 8\t|| код 9\t|| код 10\t\t|| код 11\t|| код 12\t\t||  код 13 \t||
                    \t\t\t==================================================================================
                    Подзадачу\t|| код 15\t|| код 16\t|| код 17\t\t|| код 18\t|| код 19\t\t||  код 20 \t||
                    \t\t\t==================================================================================
                    """);
            command = scanner.nextLine();
            switch (command) {
                case "1" -> {
                    Task taskToAdd = Manager.createTask(currentID);
                    taskList.put(taskToAdd.id, taskToAdd);
                    currentID++;
                }
                case "2" -> {
                    System.out.println("Какую задачу хотим посмотреть?");
                    String taskID = scanner.nextLine();
                    try {
                        Manager.showTask(taskList.get(Integer.parseInt(taskID)));
                    } catch (Exception e) {
                        System.out.println("Такой задачи нет\n");
                    }
                }
                case "3" -> Manager.showAllTasks(taskList);
                case "4" -> {
                    System.out.println("Какую задачу хотим удалить?");
                    String taskToDelete = scanner.nextLine();
                    Manager.deleteTask(Integer.parseInt(taskToDelete), taskList);
                }
                case "5" -> Manager.deleteAllTasks(taskList);
                case "6" -> {
                    System.out.println("Какую задачу хотим обновить?");
                    String taskToUpdate = scanner.nextLine();
                    Manager.updateTask(Integer.parseInt(taskToUpdate), taskList);
                }
                case "8" -> {
                    Epic newEpic = Manager.createEpic(currentID);
                    epicList.put(newEpic.id, newEpic);
                    currentID++;
                }
                case "9" -> {
                    System.out.println("Какой эпик хотим посмотреть?");
                    String epicID = scanner.nextLine();
                    try {
                        Manager.showEpic(epicList.get(Integer.parseInt(epicID)), subtaskList);
                    } catch (Exception e) {
                        System.out.println("Такой задачи нет\n");
                    }
                }
                case "10" -> Manager.showAllEpics(epicList,subtaskList);

                case "11" -> {
                    System.out.println("Какой эпик хотим удалить?");
                    String epicToDelete = scanner.nextLine();
                    Manager.deleteEpic(Integer.parseInt(epicToDelete), epicList, subtaskList, "withSubtasks");
                }
                case "12" -> Manager.deleteAllEpics(epicList,subtaskList);
                case "13" -> {
                    System.out.println("Какой эпик хотим обновить?");
                    String epicToUpdate = scanner.nextLine();
                    Manager.updateEpic(Integer.parseInt(epicToUpdate), epicList);
                }
                case "15" -> {
                    Subtask newSubtask = Manager.createSubtask(currentID, epicList);
                    subtaskList.put(newSubtask.id, newSubtask);
                    currentID++;
                }
                case "16" -> {
                    System.out.println("Какую подзадачу хотим посмотреть?");
                    String subtaskID = scanner.nextLine();
                    try {
                        Manager.showSubtask(subtaskList.get(Integer.parseInt(subtaskID)));
                    } catch (Exception e) {
                        System.out.println("Такой подзадачи нет\n");
                    }
                }
                case "17" -> Manager.showAllSubtasks(subtaskList);
                case "18" -> {
                    System.out.println("Какую подзадачу хотим удалить?");
                    String subtaskToDelete = scanner.nextLine();
                    Manager.deleteSubtask(Integer.parseInt(subtaskToDelete),subtaskList,epicList);
                }
                case "19" -> Manager.deleteAllSubtasks(subtaskList);
                case "20" -> {
                    System.out.println("Какую подзадачу хотим обновить?");
                    String subtaskToUpdate = scanner.nextLine();
                    Manager.updateSubtask(Integer.parseInt(subtaskToUpdate), subtaskList, epicList);
                }
            }
        }
        Manager.saveTaskListToFile(taskList);
        Manager.saveEpicListToFile(epicList);
        Manager.saveSubtaskListToFile(subtaskList);
        Manager.saveIdToFile(currentID);
    }
}