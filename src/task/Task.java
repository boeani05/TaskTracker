package task;

import status.Progress;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private static int idCounter = 1;
    private final int id;
    private final String description;
    private Progress status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private DateTimeFormatter dateFormatter;

    public Task(String description) {
        this(idCounter++, description, Progress.NOT_DONE, LocalDateTime.now(), LocalDateTime.now());
    }

    private Task(int id, String description, Progress status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (id >= idCounter) {
            idCounter = id + 1;
        }
    }

    @Override
    public String toString() {
        return String.format("""
                        ID: %d
                        Description: %s
                        Status: %s
                        Created At: %s
                        Updated At: %s
                        
                        """,
                this.id,
                this.description,
                this.status,
                this.createdAt,
                this.updatedAt);
    }

    public int getId() {
        return this.id;
    }

    public String toJson() {
        String escapedDescription = this.description.replace("\"", "\\\"");
        return String.format(
                "{\"id\": %d, \"description\": \"%s\", \"status\": \"%s\", \"createdAt\": \"%s\", \"updatedAt\": \"%s\"}",
                this.id,
                escapedDescription,
                this.status.name(),
                dateFormatter.format(this.createdAt),
                dateFormatter.format(this.updatedAt)
        );
    }

    public static Task fromJson(String jsonString) {
        int id = 0;
        String description = "";
        Progress status = Progress.NOT_DONE;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        try {
            String content = jsonString.trim();
            if (content.startsWith("{") && content.endsWith("}")) {
                content = content.substring(1, content.length() - 1);
            }
            String[] parts = content.split(",\\s*");
            DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (String part : parts) {
                int colonIndex = part.indexOf(":");
                if (colonIndex == -1) continue;
                String key = part.substring(0, colonIndex).trim().replace("\"", "");
                String value = part.substring(colonIndex + 1).trim();

                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                switch (key) {
                    case "id":
                        id = Integer.parseInt(value);
                        break;
                    case "description":
                        description = value.replace("\\\"", "\"");
                        break;
                    case "status":
                        status = Progress.valueOf(value);
                        break;
                    case "createdAt":
                        createdAt = LocalDateTime.from(parser.parse(value));
                        break;
                    case "updatedAt":
                        updatedAt = LocalDateTime.from(parser.parse(value));
                        break;
                }
            }
        } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
            System.err.println("Fehler beim Parsen des JSON-Strings in eine Task: " + e.getMessage());
            e.printStackTrace();
        }
        return new Task(id, description, status, createdAt, updatedAt);
    }
}
