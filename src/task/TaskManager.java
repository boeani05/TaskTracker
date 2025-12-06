package task;

import exception.TaskIdNotFoundException;
import status.Progress;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private static final String FILE_NAME = "tasks.json";

    public TaskManager() {
        loadTasksFromJson();
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
        saveTasksToJson();

        System.out.printf("=== Task added successfully (ID: %d)%n", task.getId());
    }

    public void doesTaskExist(int taskId) throws TaskIdNotFoundException {
        if (tasks.containsKey(taskId)) {
            return;
        }

        throw new TaskIdNotFoundException("Task ID not found!");
    }

    public void updateTask(int taskId, String updatedDescription) {
        Task taskToUpdate = tasks.get(taskId);

        taskToUpdate.setDescription(updatedDescription);
        taskToUpdate.setUpdatedAt(LocalDateTime.now());
        saveTasksToJson();

        System.out.println("--- Task updated successfully ---\n");
    }

    public void deleteTask(int taskId) {
        tasks.remove(taskId);
        saveTasksToJson();

        System.out.println("--- Task deleted successfully ---\n");
    }

    public void markTask(int taskId, int progressChoice) {
        Task taskToMark = tasks.get(taskId);
    }

    private void saveTasksToJson() {
        Path filePath = Path.of(FILE_NAME);
        try {
            String jsonArray = tasks.values().stream()
                    .map(Task::toJson)
                    .collect(Collectors.joining(",\n    ", "[\n    ", "\n]"));

            Files.writeString(filePath, jsonArray);
        } catch (IOException e) {
            System.err.println("Error while saving task to JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadTasksFromJson() {
        Path filePath = Path.of(FILE_NAME);
        try {
            if (!Files.exists(filePath) || Files.size(filePath) == 0) {
                return;
            }
        } catch (IOException e) {
            System.err.println("Error while handling I/O operations.");
            return;
        }

        try {
            String jsonContent = Files.readString(filePath);
            if (jsonContent.trim().isEmpty() || jsonContent.trim().equals("[]")) {
                return;
            }

            String innerContent = jsonContent.trim();
            if (innerContent.startsWith("[") && innerContent.endsWith("]")) {
                innerContent = innerContent.substring(1, innerContent.length() - 1);
            }

            String[] taskJsonStrings = innerContent.split("},\\s*\\{");

            tasks.clear();

            for (String taskJsonPart : taskJsonStrings) {
                String fullTaskJson = taskJsonPart.trim();
                if (!fullTaskJson.startsWith("{")) {
                    fullTaskJson = "{" + fullTaskJson;
                }
                if (!fullTaskJson.endsWith("}")) {
                    fullTaskJson = fullTaskJson + "}";
                }

                if (!fullTaskJson.equals("{}") && !fullTaskJson.isEmpty()) {
                    Task loadedTask = Task.fromJson(fullTaskJson);
                    tasks.put(loadedTask.getId(), loadedTask);
                }
            }
        } catch (IOException e) {
            System.err.println("Error while loading task from JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}