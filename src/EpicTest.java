import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class EpicTest {
    InMemoryTaskManager currManager = new InMemoryTaskManager();
    HashMap<Integer, Epic> testEpicList = new HashMap<>();
    HashMap<Integer, Subtask> testSubtaskList = new HashMap<>();

    @Test
    void checkEpicStatusWithZeroSubtasks(){
        Epic testEpic = currManager.createEpic("TestEpicName","TestEpicOverview", -1);
        testEpicList.put(testEpic.id, testEpic);
        Assertions.assertEquals(testEpic.status, "NEW", "Статус нового эпика неверен");
    }
    @Test
    void checkEpicStatusWithNewSubtasks(){
        Epic testEpic = currManager.createEpic("TestEpicName","TestEpicOverview", -5);
        testEpicList.put(testEpic.id, testEpic);
        Subtask testSubtask1 = currManager.createSubtask(-6, -5,testEpicList, "TestSubtaskName", "TestSubtaskOverview");
        testSubtaskList.put(testSubtask1.id, testSubtask1);
        Subtask testSubtask2 = currManager.createSubtask(-7, -5,testEpicList, "TestSubtaskName", "TestSubtaskOverview");
        testSubtaskList.put(testSubtask2.id, testSubtask2);
        Assertions.assertEquals(testEpic.status, "NEW", "Статус эпика с двумя новыми подзадачами неверен");
    }
    @Test
    void checkEpicStatusWithDoneSubtasks() {
        Epic testEpic = currManager.createEpic("TestEpicName","TestEpicOverview", -10);
        testEpicList.put(testEpic.id, testEpic);
        Subtask testSubtask1 = currManager.createSubtask(-11, -10,testEpicList, "TestSubtaskName", "TestSubtaskOverview");
        testSubtaskList.put(testSubtask1.id, testSubtask1);
        Subtask testSubtask2 = currManager.createSubtask(-12, -10,testEpicList, "TestSubtaskName", "TestSubtaskOverview");
        testSubtaskList.put(testSubtask2.id, testSubtask2);
        currManager.updateSubtask(-11, testSubtaskList, testEpicList, "Y", "N", "NewOverview");
        currManager.updateSubtask(-12, testSubtaskList, testEpicList, "Y", "N", "NewOverview");
        Assertions.assertEquals(testEpic.status, "DONE", "Статус эпика с двумя выполненными подзадачами неверен");
    }
    @Test
    void checkEpicStatusWithOneDoneSubtask() {
        Epic testEpic = currManager.createEpic("TestEpicName","TestEpicOverview", -15);
        testEpicList.put(testEpic.id, testEpic);
        Subtask testSubtask1 = currManager.createSubtask(-16, -15,testEpicList, "TestSubtaskName", "TestSubtaskOverview");
        testSubtaskList.put(testSubtask1.id, testSubtask1);
        Subtask testSubtask2 = currManager.createSubtask(-17, -15,testEpicList, "TestSubtaskName", "TestSubtaskOverview");
        testSubtaskList.put(testSubtask2.id, testSubtask2);
        currManager.updateSubtask(-16, testSubtaskList, testEpicList, "Y", "N", "NewOverview");
        Assertions.assertEquals(testEpic.status, "IN_PROGRESS", "Статус эпика с одной выполненной и одной новой подзадачами неверен");
    }
    @Test
    void checkEpicStatusWithOneInProgressSubtask() {
        Epic testEpic = currManager.createEpic("TestEpicName","TestEpicOverview", -20);
        testEpicList.put(testEpic.id, testEpic);
        Subtask testSubtask1 = currManager.createSubtask(-21, -20,testEpicList, "TestSubtaskName", "TestSubtaskOverview");
        testSubtaskList.put(testSubtask1.id, testSubtask1);
        currManager.updateSubtask(-21, testSubtaskList, testEpicList, "P", "N", "NewOverview");
        Assertions.assertEquals(testEpic.status, "IN_PROGRESS", "Статус эпика с одной выполняемой подзадачей неверен");
    }
}