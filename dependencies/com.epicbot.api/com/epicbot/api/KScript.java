package com.epicbot.api;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.task.Task;
import com.epicbot.api.task.TaskHandler;
import com.epicbot.api.trackers.TimeTracker;

import java.awt.*;

public abstract class KScript extends LoopScript {

    /**
     * For memory debug
     */
    private final int MEMORY_WIDTH = 766, MEMORY_HEIGHT = 2, MEMORY_X = 0,
            MEMORY_Y = 0, MEMORY_TEXT_X = 760, MEMORY_TEXT_Y = 13;

    /**
     * Flag used to enable(true) and disable(false) debug.
     */
    public boolean debug = false;

    /**
     * Speed used to control the loop
     */
    public int loopSpeed;

    // Create a Task Handler
    private TaskHandler taskHandler = new TaskHandler();
    // Create a TimeTracker :D
    private TimeTracker timeTracker = new TimeTracker();
    // Create memory debug runtime
    private Runtime runtime = Runtime.getRuntime();

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
        // Memory debug
        double maxMemory = runtime.maxMemory();
        double totalMemory = runtime.totalMemory();
        double freeMemory = runtime.freeMemory();
        String str3 = humanReadableByteCount((long) (totalMemory - freeMemory), false),
                str2 = humanReadableByteCount((long) totalMemory, false),
                str1 = humanReadableByteCount((long) maxMemory, false);
        Font f = gfx.getFont();
        gfx.setFont(new Font("", Font.BOLD, 10));
        gfx.setColor(new Color(0, 138, 255));
        gfx.fillRect(MEMORY_X, MEMORY_Y, MEMORY_WIDTH, MEMORY_HEIGHT);
        gfx.drawString(str1, MEMORY_TEXT_X - gfx.getFontMetrics().stringWidth(str1), MEMORY_TEXT_Y + 18);
        gfx.setColor(new Color(255, 168, 0));
        gfx.fillRect(MEMORY_X, MEMORY_Y, (int) ((totalMemory / maxMemory) * MEMORY_WIDTH), MEMORY_HEIGHT);
        gfx.drawString(str2, MEMORY_TEXT_X - gfx.getFontMetrics().stringWidth(str2), MEMORY_TEXT_Y + 9);
        gfx.setColor(new Color(255, 0, 0));
        gfx.fillRect(MEMORY_X, MEMORY_Y, (int) (((totalMemory - freeMemory) / maxMemory) * MEMORY_WIDTH), MEMORY_HEIGHT);
        gfx.drawString(str3, MEMORY_TEXT_X - gfx.getFontMetrics().stringWidth(str3), MEMORY_TEXT_Y);
        gfx.setFont(f);
    }

    /**
     * @return readableBytes for memory
     */
    private String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
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

    public void setTaskHandler(TaskHandler taskHandler) {
        this.taskHandler = taskHandler;
    }

    public TimeTracker getTimeTracker() {
        return timeTracker;
    }

    public void setTimeTracker(TimeTracker timeTracker) {
        this.timeTracker = timeTracker;
    }

}