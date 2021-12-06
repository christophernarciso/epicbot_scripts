package com.k.wc.data.targets;

import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;
import com.k.wc.data.ILocationTarget;

/**
 * Target: Basic trees
 * Target Area: Basic trees north west of the grand exchange.
 * Target Bank Area: Grand exchange
 */
public class BasicTree implements ILocationTarget {

    @Override
    public String getTarget() {
        return "Tree";
    }


    @Override
    public Area getActionArea() {
        return new Area(
                new Tile(3139, 3505),
                new Tile(3139, 3512),
                new Tile(3139, 3512),
                new Tile(3143, 3516),
                new Tile(3157, 3516),
                new Tile(3158, 3510),
                new Tile(3142, 3502)
        );
    }

    @Override
    public Area getBankArea() {
        return new Area(3160, 3493, 3169, 3485);
    }

}
