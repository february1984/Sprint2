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
    public static void showTask(Task task) {
        System.out.println(task.id + "," + task.status + "," + task.name + "," + task.overview + "," + task.taskType + "\n");
    }
    public static void showEpic(Epic epic, HashMap<Integer, Subtask> subtaskList) {
        System.out.println(epic.id + "," + epic.status + "," + epic.name + "," + epic.overview + "," + epic.taskType);
        for (Integer key : subtaskList.keySet()) {
            if (subtaskList.get(key).parentID == epic.id){
                System.out.println("\t" + subtaskList.get(key).id + "," + subtaskList.get(key).status + "," + subtaskList.get(key).name + "," + subtaskList.get(key).overview);
            }
        }
    }
    public static void showSubtask(Subtask subtask) {
        System.out.println(subtask.id + "," + subtask.status + "," + subtask.name + "," + subtask.overview + "," + subtask.taskType + "\n");
    }
    public static void deleteAllTasks (HashMap<Integer,Task> taskListToClear) throws IOException {
        FileWriter taskCleaner = new FileWriter("TaskList.txt");
        taskCleaner.write("");
        taskCleaner.close();
        taskListToClear.clear();
    }
    public static void deleteAllEpics (HashMap<Integer,Epic> epicListToClear, HashMap<Integer,Subtask> subtaskListToClear) throws IOException {
        FileWriter epicCleaner = new FileWriter("EpicList.txt");
        epicCleaner.write("");
        epicCleaner.close();
        epicListToClear.clear();
        FileWriter subtaskCleaner = new FileWriter("SubtaskList.txt");
        subtaskCleaner.write("");
        subtaskCleaner.close();
        subtaskListToClear.clear();
    }
    public static void deleteAllSubtasks (HashMap<Integer,Subtask> subtaskListToClear) throws IOException {
        FileWriter subtaskCleaner = new FileWriter("SubtaskList.txt");
        subtaskCleaner.write("");
        subtaskCleaner.close();
        subtaskListToClear.clear();
    }
    public static void printAllTasks(HashMap<Integer,Task> taskList){
        for (Integer key : taskList.keySet()) {
        Manager.showTask(taskList.get(key));
        }
    }
    public static void printAllEpics(HashMap<Integer,Epic> epicList, HashMap<Integer,Subtask> subtaskList){
        for (Integer key : epicList.keySet()) {
            Manager.showEpic(epicList.get(key),subtaskList);
        }
    }
    public static void printAllSubtasks(HashMap<Integer,Subtask> subtaskList){
        for (Integer key : subtaskList.keySet()) {
            Manager.showSubtask(subtaskList.get(key));
        }
    }
    public static HashMap<Integer, Task> deleteTask (Integer taskToDelete,
                                                     HashMap<Integer, Task> currentTaskList) throws IOException {
        HashMap<Integer, Task> taskListDeleteFrom = loadTaskFromFile();
        taskListDeleteFrom.remove(taskToDelete);
        Manager.deleteAllTasks(currentTaskList);
        for (Integer key : taskListDeleteFrom.keySet()) {
            Manager.saveTaskToFile(taskListDeleteFrom.get(key));
        }
        return taskListDeleteFrom;
    }
    public static void deleteEpic (Integer epicToDelete,
                                   HashMap<Integer, Epic> currentEpicList,
                                   HashMap<Integer, Subtask> currentSubtaskList,
                                   String subtaskIncluding) throws IOException {
        HashMap<Integer, Epic> epicListDeleteFrom = loadEpicFromFile();
        HashMap<Integer, Subtask> subtaskListDeleteFrom = loadSubtaskFromFile();
        ArrayList<Integer> subtasksIdToDelete = new ArrayList<>();
        if (subtaskIncluding.equals("withSubtasks")) {
            for (Integer key : subtaskListDeleteFrom.keySet()) {
                if (subtaskListDeleteFrom.get(key).parentID == epicToDelete) {
                    subtasksIdToDelete.add(key);
                }
            }
            for (Integer id : subtasksIdToDelete) {
                subtaskListDeleteFrom.remove(id);
            }
        }
        epicListDeleteFrom.remove(epicToDelete);
        Manager.deleteAllEpics(currentEpicList,currentSubtaskList);
        for (Integer key : epicListDeleteFrom.keySet()) {
            Manager.saveEpicToFile(epicListDeleteFrom.get(key));
        }
        for (Integer key : subtaskListDeleteFrom.keySet()) {
            Manager.saveSubtaskToFile(subtaskListDeleteFrom.get(key));
        }
    }
    public static HashMap<Integer, Subtask> deleteSubtask (Integer subtaskToDelete,
                                                     HashMap<Integer, Subtask> currentSubtaskList) throws IOException {
        HashMap<Integer, Subtask> subtaskListDeleteFrom = loadSubtaskFromFile();
        subtaskListDeleteFrom.remove(subtaskToDelete);
        Manager.deleteAllSubtasks(currentSubtaskList);
        for (Integer key : subtaskListDeleteFrom.keySet()) {
            Manager.saveSubtaskToFile(subtaskListDeleteFrom.get(key));
        }
        return subtaskListDeleteFrom;
    }
    public static HashMap<Integer,Task> updateTask (Integer taskToUpdateID,
                                                    HashMap<Integer, Task> currentTaskList) throws IOException{
        HashMap<Integer, Task> taskListToUpdate = loadTaskFromFile();
        Task taskToUpdate = taskListToUpdate.get(taskToUpdateID);
        System.out.println("Выполнили задачу? Y/N");
        String command = scanner.nextLine();
        if (command.equals("Y")){
            if (taskToUpdate.status.equals("NEW")) {
                taskToUpdate.status = "DONE";
                taskListToUpdate = deleteTask(taskToUpdateID, currentTaskList);
                taskListToUpdate.put(taskToUpdateID, taskToUpdate);
                Manager.saveTaskToFile(taskToUpdate);
            } else {
                System.out.println("Задача уже выполнена");
            }
        }
        System.out.println("Задача изменилась?");
        command = scanner.nextLine();
        if (command.equals("Y")) {
            System.out.println("Что делаем теперь?");
            taskToUpdate.overview = scanner.nextLine();
            taskListToUpdate = deleteTask(taskToUpdateID, currentTaskList);
            taskListToUpdate.put(taskToUpdateID, taskToUpdate);
            Manager.saveTaskToFile(taskToUpdate);
        }
        return taskListToUpdate;
    }
    public static HashMap<Integer,Epic> updateEpic (Integer epicToUpdateID,
                                                    HashMap<Integer, Epic> currentEpicList,
                                                    HashMap<Integer, Subtask> currentSubtaskList) throws IOException{
        HashMap<Integer, Epic> epicListToUpdate = loadEpicFromFile();
        Epic epicToUpdate = epicListToUpdate.get(epicToUpdateID);
        System.out.println("Что делаем теперь?");
        epicToUpdate.overview = scanner.nextLine();
        deleteEpic(epicToUpdateID, currentEpicList, currentSubtaskList, "withoutSubtasks");
        epicListToUpdate.put(epicToUpdateID, epicToUpdate);
        Manager.saveEpicToFile(epicToUpdate);
        return epicListToUpdate;
    }
    public static void updateSubtask (Integer subtaskToUpdateID,
                                                          HashMap<Integer, Subtask> currentSubtaskList) throws IOException{
        HashMap<Integer, Epic> epicListToUpdate = loadEpicFromFile();
        HashMap<Integer, Subtask> subtaskListToUpdate = loadSubtaskFromFile();
        Subtask subtaskToUpdate = subtaskListToUpdate.get(subtaskToUpdateID);
        Epic epicToUpdate = epicListToUpdate.get(subtaskToUpdate.parentID);
        int subtaskDoneCounter = 0;

        System.out.println("Выполнили подзадачу? Y/N");
        String command = scanner.nextLine();
        if (command.equals("Y")){
if (subtaskToUpdate.status.equals("NEW")) {
                subtaskToUpdate.status = "DONE";
                subtaskListToUpdate = deleteSubtask(subtaskToUpdateID, currentSubtaskList);
                subtaskListToUpdate.put(subtaskToUpdateID, subtaskToUpdate);
                saveSubtaskToFile(subtaskToUpdate);
                epicListToUpdate.get(subtaskToUpdate.parentID).status = "IN_PROGRESS";
                for (Integer key : subtaskListToUpdate.keySet()) {
                    if (subtaskListToUpdate.get(key).parentID == subtaskToUpdate.parentID) {
                        if (subtaskListToUpdate.get(key).status.equals("DONE")) {
                            subtaskDoneCounter++;
                        }
                        subtaskDoneCounter--;
                    }
                }
                if (subtaskDoneCounter == 0){
                    epicListToUpdate.get(subtaskToUpdate.parentID).status = "DONE";
                }
            deleteEpic(subtaskToUpdate.parentID, epicListToUpdate, subtaskListToUpdate, "withoutSubtasks");
            epicListToUpdate.put(epicToUpdate.id, epicToUpdate);
            Manager.saveEpicToFile(epicToUpdate);
            } else {
                System.out.println("Задача уже выполнена");
            }
        }
        System.out.println("Задача изменилась?");
        command = scanner.nextLine();
        if (command.equals("Y")) {
            System.out.println("Что делаем теперь?");
            subtaskToUpdate.overview = scanner.nextLine();
            subtaskListToUpdate = deleteSubtask(subtaskToUpdateID, currentSubtaskList);
            subtaskListToUpdate.put(subtaskToUpdateID, subtaskToUpdate);
            saveSubtaskToFile(subtaskToUpdate);
        }
    }
}
