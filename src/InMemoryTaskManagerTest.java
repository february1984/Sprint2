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
}