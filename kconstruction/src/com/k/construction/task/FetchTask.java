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
        String plank = Data.larderMode ? Data.OAK_PLANK : Data.MAH_PLANK;
        return !getInventory().contains(i -> !i.isNoted() && i.getName().equals(plank));
    }

    @Override
    public void run() throws InterruptedException {
        // First we want to stop the script if there is no coins left to pay the service fee.
        if (getInventory().getCount(Data.Coins) >= Data.SERVICE_FEE) {
            getScript().stop(String.format("A service fee of %d gold is needed to continue running the script", Data.SERVICE_FEE));
            return;
        }

        // Get planks from our butler.
        GameEntity butler = getNpcs().query().named(Data.BUTLER_NAME).distance(2.0).reachable().results().nearest();

        // Check if the butler is too far from us for efficiency reasons.
        if (butler == null) {
            System.out.println("Butler does not exist or is farther than 2 tiles");
            // Open the settings tab
            if (!getTabs().isOpen(ITabsAPI.Tabs.SETTINGS))
                getTabs().open(ITabsAPI.Tabs.SETTINGS);

            // Open the house settings
            WidgetChild houseOptionsMenuCallServant = getWidgets().query().text("Call Servant").results().first();
            WidgetChild houseSettingButton = getHouseSettingsButton();

            if (houseOptionsMenuCallServant != null && houseOptionsMenuCallServant.isVisible()) {
                // Call the servant using the menu button
                System.out.println("Calling butler using house setting button");
                if (houseOptionsMenuCallServant.interact())
                    sleepUntil(() -> getDialogues().isDialogueOpen(IDialogueAPI.DialogueType.OPTION), 3000, 1000);
            } else if (houseSettingButton != null && houseSettingButton.isVisible()) {
                // Open the house settings menu
                System.out.println("Open house settings menu");
                if (houseSettingButton.interact())
                    sleepUntil(this::isHouseSettingButtonVisible, 3000, 1000);
            }
        } else if (getDialogues().isDialogueOpen(IDialogueAPI.DialogueType.OPTION)) {
            System.out.println("Sending interact key");
            getKeyboard().sendKey(KeyEvent.VK_1);
            // Since send key is a void the sleep here will be here whether it was successful or not
            String plank = Data.larderMode ? Data.OAK_PLANK : Data.MAH_PLANK;
            // Service wait time: 7 secs
            System.out.println("Waiting for our planks to arrive");
            sleepUntil(() -> getInventory().contains(i -> !i.isNoted() && i.getName().equals(plank)),
                    10000, 1000);
        } else if (butler.interact("Talk-to")) {
            // Talk to the butler since he is close.
            System.out.println("Dialogue with butler");
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
        return getWidgets().query().actions("View House Options").results().first();
    }

    private boolean isHouseSettingButtonVisible() {
        return getHouseSettingsButton().isValid();
    }
}
