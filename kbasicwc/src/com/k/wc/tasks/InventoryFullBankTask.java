package com.k.wc.tasks;

import com.epicbot.api.task.Task;
import com.k.wc.data.Data;
import com.k.wc.data.ILocationTarget;

public class InventoryFullBankTask extends Task {

    private final ILocationTarget target;

    public InventoryFullBankTask(final ILocationTarget locationTarget) {
        this.target = locationTarget;
    }

    @Override
    public boolean isActive() {
        return getInventory().isFull();
    }

    @Override
    public void run() throws InterruptedException {
        if (getBank().isOpen()) {
            // Handle bank deposit the bank is open
            if (getBank().depositAllExcept(Data.AXES))
                sleepUntil(() -> !getInventory().isFull(), 2000, 500);
        } else if (getBank().open()) {
            // We try to open a nearby bank.
            sleepUntil(() -> getBank().isOpen(), 3000, 1000);
        } else {
            // Walk to the target bank location
            getWebWalking().walkTo(target.getBankArea().getNearestTile(getContext()));
        }
    }

    @Override
    public String getTaskName() {
        return "[Action Task]: Bank Logs";
    }

}
