package com.k.wc.tasks;

import com.epicbot.api.shared.entity.GameEntity;
import com.epicbot.api.task.Task;
import com.k.wc.data.ILocationTarget;

public class ChopTask extends Task {

    private final ILocationTarget target;

    public ChopTask(final ILocationTarget target) {
        this.target = target;
    }

    @Override
    public boolean isActive() {
        return !getInventory().isFull();
    }

    @Override
    public void run() throws InterruptedException {
        // Lets continue while animating
        if (myPlayer().isAnimating())
            return;

        // If the bank is open lets close it
        if (getBank().isOpen())
            getBank().close();

        // Walk to the target action area if we need to.
        if (!target.getActionArea().contains(myPosition())) {
            getWebWalking().walkTo(target.getActionArea().getNearestTile(getContext()));
            return;
        }

        GameEntity targetEntity = getObjects().query().named(target.getTarget())
                .within(target.getActionArea()).reachable().results().nearest();
        // Start animating by interacting with the game object a.k.a chop some wood
        if (targetEntity != null && targetEntity.interact()) {
            sleepUntil(() -> myPlayer().isAnimating(), 5000, 1000);
        }
    }

    @Override
    public String getTaskName() {
        return "[Action Task]: Woodcutting";
    }
}
