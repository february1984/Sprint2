import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) throws IOException {
        HashMap<Integer, Task> taskList = Manager.loadTaskFromFile();
        HashMap<Integer, Epic> epicList = Manager.loadEpicFromFile();
        HashMap<Integer, Subtask> subtaskList = Manager.loadSubtaskFromFile();
        InMemoryTaskManager currManager = new InMemoryTaskManager();
        ArrayList<String> viewedTasksHistory = Manager.loadTaskHistoryFromFile();
        TreeNode root = new TreeNode(taskList.get(taskList.keySet().toArray()[0]));
        int currentID = Manager.loadIdFromFile();
        Scanner scanner = new Scanner(System.in);
        String command = "Start";
        while (!command.equals("E")){
            System.out.println("""
                    \t\t\t==============================================================================================================
                    \t\t\t|| Создать\t|| Показать\t|| Показать все\t|| Удалить\t|| Удалить все\t|| Обновить\t|| Показать когда закончим\t||
                    \t\t\t==============================================================================================================
                    Задачу\t\t|| код 1\t|| код 2\t|| код 3\t\t|| код 4\t|| код 5 \t\t||  код 6 \t|| Код 7\t\t\t\t\t||
                    \t\t\t==============================================================================================================
                    Эпик\t\t|| код 8\t|| код 9\t|| код 10\t\t|| код 11\t|| код 12\t\t||  код 13 \t|| Код 14\t\t\t\t\t||
                    \t\t\t==============================================================================================================
                    Подзадачу\t|| код 15\t|| код 16\t|| код 17\t\t|| код 18\t|| код 19\t\t||  код 20 \t|| Код 23\t\t\t\t\t||
                    \t\t\t==============================================================================================================
                    \t\t\t21 - Показать историю сессии 22 - Показать историю быстрее
                    \t\t\t23 - Быстрая сортировка задач 24 - Сохранить задачи в бинарное дерево
                    \t\t\t25 - Вывести бинарное дерево (центрированный обход) 26 - проверить пересечение задач
                    """);
            command = scanner.nextLine();
            switch (command) {
                case "1" -> {
                    System.out.println("Заводим новую задачу:\n");
                    System.out.println("Как назовем задачу?");
                    String name = scanner.nextLine();
                    System.out.println("Что будем делать?");
                    String overview = scanner.nextLine();
                    System.out.println("Когда начнем? (Введите дату в формате dd.mm.yyyy:hh24:mm)");
                    String startTime = scanner.nextLine();
                    System.out.println("Сколько в часах планируем делать задачу?");
                    int duration = Integer.parseInt(scanner.nextLine());
                    Task taskToAdd = currManager.createTask(currentID, name, overview, duration, startTime);
                    taskList.put(taskToAdd.id, taskToAdd);
                    currentID++;
                }
                case "2" -> {
                    System.out.println("Какую задачу хотим посмотреть?");
                    String taskID = scanner.nextLine();
                    try {
                        currManager.showTask(taskList.get(Integer.parseInt(taskID)));
                    } catch (Exception e) {
                        System.out.println("Такой задачи нет\n");
                    }
                }
                case "3" -> currManager.showAllTasks(taskList);
                case "4" -> {
                    System.out.println("Какую задачу хотим удалить?");
                    String taskToDelete = scanner.nextLine();
                    currManager.deleteTask(Integer.parseInt(taskToDelete), taskList);
                }
                case "5" -> currManager.deleteAllTasks(taskList);
                case "6" -> {
                    System.out.println("Какую задачу хотим обновить?");
                    String taskToUpdate = scanner.nextLine();
                    System.out.println("Выполнили задачу? Y/N");
                    String commandToComplete = scanner.nextLine();
                    System.out.println("Задача изменилась? Y/N");
                    String commandToUpdate = scanner.nextLine();
                    String newOverview = "";
                    if (commandToUpdate.equals("Y")) {
                        System.out.println("Что делаем теперь?");
                        newOverview = scanner.nextLine();
                    }
                    currManager.updateTask(Integer.parseInt(taskToUpdate), taskList, commandToComplete, commandToUpdate, newOverview);
                }
                case "7" -> {
                    System.out.println("Узнать время завершения какой задачи?");
                    String task = scanner.nextLine();
                    currManager.getEndTime(taskList.get(Integer.parseInt(task)));
                }
                case "8" -> {
                    System.out.println("Заводим новый эпик:\n");
                    System.out.println("Как назовем эпик?");
                    String name = scanner.nextLine();
                    System.out.println("Что будем делать?");
                    String overview = scanner.nextLine();
                    Epic newEpic = currManager.createEpic(name, overview, currentID);
                    epicList.put(newEpic.id, newEpic);
                    currentID++;
                }
                case "9" -> {
                    System.out.println("Какой эпик хотим посмотреть?");
                    String epicID = scanner.nextLine();
                    try {
                        currManager.showEpic(epicList.get(Integer.parseInt(epicID)), subtaskList);
                    } catch (Exception e) {
                        System.out.println("Такой задачи нет\n");
                    }
                }
                case "10" -> currManager.showAllEpics(epicList, subtaskList);

                case "11" -> {
                    System.out.println("Какой эпик хотим удалить?");
                    String epicToDelete = scanner.nextLine();
                    currManager.deleteEpic(Integer.parseInt(epicToDelete), epicList, subtaskList, "withSubtasks");
                }
                case "12" -> currManager.deleteAllEpics(epicList, subtaskList);
                case "13" -> {
                    System.out.println("Какой эпик хотим обновить?");
                    String epicToUpdate = scanner.nextLine();
                    while (!epicList.containsKey(Integer.parseInt(epicToUpdate))) {
                        System.out.println("Такого эпика нет. Список доступных эпиков: " + epicList.keySet());
                        System.out.println("Пожалуйста введите эпик снова");
                        epicToUpdate = scanner.nextLine();
                    }
                    System.out.println("Что делаем теперь?");
                    String newOverview = scanner.nextLine();
                    currManager.updateEpic(Integer.parseInt(epicToUpdate), epicList, newOverview);
                }
                case "15" -> {
                    System.out.println("Подзадача какого эпика?");
                    int parentID = Integer.parseInt(scanner.nextLine());
                    while (!epicList.containsKey(parentID)) {
                        System.out.println("Такого эпика нет. Список доступных эпиков: " + epicList.keySet());
                        System.out.println("Пожалуйста введите эпик снова");
                        parentID = Integer.parseInt(scanner.nextLine());
                    }
                    System.out.println("Заводим новую подзадачу:\n");
                    System.out.println("Как назовем подзадачу?");
                    String name = scanner.nextLine();
                    System.out.println("Что будем делать?");
                    String overview = scanner.nextLine();
                    Subtask newSubtask = currManager.createSubtask(currentID, parentID, epicList, name, overview);
                    subtaskList.put(newSubtask.id, newSubtask);
                    currentID++;
                }
                case "16" -> {
                    System.out.println("Какую подзадачу хотим посмотреть?");
                    String subtaskID = scanner.nextLine();
                    try {
                        currManager.showSubtask(subtaskList.get(Integer.parseInt(subtaskID)));
                    } catch (Exception e) {
                        System.out.println("Такой подзадачи нет\n");
                    }
                }
                case "17" -> currManager.showAllSubtasks(subtaskList);
                case "18" -> {
                    System.out.println("Какую подзадачу хотим удалить?");
                    String subtaskToDelete = scanner.nextLine();
                    currManager.deleteSubtask(Integer.parseInt(subtaskToDelete), subtaskList, epicList);
                }
                case "19" -> currManager.deleteAllSubtasks(subtaskList);
                case "20" -> {
                    String newOverview = "Overview";
                    System.out.println("Какую подзадачу хотим обновить?");
                    String subtaskToUpdate = scanner.nextLine();
                    System.out.println("Выполнили подзадачу? Y (Да)/ P (В процессе)/ N (Нет)");
                    String completeCommand = scanner.nextLine();
                    System.out.println("Задача изменилась?");
                    String changeCommand = scanner.nextLine();
                    if (changeCommand.equals("Y")) {
                        System.out.println("Что делаем теперь?");
                        newOverview = scanner.nextLine();
                    }
                    currManager.updateSubtask(Integer.parseInt(subtaskToUpdate), subtaskList, epicList,
                            completeCommand, changeCommand, newOverview);
                }
                case "21" -> currManager.showTaskViewsHistory();
                case "22" -> {
                    viewedTasksHistory = currManager.getHistory(viewedTasksHistory);
                    for (String task : viewedTasksHistory) {
                        System.out.println(task);
                    }
                }
                case "23" ->{
                    Task[] tasks = taskList.values().toArray(new Task[0]);
                    currManager.sortTaskList(tasks, 0, taskList.size() - 1);
                    for (Task task: tasks){
                        System.out.println("( " + task.name + " " + task.overview + " " + task.startTime + " )");
                    }
                }
                case "24" ->{
                    for (Task task : taskList.values()){
                        root.addRecursive(root, task);
                    }
                }
                case "25" -> root.showTheTreeRecursive(root);
                case "26" -> {
                    int errCounter = 0;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy:HH:mm");
                    Task[] tasks = taskList.values().toArray(new Task[0]);
                    currManager.sortTaskList(tasks, 0, taskList.size() - 1);
                    for (int i = 0; i < tasks.length-1; i++){
                        if (java.time.LocalDateTime.parse(tasks[i].startTime, formatter).plusHours(tasks[i].duration).isAfter(
                                java.time.LocalDateTime.parse(tasks[i+1].startTime, formatter))) {
                            System.out.println("Ошибка времени выполнения задачи " + tasks[i].name);
                            errCounter++;
                        }
                    }
                    System.out.println("Количество ошибок  = " + errCounter);
                }

            }
        }
        Manager.saveTaskListToFile(taskList);
        Manager.saveEpicListToFile(epicList);
        Manager.saveSubtaskListToFile(subtaskList);
        Manager.saveIdToFile(currentID);
        Manager.saveTaskHistoryToFile(currManager.getHistory(viewedTasksHistory));
    }
}