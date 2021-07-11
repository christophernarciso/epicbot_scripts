package com.epicbot.api.task;

import com.epicbot.api.KScript;
import com.epicbot.api.generics.KScriptContext;

/**
 * Created by Mitchell on 9-9-2015.
 */
public abstract class Task extends KScriptContext<KScript> {

    public abstract boolean isActive();

    public void onTaskStart() throws InterruptedException { }

    public abstract void run() throws InterruptedException;

    public void onTaskFinish() throws InterruptedException { }

    public abstract String getTaskName();
}
