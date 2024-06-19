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
    public Task createTask(int maxId) {
        Task taskToSet = new Task();
        System.out.println("Заводим новую задачу:\n");
        taskToSet.id = maxId;
        System.out.println("Как назовем задачу?");
        taskToSet.name = scanner.nextLine();
        System.out.println("Что будем делать?");
        taskToSet.overview = scanner.nextLine();
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
        remove(currentTaskList.get(taskToDelete));
        currentTaskList.remove(taskToDelete);
    }
    @Override
    public void deleteAllTasks (HashMap<Integer,Task> taskListToClear) {
        taskListToClear.clear();
    }
    @Override
    public void updateTask (Integer taskToUpdateID, HashMap<Integer, Task> currentTaskList) {
        System.out.println("Выполнили задачу? Y/N");
        String command = scanner.nextLine();
        if (command.equals("Y")){
            if (currentTaskList.get(taskToUpdateID).status.equals("NEW")) {
                currentTaskList.get(taskToUpdateID).status = "DONE";
            } else {
                System.out.println("Задача уже выполнена");
            }
        }
        System.out.println("Задача изменилась?");
        command = scanner.nextLine();
        if (command.equals("Y")) {
            System.out.println("Что делаем теперь?");
            currentTaskList.get(taskToUpdateID).overview = scanner.nextLine();
        }
    }
    @Override
    public Epic createEpic (int maxId) {
        Epic epicToSet = new Epic();
        System.out.println("Заводим новый эпик:\n");
        epicToSet.id = maxId;
        System.out.println("Как назовем эпик?");
        epicToSet.name = scanner.nextLine();
        System.out.println("Что будем делать?");
        epicToSet.overview = scanner.nextLine();
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
    public Subtask createSubtask (int maxId, HashMap<Integer,Epic> epicList){
        Subtask subtaskToSet = new Subtask();
        System.out.println("Подзадача какого эпика?");
        subtaskToSet.parentID = Integer.parseInt(scanner.nextLine());
        while (!epicList.containsKey(subtaskToSet.parentID)){
            System.out.println("Такого эпика нет. Список доступных эпиков: " + epicList.keySet());
            System.out.println("Пожалуйста введите эпик снова");
            subtaskToSet.parentID = Integer.parseInt(scanner.nextLine());
        }
        System.out.println("Заводим новую подзадачу:\n");
        subtaskToSet.id = maxId;
        System.out.println("Как назовем подзадачу?");
        subtaskToSet.name = scanner.nextLine();
        System.out.println("Что будем делать?");
        subtaskToSet.overview = scanner.nextLine();
        subtaskToSet.status = "NEW";
        subtaskToSet.taskType = taskType.SUBTASK.name();
        if (epicList.get(subtaskToSet.parentID).status.equals("DONE")){
            epicList.get(subtaskToSet.parentID).status = "IN_PROGRESS";
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
                               HashMap <Integer, Epic> currentEpicList) {
        int subtaskDoneCounter = 0;

        System.out.println("Выполнили подзадачу? Y/N");
        String command = scanner.nextLine();
        if (command.equals("Y")){
            if (currentSubtaskList.get(subtaskToUpdateID).status.equals("NEW")) {
                currentSubtaskList.get(subtaskToUpdateID).status = "DONE";
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
        System.out.println("Задача изменилась?");
        command = scanner.nextLine();
        if (command.equals("Y")) {
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
        taskList.get(task.id).previousNode.nextNode = taskList.get(task.id).nextNode;
        taskList.get(task.id).nextNode.previousNode = taskList.get(task.id).previousNode;
    }
    @Override
    public ArrayList<String> getHistory (ArrayList<String> currHistory) {
        return getTasks(currHistory);
    }
}
