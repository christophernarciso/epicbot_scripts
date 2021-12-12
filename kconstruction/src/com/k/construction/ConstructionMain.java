package com.k.construction;

import com.epicbot.api.KScript;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.script.ScriptManifest;
import com.k.construction.data.Data;
import com.k.construction.task.CraftTask;
import com.k.construction.task.FetchTask;

@ScriptManifest(gameType = GameType.OS, name = "KConstruction")
public class ConstructionMain extends KScript {
    @Override
    public void init() {
        // If we have oak planks at the start then we do oak larders, otherwise its mahogany tables.
        Data.larderMode = getAPIContext().inventory().contains(Data.OAK_PLANK);
        System.out.println("Doing oak larders: " + Data.larderMode);
    }

    @Override
    public void registerTasks() {
        registerTask(new FetchTask());
        registerTask(new CraftTask());
    }
}
