package com.epicbot.api.task;

import com.epicbot.api.KScript;
import com.epicbot.api.shared.util.time.Time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Mitchell on 9-9-2015.
 */
public class TaskHandler {

    private ArrayList<Task> taskList = new ArrayList<Task>();

    private Task currentTask = null;
    private Task queuedTask = null;
    private boolean newTask = false;

    public TaskHandler() {
    }

    public void registerTask(Task task) {
        taskList.add(task);
    }

    public void activate() {
        queuedTask = null;
        for (Task task : taskList) {
            if (task.isActive()) {
                queuedTask = task;
                return;
            }
        }
    }

    public void run(KScript script) throws InterruptedException {
        newTask = false;

        // run next task in que if spot is open
        if (currentTask == null && queuedTask != null) {
            currentTask = queuedTask;
            queuedTask = null;
            newTask = true;
        }

        // idle if no task
        if (currentTask == null) {
            if (script.isDebug()) {
                System.out.println("[DEBUG] No task available.");
            }
            Time.sleep(600);
            return;
        }

        // run the onTaskStart code if needed
        if (newTask) {
            if (script.isDebug()) {
                System.out.println("[DEBUG] Now starting " + currentTask.getTaskName());
            }
            currentTask.onTaskStart();
        }

        if (currentTask.isActive()) {
            currentTask.run();
        }

        // run the onTaskFinish code if needed
        if (!currentTask.isActive()) {
            if (script.isDebug()) {
                System.out.println("[DEBUG] Now stopping " + currentTask.getTaskName());
            }
            currentTask.onTaskFinish();
            currentTask = null;
        }

    }

    public String getStatus() {
        if (currentTask == null) return "None";
        return currentTask.getTaskName();
    }

    public boolean isEmpty() {
        return taskList.isEmpty();
    }

    public void randomizeOrder() {
        Collections.shuffle(taskList, new Random());
    }

}
