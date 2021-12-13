package com.k.construction;

import com.epicbot.api.KScript;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.script.ScriptManifest;
import com.k.construction.data.Data;
import com.k.construction.task.CraftTask;
import com.k.construction.task.FetchTask;

import java.awt.*;

@ScriptManifest(gameType = GameType.OS, name = "KConstruction")
public class ConstructionMain extends KScript {
    @Override
    public void init() {
        // set task debugger on so we can log to the console
        setDebug(true);
        // If we have oak planks at the start then we do oak larders, otherwise its mahogany tables.
        Data.larderMode = getAPIContext().inventory().contains(Data.OAK_PLANK);
        System.out.println("Doing oak larders: " + Data.larderMode);
    }

    @Override
    public void registerTasks() {
        registerTask(new FetchTask());
        registerTask(new CraftTask());
    }

    @Override
    protected void onPaint(Graphics2D gfx, APIContext ctx) {
        getPaintFrame().draw(gfx,0,90, ctx);
    }
}
