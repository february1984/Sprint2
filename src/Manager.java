import java.util.Scanner;

public class Manager {
    static Scanner scanner = new Scanner(System.in);
    static int id = 1;

    public static Task createTask () {
        Task taskToSet = new Task();

        System.out.println("Заводим новую задачу:\n");
        //Сразу присваиваем новой задаче ID и увеличиваем для следующей
        taskToSet.id = id;
        id++;
        //Ввод параметров задачи. Статус всегда NEW
        System.out.println("Как назовем задачу?");
        taskToSet.name = scanner.nextLine();
        System.out.println("Что будем делать?");
        taskToSet.overview = scanner.nextLine();
        taskToSet.status = "NEW";
        return taskToSet;
    }

    public static void showTask(Task task) {
        System.out.println(task.id + "," + task.status + "," + task.name + "," + task.overview + "\n");
    }
}
