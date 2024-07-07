import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
    void checkAllTaskDeletionStandard () {
        Task taskToAdd = currManager.createTask(-2, "Test name", "Test overview");
        testTaskList.put(taskToAdd.id, taskToAdd);
        currManager.deleteAllTasks(testTaskList);
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
    @Test
    void checkAllEpicsDeletionStandard () {
        Epic epicToAdd = currManager.createEpic("Test name", "Test overview", -20);
        testEpicList.put(epicToAdd.id, epicToAdd);
        Subtask subtaskToAdd = currManager.createSubtask(-21,-20, testEpicList, "Test name", "Test overview");
        testSubtaskList.put(subtaskToAdd.id, subtaskToAdd);
        currManager.deleteAllEpics(testEpicList,testSubtaskList);
        Assertions.assertTrue(testEpicList.isEmpty());
    }
    @Test
    void checkEpicUpdateStandard (){
        Epic epicToAdd = currManager.createEpic("Test name", "Test overview", -13);
        testEpicList.put(epicToAdd.id, epicToAdd);
        currManager.updateEpic(-13, testEpicList, "New overview");
        Assertions.assertEquals("New overview", epicToAdd.overview);
    }
    @Test
    void checkEpicUpdateIncorrect (){
        Epic epicToAdd = currManager.createEpic("Test name", "Test overview", -14);
        testEpicList.put(epicToAdd.id, epicToAdd);
        currManager.updateEpic(-15, testEpicList, "New overview");
        Assertions.assertEquals("Test overview", epicToAdd.overview);
    }
    @Test
    void checkSubtaskCreationStandard (){
        Epic referenceEpic = new Epic();
        referenceEpic.taskType = "EPIC";
        referenceEpic.overview = "Epic overview";
        referenceEpic.name = "Epic name";
        referenceEpic.status = "NEW";
        referenceEpic.id = -16;
        testEpicList.put(referenceEpic.id, referenceEpic);
        Subtask referenceSubtask = new Subtask();
        referenceSubtask.taskType = "SUBTASK";
        referenceSubtask.overview = "Test overview";
        referenceSubtask.name = "Test name";
        referenceSubtask.status = "NEW";
        referenceSubtask.id = -17;
        referenceSubtask.parentID = -16;
        Task subtaskToAdd = currManager.createSubtask(-17,-16, testEpicList, "Test name", "Test overview");
        Assertions.assertEquals(referenceSubtask, subtaskToAdd, "Задача создается некорректно");
    }
    @Test
    void checkSubtaskCreationIncorrect (){
        Epic referenceEpic = new Epic();
        referenceEpic.taskType = "EPIC";
        referenceEpic.overview = "Epic overview";
        referenceEpic.name = "Epic name";
        referenceEpic.status = "NEW";
        referenceEpic.id = -18;
        testEpicList.put(referenceEpic.id, referenceEpic);
        Subtask subtaskToAdd = currManager.createSubtask(-17,-19, testEpicList, "Test name", "Test overview");
        Assertions.assertNull(subtaskToAdd.name);
    }
    @Test
    void checkSubtaskDeletionStandard () {
        Epic epicToAdd = currManager.createEpic("Test name", "Test overview", -20);
        testEpicList.put(epicToAdd.id, epicToAdd);
        Subtask subtaskToAdd = currManager.createSubtask(-21,-20, testEpicList, "Test name", "Test overview");
        testSubtaskList.put(subtaskToAdd.id, subtaskToAdd);
        currManager.deleteSubtask(-21, testSubtaskList, testEpicList);
        Assertions.assertTrue(testSubtaskList.isEmpty());
    }
    @Test
    void checkSubtaskDeletionByEpicDeletionStandard () {
        Epic epicToAdd = currManager.createEpic("Test name", "Test overview", -22);
        testEpicList.put(epicToAdd.id, epicToAdd);
        Subtask subtaskToAdd = currManager.createSubtask(-23,-22, testEpicList, "Test name", "Test overview");
        testSubtaskList.put(subtaskToAdd.id, subtaskToAdd);
        currManager.deleteEpic(-22, testEpicList, testSubtaskList, "withSubtasks");
        Assertions.assertTrue(testSubtaskList.isEmpty());
    }
    @Test
    void checkSubtaskDeletionEmpty () {
        currManager.deleteSubtask(-24, testSubtaskList, testEpicList);
        Assertions.assertTrue(testSubtaskList.isEmpty());
    }
    @Test
    void checkSubtaskDeletionIncorrect () {
        Epic epicToAdd = currManager.createEpic("Test name", "Test overview", -25);
        testEpicList.put(epicToAdd.id, epicToAdd);
        Subtask subtaskToAdd = currManager.createSubtask(-26,-25, testEpicList, "Test name", "Test overview");
        testSubtaskList.put(subtaskToAdd.id, subtaskToAdd);
        currManager.deleteSubtask(-27, testSubtaskList, testEpicList);
        Assertions.assertTrue(testSubtaskList.containsKey(-26));
    }
    @Test
    void checkAllSubtasksDeletionStandard () {
        Epic epicToAdd = currManager.createEpic("Test name", "Test overview", -20);
        testEpicList.put(epicToAdd.id, epicToAdd);
        Subtask subtaskToAdd = currManager.createSubtask(-21,-20, testEpicList, "Test name", "Test overview");
        testSubtaskList.put(subtaskToAdd.id, subtaskToAdd);
        currManager.deleteAllSubtasks(testSubtaskList);
        Assertions.assertTrue(testSubtaskList.isEmpty());
    }
    @Test
    void checkSubtaskUpdateStandard(){
        Epic referenceEpic = currManager.createEpic("Epic name", "Epic overview", -22);
        testEpicList.put(referenceEpic.id, referenceEpic);
        Subtask subtaskToAdd = currManager.createSubtask(-23,-22, testEpicList, "Test name", "Test overview");
        testSubtaskList.put(subtaskToAdd.id, subtaskToAdd);
        currManager.updateSubtask(-23,testSubtaskList,testEpicList,"Y","Y","NewOverview");
        Assertions.assertTrue(subtaskToAdd.status.equals("DONE") && subtaskToAdd.overview.equals("NewOverview"));
    }
    @Test
    void checkSubtaskUpdateIncorrect(){
        Epic referenceEpic = currManager.createEpic("Epic name", "Epic overview", -24);
        testEpicList.put(referenceEpic.id, referenceEpic);
        Subtask subtaskToAdd = currManager.createSubtask(-25,-24, testEpicList, "Test name", "Test overview");
        testSubtaskList.put(subtaskToAdd.id, subtaskToAdd);
        currManager.updateSubtask(-26,testSubtaskList,testEpicList,"Y","Y","NewOverview");
        Assertions.assertTrue(subtaskToAdd.status.equals("NEW") && subtaskToAdd.overview.equals("Test overview"));
    }
    @Test
    void checkShowHistoryStandard(){
        ArrayList<String> viewedTasksHistory = new ArrayList<>();
        Task firstTestTask = currManager.createTask(-27,"FirstName", "Test overview");
        Task secTestTask = currManager.createTask(-28,"SecName", "Test overview");
        currManager.showTask(firstTestTask);
        currManager.showTask(secTestTask);
        viewedTasksHistory = currManager.getHistory(viewedTasksHistory);
        Assertions.assertTrue(viewedTasksHistory.contains("(TASK FirstName)") && viewedTasksHistory.contains("(TASK SecName)"));
    }
    @Test
    void checkShowHistoryEmpty(){
        ArrayList<String> viewedTasksHistory = new ArrayList<>();
        viewedTasksHistory = currManager.getHistory(viewedTasksHistory);
        Assertions.assertTrue(viewedTasksHistory.isEmpty());
    }
    @Test
    void checkShowHistoryDuplication(){
        ArrayList<String> viewedTasksHistory = new ArrayList<>();
        Task firstTestTask = currManager.createTask(-27,"FirstName", "Test overview");
        Task secTestTask = currManager.createTask(-28,"SecName", "Test overview");
        currManager.showTask(firstTestTask);
        currManager.showTask(secTestTask);
        currManager.showTask(firstTestTask);
        viewedTasksHistory = currManager.getHistory(viewedTasksHistory);
        Assertions.assertEquals(2, viewedTasksHistory.size());
    }
    @Test
    void checkShowHistoryWithDeletionFirst(){
        ArrayList<String> viewedTasksHistory = new ArrayList<>();
        Task firstTestTask = currManager.createTask(-27,"FirstName", "Test overview");
        Task secTestTask = currManager.createTask(-28,"SecName", "Test overview");
        Task thirdTestTask = currManager.createTask(-29,"ThirdName", "Test overview");
        testTaskList.put(firstTestTask.id, firstTestTask);
        testTaskList.put(secTestTask.id, secTestTask);
        testTaskList.put(thirdTestTask.id, thirdTestTask);
        currManager.showTask(firstTestTask);
        currManager.showTask(secTestTask);
        currManager.showTask(thirdTestTask);
        currManager.deleteTask(-27,testTaskList);
        viewedTasksHistory = currManager.getHistory(viewedTasksHistory);
        Assertions.assertTrue(!viewedTasksHistory.contains("(TASK FirstName)") &&
                viewedTasksHistory.contains("(TASK SecName)") &&
                viewedTasksHistory.contains("(TASK ThirdName)") &&
                viewedTasksHistory.size() == 2);
    }
    @Test
    void checkShowHistoryWithDeletionMiddle(){
        ArrayList<String> viewedTasksHistory = new ArrayList<>();
        Task firstTestTask = currManager.createTask(-27,"FirstName", "Test overview");
        Task secTestTask = currManager.createTask(-28,"SecName", "Test overview");
        Task thirdTestTask = currManager.createTask(-29,"ThirdName", "Test overview");
        testTaskList.put(firstTestTask.id, firstTestTask);
        testTaskList.put(secTestTask.id, secTestTask);
        testTaskList.put(thirdTestTask.id, thirdTestTask);
        currManager.showTask(firstTestTask);
        currManager.showTask(secTestTask);
        currManager.showTask(thirdTestTask);
        currManager.deleteTask(-28,testTaskList);
        viewedTasksHistory = currManager.getHistory(viewedTasksHistory);
        Assertions.assertTrue(viewedTasksHistory.contains("(TASK FirstName)") &&
                !viewedTasksHistory.contains("(TASK SecName)") &&
                viewedTasksHistory.contains("(TASK ThirdName)") &&
                viewedTasksHistory.size() == 2);
    }
    @Test
    void checkShowHistoryWithDeletionLast(){
        ArrayList<String> viewedTasksHistory = new ArrayList<>();
        Task firstTestTask = currManager.createTask(-27,"FirstName", "Test overview");
        Task secTestTask = currManager.createTask(-28,"SecName", "Test overview");
        Task thirdTestTask = currManager.createTask(-29,"ThirdName", "Test overview");
        testTaskList.put(firstTestTask.id, firstTestTask);
        testTaskList.put(secTestTask.id, secTestTask);
        testTaskList.put(thirdTestTask.id, thirdTestTask);
        currManager.showTask(firstTestTask);
        currManager.showTask(secTestTask);
        currManager.showTask(thirdTestTask);
        currManager.deleteTask(-29,testTaskList);
        viewedTasksHistory = currManager.getHistory(viewedTasksHistory);
        Assertions.assertTrue(viewedTasksHistory.contains("(TASK FirstName)") &&
                viewedTasksHistory.contains("(TASK SecName)") &&
                !viewedTasksHistory.contains("(TASK ThirdName)") &&
                viewedTasksHistory.size() == 2);
    }
}