import java.util.HashMap;

public class Managers {
    public Manager getDefault(){
        return new Manager() {
            @Override
            public void showTaskViewsHistory() {

            }

            @Override
            public Task createTask(int maxId, String name, String overview) {
                return null;
            }

            @Override
            public void showTask(Task task) {

            }

            @Override
            public void showAllTasks(HashMap<Integer, Task> taskList) {

            }

            @Override
            public void deleteTask(Integer taskToDelete, HashMap<Integer, Task> currentTaskList) {

            }

            @Override
            public void deleteAllTasks(HashMap<Integer, Task> taskListToClear) {

            }

            @Override
            public void updateTask(Integer taskToUpdateID, HashMap<Integer, Task> currentTaskList, String completeCommand, String updateCommand, String newOverview) {

            }

            @Override
            public Epic createEpic(String name, String overview, int maxId) {
                return null;
            }

            @Override
            public void showEpic(Epic epic, HashMap<Integer, Subtask> subtaskList) {

            }

            @Override
            public void showAllEpics(HashMap<Integer, Epic> epicList, HashMap<Integer, Subtask> subtaskList) {

            }

            @Override
            public void deleteEpic(Integer epicToDelete, HashMap<Integer, Epic> epicList, HashMap<Integer, Subtask> subtaskList, String subtaskIncluding) {

            }

            @Override
            public void deleteAllEpics(HashMap<Integer, Epic> epicListToClear, HashMap<Integer, Subtask> subtaskListToClear) {

            }

            @Override
            public void updateEpic(Integer epicToUpdateID, HashMap<Integer, Epic> epicList, String newOverview) {

            }

            @Override
            public Subtask createSubtask(int maxId, int parentID, HashMap<Integer, Epic> epicList, String name, String overview) {
                return null;
            }

            @Override
            public void showSubtask(Subtask subtask) {

            }

            @Override
            public void showAllSubtasks(HashMap<Integer, Subtask> subtaskList) {

            }

            @Override
            public void deleteSubtask(Integer subtaskToDelete, HashMap<Integer, Subtask> currentSubtaskList, HashMap<Integer, Epic> currentEpicList) {

            }

            @Override
            public void deleteAllSubtasks(HashMap<Integer, Subtask> subtaskListToClear) {

            }

            @Override
            public void updateSubtask(Integer subtaskToUpdateID, HashMap<Integer, Subtask> currentSubtaskList,
                                      HashMap<Integer, Epic> currentEpicList, String completeCommand, String changeCommand, String newOverview) {

            }
        };
    }
}
