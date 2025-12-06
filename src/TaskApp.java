/*
TODO: add, update and delete tasks
TODO: mark a task as in progress or done
TODO: list all tasks
TODO: list all tasks that are done
TODO: list all tasks that are not done
TODO: list all tasks that are in progress
 */

import exception.TaskIdNotFoundException;
import task.Task;
import task.TaskManager;

import java.util.InputMismatchException;
import java.util.Scanner;
import status.Progress;

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
                    String updatedDescription;

                    System.out.println("=== Enter the ID of the task you would like to update ===");

                    while (true) {
                        try {
                            lookForId = scanner.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.err.println("Please enter a valid ID!");
                        } finally {
                            scanner.nextLine();
                        }
                    }

                    try {
                        taskManager.doesTaskExist(lookForId);
                    } catch (TaskIdNotFoundException e) {
                        System.err.println("This Task-ID does not exist!\n");
                        continue;
                    }

                    System.out.println("=== Enter the updated description of the task ===");

                    updatedDescription = scanner.nextLine();

                    taskManager.updateTask(lookForId, updatedDescription);
                    break;
                case 3:
                    int idOfTaskToDelete;
                    String confirmation;

                    System.out.println("=== Enter the ID of the task you would like to delete ===");


                    while (true) {
                        try {
                            idOfTaskToDelete = scanner.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.err.println("Please enter a valid ID!");
                        } finally {
                            scanner.nextLine();
                        }
                    }

                    try {
                        taskManager.doesTaskExist(idOfTaskToDelete);
                    } catch (TaskIdNotFoundException e) {
                        System.err.println("This Task-ID does not exist!\n");
                        continue;
                    }

                    System.out.println("--- Do you really want to delete this task (Y/N)? ---");
                    confirmation = scanner.next().toUpperCase();

                    if (confirmation.equals("Y")) {
                        taskManager.deleteTask(idOfTaskToDelete);
                    }
                    break;
                case 4:
                    int idOfTaskToMark;
                    int progressStatusChoice;

                    System.out.println("=== Enter the ID of the task you would like to mark ===");

                    while (true) {
                        try {
                            idOfTaskToMark = scanner.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.err.println("Please enter a valid ID!");
                        }
                        finally {
                            scanner.nextLine();
                        }
                    }

                    try {
                        taskManager.doesTaskExist(idOfTaskToMark);
                    } catch (TaskIdNotFoundException e) {
                        System.err.println("This Task-ID does no exist!\n");
                        continue;
                    }

                    System.out.printf("""
                            === How would you like to mark the task? ===
                            1. %s
                            2. %s
                            3. %s
                            
                            """,
                            (Object[]) Progress.values()
                    );

                    while (true) {
                        try {
                            progressStatusChoice = scanner.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid status!");
                        }
                        finally {
                            scanner.nextLine();
                        }
                    }

                    switch (progressStatusChoice) {
                        case 1:
                            taskManager.markTask(idOfTaskToMark, progressStatusChoice);
                    }
            }
        }

    }
}