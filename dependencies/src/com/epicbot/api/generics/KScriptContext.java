package com.epicbot.api.generics;

import com.epicbot.api.KScript;
import com.epicbot.api.trackers.TimeTracker;

public class KScriptContext<MainKScript extends KScript> extends KContext<MainKScript> {
    public KScriptContext() {
        super();
    }

    public String getStatus() {
        return mainKScriptFile.getStatus();
    }

    public TimeTracker getTimeTracker() {
        return mainKScriptFile.getTimeTracker();
    }
}
