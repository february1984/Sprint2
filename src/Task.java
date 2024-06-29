import java.util.Objects;

public class Task {
    public int id;
    public String name;
    public String overview;
    public String status;
    public String taskType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(overview, task.overview) && Objects.equals(status, task.status) && Objects.equals(taskType, task.taskType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, overview, status, taskType);
    }
}
