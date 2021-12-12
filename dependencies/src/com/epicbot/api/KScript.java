package com.epicbot.api;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.util.paint.frame.PaintFrame;
import com.epicbot.api.task.Task;
import com.epicbot.api.task.TaskHandler;
import com.epicbot.api.trackers.TimeTracker;

import java.awt.*;

public abstract class KScript extends LoopScript {

    /**
     * Flag used to enable(true) and disable(false) debug.
     */
    private boolean debug = false;

    /**
     * Speed used to control the loop
     */
    private int loopSpeed;

    // Create a Task Handler
    private TaskHandler taskHandler = new TaskHandler();
    // Create a TimeTracker :D
    private TimeTracker timeTracker = new TimeTracker();
    // Create memory debug runtime
    private Runtime runtime = Runtime.getRuntime();

    // Create PaintFrame
    private PaintFrame paintFrame = new PaintFrame();

    /**
     * Used to run pre init code.
     */
    public abstract void init();

    /**
     * Used to register the tasks in.
     */
    public abstract void registerTasks();

    /**
     * Used to register a Task.
     *
     * @param task a task to register.
     */
    public void registerTask(Task task) {
        task.setScript(this);
        taskHandler.registerTask(task);
    }

    @Override
    public boolean onStart(String... strings) {
        // Space for new initiated objects for future dependency api

        init();
        registerTasks();
        setLoopSpeed(600);
        timeTracker.reset();
        paintFrame.setTitle(getManifest().name());
        return true;
    }

    @Override
    protected int loop() {
        try {
            taskHandler.activate();
            taskHandler.run(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return loopSpeed;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPaint(Graphics2D gfx, APIContext ctx) {
        super.onPaint(gfx, ctx);
        // Script paint frame
        paintFrame.addLine("Time: ", getTimeTracker().format());
        paintFrame.addLine("Status: ", getStatus());
        paintFrame.draw(gfx, 0, 90, ctx);
    }

    /**
     * @return a string containing the current active Task name.
     */
    public String getStatus() {
        return taskHandler.getStatus();
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getLoopSpeed() {
        return loopSpeed;
    }

    public void setLoopSpeed(int loopSpeed) {
        this.loopSpeed = loopSpeed;
    }

    public TaskHandler getTaskHandler() {
        return taskHandler;
    }

    public TimeTracker getTimeTracker() {
        return timeTracker;
    }

    public PaintFrame getPaintFrame() {
        return paintFrame;
    }
}