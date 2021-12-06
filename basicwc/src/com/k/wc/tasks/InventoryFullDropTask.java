package com.k.wc.tasks;

import com.epicbot.api.task.Task;
import com.k.wc.data.Data;

public class InventoryFullDropTask extends Task {

    @Override
    public boolean isActive() {
        return getInventory().isFull();
    }

    @Override
    public void run() throws InterruptedException {
        // We want to remove everything in the inventory that is not our axe. Power chop mode.
        getInventory().dropAllExcept(Data.AXES);
    }

    @Override
    public String getTaskName() {
        return "[Action Task]: Drop Logs";
    }

}
