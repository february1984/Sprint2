import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;


class InMemoryTaskManagerTest {
    InMemoryTaskManager currManager = new InMemoryTaskManager();
    HashMap<Integer, Task> testTaskList = new HashMap<>();
    HashMap<Integer, Epic> testEpicList = new HashMap<>();
    HashMap<Integer, Subtask> testSubtaskList = new HashMap<>();


    @Test
    void checkTaskCreation (){
        Task referenceTask = new Task();
        referenceTask.taskType = "TASK";
        referenceTask.overview = "Test overview";
        referenceTask.name = "Test name";
        referenceTask.status = "NEW";
        referenceTask.id = -1;
        Task taskToAdd = currManager.createTask(-1,"Test name", "Test overview");
        Assertions.assertEquals(referenceTask, taskToAdd, "Задача создается некорректно");
    }
    @Test
    void checkTaskDeletionStandard () {
        Task taskToAdd = currManager.createTask(-2, "Test name", "Test overview");
        testTaskList.put(taskToAdd.id, taskToAdd);
        currManager.deleteTask(taskToAdd.id,testTaskList);
        Assertions.assertTrue(testTaskList.isEmpty());
    }
    @Test
    void checkTaskDeletionEmpty () {
        currManager.deleteTask(-3,testTaskList);
        Assertions.assertTrue(testTaskList.isEmpty());
    }
    @Test
    void checkTaskDeletionIncorrect () {
        Task taskToAdd = currManager.createTask(-4, "Test name", "Test overview");
        testTaskList.put(taskToAdd.id, taskToAdd);
        currManager.deleteTask(-5,testTaskList);
        Assertions.assertTrue(testTaskList.containsKey(-4));
    }
    @Test
    void checkTaskUpdateStandard () {
        Task taskToAdd = currManager.createTask(-5, "Test name", "Test overview");
        testTaskList.put(taskToAdd.id, taskToAdd);
        currManager.updateTask(-5,testTaskList,"Y","Y","New overview");
        Assertions.assertTrue(taskToAdd.status.equals("DONE") && taskToAdd.overview.equals("New overview"));
    }
    @Test
    void checkTaskUpdateIncorrect () {
        Task taskToAdd = currManager.createTask(-6, "Test name", "Test overview");
        testTaskList.put(taskToAdd.id, taskToAdd);
        currManager.updateTask(-7,testTaskList,"Y","Y","New overview");
        Assertions.assertTrue(taskToAdd.status.equals("NEW") && taskToAdd.overview.equals("Test overview"));
    }
    @Test
    void checkEpicCreation (){
        Epic referenceEpic = new Epic();
        referenceEpic.taskType = "EPIC";
        referenceEpic.overview = "Epic overview";
        referenceEpic.name = "Epic name";
        referenceEpic.status = "NEW";
        referenceEpic.id = -8;
        Epic epicToAdd = currManager.createEpic("Epic name", "Epic overview", -8);
        Assertions.assertEquals(referenceEpic, epicToAdd, "Эпик создается некорректно");
    }
    @Test
    void checkEpicDeletionStandard () {
        Epic epicToAdd = currManager.createEpic("Test name", "Test overview", -9);
        testEpicList.put(epicToAdd.id, epicToAdd);
        currManager.deleteEpic(epicToAdd.id,testEpicList,testSubtaskList,"withSubtasks");
        Assertions.assertTrue(testEpicList.isEmpty());
    }
    @Test
    void checkEpicDeletionEmpty () {
        currManager.deleteEpic(-10,testEpicList,testSubtaskList,"withSubtasks");
        Assertions.assertTrue(testEpicList.isEmpty());
    }
    @Test
    void checkEpicDeletionIncorrect () {
        Epic epicToAdd = currManager.createEpic("Test name", "Test overview", -11);
        testEpicList.put(epicToAdd.id, epicToAdd);
        currManager.deleteEpic(-12,testEpicList,testSubtaskList,"withSubtasks");
        Assertions.assertTrue(testEpicList.containsKey(-11));
    }
}