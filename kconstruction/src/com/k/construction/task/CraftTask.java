package com.k.construction.task;

import com.epicbot.api.shared.entity.GameEntity;
import com.epicbot.api.shared.methods.IDialogueAPI;
import com.epicbot.api.task.Task;
import com.k.construction.data.Data;

import java.awt.event.KeyEvent;

public class CraftTask extends Task {
    @Override
    public boolean isActive() {
        return getInventory().contains(Data.HAMMER) && getInventory().contains(Data.SAW) &&
                (Data.larderMode && getInventory().getCount(Data.OAK_PLANK) >= Data.OAK_REQ
                        || getInventory().getCount(Data.MAH_PLANK) >= Data.MAH_REQ);
    }

    @Override
    public void run() throws InterruptedException {
        // Check if we should remove completed objects.
        GameEntity remove = getRemovableObject();

        // If it exists then remove it
        if (remove != null) {
            System.out.println("Removable object found");
            if (getDialogues().isDialogueOpen(IDialogueAPI.DialogueType.OPTION)) {
                System.out.println("Sending interact key");
                getKeyboard().sendKey(KeyEvent.VK_1);
                // Since send key is a void the sleep here will be here whether it was successful or not
                sleepUntil(() -> !getDialogues().isDialogueOpen(IDialogueAPI.DialogueType.OPTION), 3000, 1000);
            } else if (remove.interact("Remove")) {
                System.out.println("Process remove of completed object");
                sleepUntil(() -> getDialogues().isDialogueOpen(IDialogueAPI.DialogueType.OPTION),
                        3000, 1000);
            }
            return;
        }

        // Check if craft object exists.
        GameEntity craft = getCraftObject();

        if (getWidgets().isInterfaceOpen()) {
            System.out.println("Sending interact key");
            getKeyboard().sendKey(getCraftInteractKeyBind());
            // Since send key is a void the sleep here will be here whether it was successful or not.
            sleepUntil(() -> !getWidgets().isInterfaceOpen(), 3000, 1000);
        } else if (craft != null && craft.interact("Build")) {
            // Wait until the craft interface opens
            System.out.println("Process build of craft object");
            sleepUntil(() -> getWidgets().isInterfaceOpen(), 5000, 1000);
        }
    }

    @Override
    public String getTaskName() {
        return Data.larderMode ? "[Craft Task]: Oak Larder" : "[Craft Task]: Mahogany Table";
    }

    /**
     * @return the craft we are trying to make based on the mode.
     */
    private GameEntity getCraftObject() {
        return getObjects().query().named(Data.larderMode ? Data.LARDER_SPACE : Data.TABLE_SPACE)
                .reachable().results().nearest();
    }

    /**
     * @return the removable finished product after crafting.
     */
    private GameEntity getRemovableObject() {
        return getObjects().query().named(Data.larderMode ? Data.CRAFT_COMPLETION_LARDER : Data.CRAFT_COMPLETION_TABLE)
                .reachable().actions("Remove").results().nearest();
    }

    /**
     * @return the key-bind associated with each craft
     */
    private int getCraftInteractKeyBind() {
        return Data.larderMode ? KeyEvent.VK_2 : KeyEvent.VK_3;
    }
}
