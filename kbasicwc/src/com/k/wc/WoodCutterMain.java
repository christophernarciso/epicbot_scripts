package com.k.wc;

import com.epicbot.api.KScript;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.script.ScriptManifest;
import com.k.wc.data.ILocationTarget;
import com.k.wc.data.targets.BasicTree;
import com.k.wc.tasks.ChopTask;
import com.k.wc.tasks.InventoryFullBankTask;
import com.k.wc.tasks.InventoryFullDropTask;

@ScriptManifest(gameType = GameType.OS, name = "KWoodCutter")
public class WoodCutterMain extends KScript {

    boolean dropMode = true;
    ILocationTarget locationTarget;

    @Override
    public void init() {
        // TODO: setup drop mode and locationTarget through configuration

        // default mode
        locationTarget = new BasicTree();
        getPaintFrame().addLine("Location Target: ", locationTarget.getTarget());
    }

    @Override
    public void registerTasks() {
        // If we are not dropping full inventories
        if (!dropMode)
            registerTask(new InventoryFullBankTask(locationTarget));

        // Register other tasks
        registerTask(new InventoryFullDropTask());
        registerTask(new ChopTask(locationTarget));
    }
}
