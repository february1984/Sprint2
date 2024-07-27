import java.time.format.DateTimeFormatter;

public class TreeNode {
    Task value;
    TreeNode left;
    TreeNode right;

    TreeNode(Task value) {
        this.value = value;
        right = null;
        left = null;
    }

    public TreeNode addRecursive(TreeNode current, Task value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy:HH:mm");
        if (current == null) {
            return new TreeNode(value);
        }
        if (java.time.LocalDateTime.parse(value.startTime, formatter).isBefore(java.time.LocalDateTime.parse(current.value.startTime, formatter))) {
            current.left = addRecursive(current.left, value);
        } else if (java.time.LocalDateTime.parse(value.startTime, formatter).isAfter(java.time.LocalDateTime.parse(current.value.startTime, formatter))) {
            current.right = addRecursive(current.right, value);
        } else {
            return current;
        }
        return current;
    }
    public void showTheTreeRecursive (TreeNode root){
        if (root.left != null) {
            root.showTheTreeRecursive(root.left);
        }
        if (root.right != null){
            System.out.println(root.value.name + " " + root.value.overview + " " + root.value.startTime);
            root.showTheTreeRecursive(root.right);
        }
        else System.out.println(root.value.name + " " + root.value.overview + " " + root.value.startTime);
    }
}