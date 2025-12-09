import exception.TaskIdNotFoundException;
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
                    6. List all Tasks
                    7. Exit
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
                        } finally {
                            scanner.nextLine();
                        }
                    }

                    try {
                        taskManager.doesTaskExist(idOfTaskToMark);
                    } catch (TaskIdNotFoundException e) {
                        System.err.println("This Task-ID does not exist!\n");
                        continue;
                    }

                    System.out.println("""
                            === How would you like to mark the task? ===
                            1. DONE
                            2. IN PROGRESS
                            3. NOT DONE
                            """
                    );

                    while (true) {
                        try {
                            progressStatusChoice = scanner.nextInt();
                            if (progressStatusChoice == 1 || progressStatusChoice == 2 || progressStatusChoice == 3) {
                                taskManager.markTask(idOfTaskToMark, progressStatusChoice);
                                break;
                            } else {
                                System.err.println("Invalid status choice! Please enter 1, 2, or 3.\n");
                            }
                        } catch (InputMismatchException e) {
                            System.err.println("Please enter a valid status!");
                        } finally {
                            scanner.nextLine();
                        }
                    }
                    break;
                case 5:
                    int tasksWithStatusNumber;

                    System.out.println("""
                            === What tasks would you like to see? ===
                            1. DONE
                            2. IN PROGRESS
                            3. NOT DONE
                            
                            """);

                    while (true) {
                        try {
                            tasksWithStatusNumber = scanner.nextInt();
                            if (tasksWithStatusNumber == 1 || tasksWithStatusNumber == 2 || tasksWithStatusNumber == 3) {
                                taskManager.listAllTasksWithStatus(tasksWithStatusNumber);
                                break;
                            } else {
                                System.err.println("Please enter a valid number between 1 - 3!");
                            }
                        } catch (InputMismatchException e) {
                            System.err.println("Please enter a valid number!");
                        } finally {
                            scanner.nextLine();
                        }
                    }
                    break;
                case 6:
                    taskManager.listAllTasks();
                    break;
                case 7:
                    scanner.close();
                    doesMenuShow = false;
                    break;
                default:
                    System.err.println("Please enter a valid choice between 1 - 6!");
            }
        }
    }
}