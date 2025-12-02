package task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private static final String FILE_NAME = "tasks.json";

    public TaskManager() {
        loadTasksFromJson();
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasksToJson();
    }

    private void saveTasksToJson() {
        Path filePath = Path.of(FILE_NAME);
        try {
            String jsonArray = tasks.stream()
                    .map(Task::toJson)
                    .collect(Collectors.joining(",\n    ", "[\n    ", "\n]"));

            Files.writeString(filePath, jsonArray);
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern der Aufgaben in der JSON-Datei: " + e.getMessage());
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
                    tasks.add(Task.fromJson(fullTaskJson));
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Aufgaben aus der JSON-Datei: " + e.getMessage());
            e.printStackTrace();
        }
    }
}