/*
TODO: add, update and delete tasks
TODO: mark a task as in progress or done
TODO: list all tasks
TODO: list all tasks that are done
TODO: list all tasks that are not done
TODO: list all tasks that are in progress
 */

import task.Task;
import task.TaskManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskApp {
    public static void main(String[] args) {
        final TaskManager taskManager = new TaskManager();
        boolean doesMenuShow = true;
        final Scanner scanner = new Scanner(System.in);
        int menuInput;

        while (doesMenuShow) {
            System.out.println("""
                === Task Tracker ===
                1. Add Task
                2. Update Task
                3. Delete Task
                4. Mark Task
                5. List Task(s)
                6. Exit
                """);

            while (true) {
                try {
                    menuInput = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("Please enter a valid number!");
                } finally {
                    scanner.nextLine();
                }
            }

            switch (menuInput) {
                case 1:
                    Task newTask;
                    String newTaskDescription;

                    System.out.println("=== Enter a task ===");
                    newTaskDescription = scanner.nextLine();

                    newTask = new Task(newTaskDescription);

                    taskManager.addTask(newTask);
                    break;
                case 2:
                    int lookForId;
                    System.out.println("=== Enter the ID of the task you would like to update ===");

                    while (true) {
                        try {
                            lookForId = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.err.println("Please enter a valid ID!");
                        } finally {
                            scanner.nextLine();
                        }
                    }
            }
        }

    }
}