package com.k.wc.data;

import com.epicbot.api.shared.model.Area;

/**
 * Target Interface so we can create multiple locations that a user can select in a UI. (see BasicTree.java for usage)
 */
public interface ILocationTarget {
    public String getTarget();

    public Area getActionArea();

    public Area getBankArea();
}
