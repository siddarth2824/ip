package duke.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks. A <code>TaskManager</code> object will be able to carry out certain functions
 * with regards to the list of tasks.
 */

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task newTask) {
        tasks.add(newTask);
    }

    public void deleteTask(int taskIndex) {
        tasks.remove(taskIndex - 1);
    }

    public Task getTask(int taskIndex) {
        return this.tasks.get(taskIndex);
    }

    public int getTotalNumberOfTasks() {
        return this.tasks.size();
    }

    public List<Task> getAllTasks() {
        return this.tasks;
    }

    public int getNumberOfCompletedTasks() {
        int completedTasks = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getIsDone()) {
                completedTasks++;
            }
        }
        return completedTasks;
    }

    public int getNumberOfUncompletedTasks() {
        int uncompletedTasks = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).getIsDone()) {
                uncompletedTasks++;
            }
        }
        return uncompletedTasks;
    }

    public List<Task> getAllUncompletedTasks() {
        List<Task> uncompletedTasks = new ArrayList<>();
        tasks.forEach(task -> {
            if (!task.getIsDone()) {
                uncompletedTasks.add(task);
            }
        });
        return uncompletedTasks;
    }

    public List<Task> getAllCompletedTasks() {
        List<Task> completedTasks = new ArrayList<>();
        tasks.forEach(task -> {
            if (task.getIsDone()) {
                completedTasks.add(task);
            }
        });
        return completedTasks;
    }

    public List<Task> getAllTasksBeforeQueryDate(LocalDate queryDate) {
        List<Task> tasksBeforeQueryDate = new ArrayList<>();
        tasks.forEach(task -> {
            if (task instanceof TimeBased) {
                TimeBased timeBasedTask = (TimeBased) task;
                LocalDate taskLocalDate = timeBasedTask.getDate();
                if (queryDate.isAfter(taskLocalDate)) {
                    tasksBeforeQueryDate.add(task);
                }
            }
        });
        return tasksBeforeQueryDate;
    }

    public List<Task> findTasksByKeyword(String keyword) {
        List<Task> filteredTasks = new ArrayList<>();
        tasks.forEach(task -> {
            if (task.getContent().contains(keyword)) {
                filteredTasks.add(task);
            }
        });
        return filteredTasks;
    }

    public List<Task> findTasksByDate(LocalDate queryDate) {
        List<Task> tasksForQueryDate = new ArrayList<>();
        tasks.forEach(task -> {
            if (task instanceof TimeBased) {
                TimeBased timeBasedTask = (TimeBased) task;
                LocalDate taskLocalDate = timeBasedTask.getDate();
                if (queryDate.isEqual(taskLocalDate)) {
                    tasksForQueryDate.add(task);
                }
            }
        });
        return tasksForQueryDate;
    }

    public void markTaskAsDone(int taskIndex) {
        Task updatedTask = tasks.get(taskIndex - 1);
        updatedTask.markTaskAsDone();
        tasks.set(taskIndex - 1, updatedTask);
    }
}
