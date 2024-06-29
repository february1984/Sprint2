import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements Manager, HistoryManager {
    ArrayList<String> history = new ArrayList<>();
    HashMap<Integer, HistoryNode> taskList= new HashMap<>();
    ArrayList<HistoryNode> linkedHistory = new ArrayList<>();
    public HistoryNode head = new HistoryNode();
    public HistoryNode tail = new HistoryNode();
    Integer historyLength = 0;


    public ArrayList<HistoryNode> linkLast (ArrayList<HistoryNode> currHistory, Task taskToAdd){
        HistoryNode node;
        node = new HistoryNode();
        if(currHistory.isEmpty()){
            node.task = taskToAdd;
            node.previousNode = head;
            node.nextNode = tail;
            head.nextNode = node;
            tail.previousNode = node;
            node.nodeID = taskToAdd.id;
            currHistory.add(node);
            taskList.put(taskToAdd.id,node);
            historyLength++;
        } else {
            node.task = taskToAdd;
            node.previousNode = tail.previousNode;
            node.nextNode = tail;
            tail.previousNode.nextNode = node;
            tail.previousNode = node;
            node.nodeID = taskToAdd.id;
            currHistory.add(node);
            taskList.put(taskToAdd.id,node);
            historyLength++;
            if (historyLength > 10){
                remove(head.nextNode.task);
                historyLength--;
            }
        }
        return currHistory;
    }
    public ArrayList<String> getTasks (ArrayList<String> tasksToGet){
        HistoryNode currNode = head.nextNode;
        if (currNode != null) {
            while (currNode != tail) {
                if (tasksToGet.size() < 10 && !tasksToGet.contains("(" + currNode.task.taskType + " " + currNode.task.name + ")")) {
                    tasksToGet.add("(" + currNode.task.taskType + " " + currNode.task.name + ")");
                } else if (!tasksToGet.contains("(" + currNode.task.taskType + " " + currNode.task.name + ")")) {
                    tasksToGet.removeFirst();
                    tasksToGet.add("(" + currNode.task.taskType + " " + currNode.task.name + ")");
                }
                currNode = currNode.nextNode;
            }
        }
        return tasksToGet;
    }
    @Override
    public void showTaskViewsHistory() {
        for (String currViewInHistory: history){
            System.out.println(currViewInHistory + " ");
        }
    }
    @Override
    public Task createTask(int maxId, String name, String overview) {
        Task taskToSet = new Task();
        taskToSet.id = maxId;
        taskToSet.name = name;
        taskToSet.overview = overview;
        taskToSet.status = "NEW";
        taskToSet.taskType = taskType.TASK.name();
        return taskToSet;
    }
    @Override
    public void showTask(Task task) {
        System.out.println(task.id + "," + task.status + "," + task.name + "," + task.overview + "," + task.taskType + "\n");
        if (history.size() < 10 ) {
            history.add("(" + task.taskType + " " + task.name + ")");
        } else {
            history.add("(" + task.taskType + " " + task.name + ")");
            history.removeFirst();
        }
        if (taskList.containsKey(task.id)) {
            remove(task);
            add(task);
        } else add(task);
    }
    @Override
    public void showAllTasks (HashMap<Integer,Task> taskList){
        for (Integer key : taskList.keySet()) {
            showTask(taskList.get(key));
        }
    }
    @Override
    public void deleteTask (Integer taskToDelete, HashMap<Integer, Task> currentTaskList) {
        if (currentTaskList.containsKey(taskToDelete)) {
            remove(currentTaskList.get(taskToDelete));
            currentTaskList.remove(taskToDelete);
        } else System.out.println("Такой задачи не существует");
    }
    @Override
    public void deleteAllTasks (HashMap<Integer,Task> taskListToClear) {
        taskListToClear.clear();
    }
    @Override
    public void updateTask (Integer taskToUpdateID, HashMap<Integer, Task> currentTaskList,
                            String completeCommand, String updateCommand, String newOverview) {
        if (completeCommand.equals("Y") && currentTaskList.get(taskToUpdateID) != null){
            if (currentTaskList.get(taskToUpdateID).status.equals("NEW")) {
                currentTaskList.get(taskToUpdateID).status = "DONE";
            } else {
                System.out.println("Задача уже выполнена");
            }
        }
        if (updateCommand.equals("Y") && currentTaskList.get(taskToUpdateID) != null) {
            currentTaskList.get(taskToUpdateID).overview = newOverview;
        }
    }
    @Override
    public Epic createEpic (String name, String overview, int maxId) {
        Epic epicToSet = new Epic();
        epicToSet.id = maxId;
        epicToSet.name = name;
        epicToSet.overview = overview;
        epicToSet.status = "NEW";
        epicToSet.taskType = taskType.EPIC.name();
        return epicToSet;
    }
    @Override
    public void showEpic(Epic epic, HashMap<Integer, Subtask> subtaskList) {
        System.out.println(epic.id + "," + epic.status + "," + epic.name + "," + epic.overview + "," + epic.taskType);
        if (taskList.containsKey(epic.id)) {
            remove(epic);
            add(epic);
        } else add(epic);
        for (Integer key : subtaskList.keySet()) {
            if (subtaskList.get(key).parentID == epic.id){
                System.out.println("\t" + subtaskList.get(key).id + "," + subtaskList.get(key).status + "," + subtaskList.get(key).name + "," + subtaskList.get(key).overview);
                if (taskList.containsKey(subtaskList.get(key).id)) {
                    remove(subtaskList.get(key));
                    add(subtaskList.get(key));
                } else add(subtaskList.get(key));
            }
        }
        if (history.size() < 10 ) {
            history.add("(" + epic.taskType + " " + epic.name + ")");
        } else {
            history.add("(" + epic.taskType + " " + epic.name + ")");
            history.removeFirst();
        }
    }
    @Override
    public void showAllEpics(HashMap<Integer,Epic> epicList, HashMap<Integer,Subtask> subtaskList){
        for (Integer key : epicList.keySet()) {
            showEpic(epicList.get(key),subtaskList);
        }
    }
    @Override
    public void deleteEpic (Integer epicToDelete, HashMap <Integer, Epic> epicList,
                            HashMap<Integer, Subtask> subtaskList, String subtaskIncluding) {
        ArrayList<Integer> subtasksIdToDelete = new ArrayList<>();
        remove(epicList.get(epicToDelete));
        if (subtaskIncluding.equals("withSubtasks")) {
            for (Integer key : subtaskList.keySet()) {
                if (subtaskList.get(key).parentID == epicToDelete) {
                    subtasksIdToDelete.add(key);
                }
            }
            for (Integer id : subtasksIdToDelete) {
                remove(subtaskList.get(id));
                subtaskList.remove(id);
            }
        }
    }
    @Override
    public void deleteAllEpics (HashMap<Integer,Epic> epicListToClear, HashMap<Integer,Subtask> subtaskListToClear) {
        epicListToClear.clear();
        subtaskListToClear.clear();
    }
    @Override
    public void updateEpic (Integer epicToUpdateID, HashMap<Integer, Epic> epicList){
        while (!epicList.containsKey(epicToUpdateID)){
            System.out.println("Такого эпика нет. Список доступных эпиков: " + epicList.keySet());
            System.out.println("Пожалуйста введите эпик снова");
            epicToUpdateID = Integer.parseInt(scanner.nextLine());
        }
        System.out.println("Что делаем теперь?");
        epicList.get(epicToUpdateID).overview = scanner.nextLine();
    }
    @Override
    public Subtask createSubtask (int maxId, int parentID, HashMap<Integer,Epic> epicList, String name, String overview){
        Subtask subtaskToSet = new Subtask();
        subtaskToSet.id = maxId;
        subtaskToSet.name = name;
        subtaskToSet.overview = overview;
        subtaskToSet.status = "NEW";
        subtaskToSet.taskType = taskType.SUBTASK.name();
        subtaskToSet.parentID = parentID;
        if (epicList.get(parentID).status.equals("DONE")){
            epicList.get(parentID).status = "IN_PROGRESS";
        }
        return subtaskToSet;
    }
    @Override
    public void showSubtask(Subtask subtask) {
        System.out.println(subtask.id + "," + subtask.status + "," + subtask.name + "," + subtask.overview + "," + subtask.taskType + "\n");
        if (history.size() < 10 ) {
            history.add("(" + subtask.taskType + " " + subtask.name + ")");
        } else {
            history.add("(" + subtask.taskType + " " + subtask.name + ")");
            history.removeFirst();
        }
        if (taskList.containsKey(subtask.id)) {
            remove(subtask);
            add(subtask);
        } else add(subtask);
    }
    @Override
    public void showAllSubtasks(HashMap<Integer,Subtask> subtaskList){
        for (Integer key : subtaskList.keySet()) {
            showSubtask(subtaskList.get(key));
        }
    }
    @Override
    public void deleteSubtask (Integer subtaskToDelete,
                               HashMap <Integer, Subtask> currentSubtaskList,
                               HashMap <Integer, Epic> currentEpicList) {
        int subtaskDoneCounter = 0;
        int numberOfSubtasks = 0;
        remove(currentSubtaskList.get(subtaskToDelete));
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
    @Override
    public void deleteAllSubtasks (HashMap<Integer,Subtask> subtaskListToClear) {
        subtaskListToClear.clear();
    }
    @Override
    public void updateSubtask (Integer subtaskToUpdateID,
                               HashMap<Integer, Subtask> currentSubtaskList,
                               HashMap <Integer, Epic> currentEpicList,
                               String completeCommand,
                               String changeCommand) {
        int subtaskDoneCounter = 0;

        if (completeCommand.equals("Y") || completeCommand.equals("P")){
            if (currentSubtaskList.get(subtaskToUpdateID).status.equals("NEW") ||
                    currentSubtaskList.get(subtaskToUpdateID).status.equals("IN_PROGRESS")) {
                if (completeCommand.equals("Y")){
                    currentSubtaskList.get(subtaskToUpdateID).status = "DONE";
                } else currentSubtaskList.get(subtaskToUpdateID).status = "IN_PROGRESS";
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
        if (changeCommand.equals("Y")) {
            System.out.println("Что делаем теперь?");
            currentSubtaskList.get(subtaskToUpdateID).overview = scanner.nextLine();
        }
    }
    @Override
    public void add(Task task) {
        linkedHistory = linkLast(linkedHistory, task);
    }
    @Override
    public void remove(Task task) {
        if (taskList.get(task.id) != null) {
            taskList.get(task.id).previousNode.nextNode = taskList.get(task.id).nextNode;
            taskList.get(task.id).nextNode.previousNode = taskList.get(task.id).previousNode;
        }
    }
    @Override
    public ArrayList<String> getHistory (ArrayList<String> currHistory) {
        return getTasks(currHistory);
    }
}
