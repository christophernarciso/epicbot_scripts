package com.k.construction.task;

import com.epicbot.api.shared.entity.GameEntity;
import com.epicbot.api.shared.entity.WidgetChild;
import com.epicbot.api.shared.methods.IDialogueAPI;
import com.epicbot.api.shared.methods.ITabsAPI;
import com.epicbot.api.task.Task;
import com.k.construction.data.Data;

import java.awt.event.KeyEvent;

/**
 * Currently makes use of the house butler. [DEMON]
 */
public class FetchTask extends Task {
    @Override
    public boolean isActive() {
        // Temporary until fixes..maybe this will be fine?
        return getInventory().getEmptySlotCount() >= 24;
    }

    @Override
    public void run() throws InterruptedException {
        // First we want to stop the script if there is no coins left to pay the service fee.
        if (getInventory().getCount(Data.Coins) >= Data.SERVICE_FEE) {
            getScript().stop(String.format("A service fee of %d gold is needed to continue running the script.", Data.SERVICE_FEE));
            return;
        }

        // Second we want to stop the script if there is no NOTED planks left.
        String plank = Data.larderMode ? Data.OAK_PLANK : Data.MAH_PLANK;
        int plankNotedId = Data.larderMode ? Data.OAK_NOTED : Data.MAH_NOTED;
        if (!getInventory().contains(plankNotedId)) {
            getScript().stop("Out of planks so we are now stopping the script.");
            return;
        }

        // Get planks from our butler.
        GameEntity butler = getNpcs().query().named(Data.REG_BUTLER, Data.DEMON_BUTLER)
                .distance(2.0).reachable().results().nearest();

        // Check if the butler is too far from us for efficiency reasons.
        if (butler == null) {
            log("Butler does not exist or is farther than 2 tiles");
            // Open the settings tab
            if (!getTabs().isOpen(ITabsAPI.Tabs.SETTINGS))
                getTabs().open(ITabsAPI.Tabs.SETTINGS);

            // Open the house settings
            WidgetChild houseOptionsMenuCallServant = getWidgets().query().text("Call Servant").results().first();
            WidgetChild houseSettingButton = getHouseSettingsButton();

            if (houseOptionsMenuCallServant != null && houseOptionsMenuCallServant.isVisible()) {
                // Call the servant using the menu button
                log("Calling butler using house setting button");
                if (houseOptionsMenuCallServant.interact()) {
                    log("Started sleep check for click call");
                    sleepUntil(() -> getDialogues().isDialogueOpen(IDialogueAPI.DialogueType.OPTION), 5000, 1000);
                    // As of 12/12/2021 getDialogues().isDialogueOpen(IDialogueAPI.DialogueType.OPTION) is always returning true
                    log("Ended sleep check for click call");
                }
            } else if (houseSettingButton != null && houseSettingButton.isVisible()) {
                // Open the house settings menu
                log("Open house settings menu");
                if (houseSettingButton.interact())
                    sleepUntil(this::isHouseSettingButtonVisible, 3000, 1000);
            }
        } else if (getDialogues().isDialogueOpen(IDialogueAPI.DialogueType.CONTINUE)) {
            if (getDialogues().selectContinue())
                sleepUntil(() -> !getDialogues().isDialogueOpen(IDialogueAPI.DialogueType.CONTINUE), 3000, 1000);
        } else if (getDialogues().isDialogueOpen(IDialogueAPI.DialogueType.OPTION)) {
            log("Sending interact key");
            // Since send key is a void the sleep here will be here whether it was successful or not
            getKeyboard().sendKey(KeyEvent.VK_1);

            // Service wait time: 7 secs
            log("Waiting for our planks to arrive");
            // Broken as of 12/12/2021 so this is just a debug log for now.
            log("inventory predicate check: " + String.valueOf(getInventory().contains(i -> !i.isNoted() && i.getName().equals(plank))));
            // 15secs wait time for either butler timer + latency
            sleepUntil(() -> getInventory().isFull() || getDialogues().canContinue(),
                    15000, 1000);
        } else if (butler.interact("Talk-to")) {
            // Talk to the butler since he is close.
            log("Dialogue with butler");
            sleepUntil(() -> getDialogues().isDialogueOpen(IDialogueAPI.DialogueType.OPTION), 3000, 1000);
        }
    }

    @Override
    public String getTaskName() {
        return "[Fetch Task]: Get materials";
    }

    /**
     * @return the house settings button widget since we have multiple usages for this query
     */
    private WidgetChild getHouseSettingsButton() {
        return getWidgets().query().actions("View House Options").visible().results().first();
    }


    /**
     * @return whether the interface is open or not
     */
    private boolean isHouseSettingButtonVisible() {
        WidgetChild test = getHouseSettingsButton();
        return test != null && test.isVisible();
    }
}
