# Java CLI Task Tracker

A simple command-line interface (CLI) task management tool implemented in Java. It allows users to add, update, delete, and mark tasks, as well as list tasks based on their status or list all tasks. All tasks are persistently stored in a JSON file without the use of external libraries.

## Table of Contents

-   [Features](#features)
-   [Technologies](#technologies)
-   [Getting Started](#getting-started)
    -   [Compiling and Running](#compiling-and-running)
-   [Usage](#usage)
-   [Future Enhancements](#future-enhancements)
-   [License](#license)

## Features

*   **Add Task:** Create new tasks with a description.
*   **Update Task:** Modify the description of an existing task by its ID.
*   **Delete Task:** Remove tasks from the list by their ID.
*   **Mark Task:** Set a task's status to "DONE", "IN PROGRESS", or "NOT DONE".
*   **List Tasks:**
    *   View all tasks.
    *   View tasks filtered by their status (Done, In Progress, Not Done).
*   **Data Persistence:** All tasks are automatically saved to a `tasks.json` file and loaded when the application starts.
*   **Optimized Performance:** Uses a `HashMap` for task storage, providing efficient ID-based lookup and management (average O(1) time complexity).
*   **Robust Input:** Comprehensive error handling for user input to catch invalid or malformed entries.

## Technologies

*   **Java 17+**: The application is developed using modern Java features.
*   **Standard Java Library**: No external dependencies are used for JSON serialization/deserialization.

## Getting Started
To compile and run the project, you need a Java Development Kit (JDK) version 17 or higher.
### Compiling and Running
1.  **Clone the repository (if applicable):**
    ```bash
    git clone https://github.com/boeani05/TaskTracker.git
    cd TaskTracker
    ```
2.  **Compile:**
    Navigate to the `src` directory and compile the Java files:
    ```bash
    javac -d ../bin src/TaskApp.java src/task/*.java src/status/*.java src/exception/*.java
    ```
    *(Note: The `javac` command might vary depending on your operating system and exact file structure.)*
3.  **Run:**
    Navigate to the parent directory and run the application:
    ```bash
    java -cp bin TaskApp
    ```
## Usage
Upon starting the application, a main menu will be displayed:
=== Task Tracker ===

Add Task
Update Task
Delete Task
Mark Task
List Task(s) # List by status
List all Tasks
Exit