package com.epicbot.api.generics;

import com.epicbot.api.KScript;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.methods.*;
import com.epicbot.api.shared.model.Tile;
import com.epicbot.api.shared.util.details.Completable;
import com.epicbot.api.shared.util.time.Time;

import java.util.Random;

/**
 * Allows inheritors to access API context wrappers
 */
public class KContext<M extends KScript> {

    protected M mainKScriptFile;

    public KContext() {
    }

    public M getScript() {
        return mainKScriptFile;
    }

    public void setScript(M mainKScriptFile) {
        this.mainKScriptFile = mainKScriptFile;
    }

    protected int random(int a, int b) {
        int max = Math.max(a, b),
                min = Math.min(a, b);

        if (max == min)
            return max;

        return min + ((new Random()).nextInt(max - min));
    }

    // Sleep functions
    protected void sleep(int a) {
        Time.sleep(a);
    }

    protected void sleep(int a, int b) {
        Time.sleep(random(a, b));
    }

    protected boolean sleepUntil(final Completable supplier, int timeout) {
        return Time.sleep(timeout, supplier);
    }

    protected boolean sleepUntil(final Completable supplier, int timeout, int sleepTime) {
        return Time.sleep(timeout, supplier, sleepTime);
    }

    // Debugging function
    protected void log(String s) {
        if (mainKScriptFile.isDebug())
            System.out.println(s);
    }

    /**
     * Give access to API Context methods through getter wrappers
     */

    public APIContext getContext() {
        return mainKScriptFile.getAPIContext();
    }

    protected Tile myPosition() {
        return mainKScriptFile.getAPIContext().localPlayer().getLocation();
    }

    protected ILocalPlayerAPI myPlayer() {
        return mainKScriptFile.getAPIContext().localPlayer();
    }

    protected IBankAPI getBank() {
        return mainKScriptFile.getAPIContext().bank();
    }

    protected IInventoryAPI getInventory() {
        return mainKScriptFile.getAPIContext().inventory();
    }

    protected IEquipmentAPI getEquipment() {
        return mainKScriptFile.getAPIContext().equipment();
    }

    protected ICombatAPI getCombat() {
        return mainKScriptFile.getAPIContext().combat();
    }

    protected IClientAPI getClient() {
        return mainKScriptFile.getAPIContext().client();
    }

    protected ICameraAPI getCamera() {
        return mainKScriptFile.getAPIContext().camera();
    }

    protected ICalculationsAPI getCalculations() {
        return mainKScriptFile.getAPIContext().calculations();
    }

    protected IVariablesAPI getVars() {
        return mainKScriptFile.getAPIContext().vars();
    }

    protected IHintArrowAPI getHintArrow() {
        return mainKScriptFile.getAPIContext().hintArrow();
    }

    protected IDialogueAPI getDialogues() {
        return mainKScriptFile.getAPIContext().dialogues();
    }

    protected IStoreAPI getStore() {
        return mainKScriptFile.getAPIContext().store();
    }

    protected ISkillsAPI getSkills() {
        return mainKScriptFile.getAPIContext().skills();
    }

    protected IScriptAPI getScriptSetting() {
        return mainKScriptFile.getAPIContext().script();
    }

    protected ITabsAPI getTabs() {
        return mainKScriptFile.getAPIContext().tabs();
    }

    protected IPlayersAPI getPLayers() {
        return mainKScriptFile.getAPIContext().players();
    }

    protected IPrayerAPI getPrayers() {
        return mainKScriptFile.getAPIContext().prayers();
    }

    protected IProjectilesAPI getProjectiles() {
        return mainKScriptFile.getAPIContext().projectiles();
    }

    protected IObjectsAPI getObjects() {
        return mainKScriptFile.getAPIContext().objects();
    }

    protected IKeyboardAPI getKeyboard() {
        return mainKScriptFile.getAPIContext().keyboard();
    }

    protected IMagicAPI getMagic() {
        return mainKScriptFile.getAPIContext().magic();
    }

    protected IMenuAPI getMenu() {
        return mainKScriptFile.getAPIContext().menu();
    }

    protected IMouseAPI getMouse() {
        return mainKScriptFile.getAPIContext().mouse();
    }

    protected INPCsAPI getNpcs() {
        return mainKScriptFile.getAPIContext().npcs();
    }

    protected IWalkingAPI getWalking() {
        return mainKScriptFile.getAPIContext().walking();
    }

    protected IWebWalkingAPI getWebWalking() {
        return mainKScriptFile.getAPIContext().webWalking();
    }

    protected IWorldAPI getWorlds() {
        return mainKScriptFile.getAPIContext().world();
    }

    protected IWidgetsAPI getWidgets() {
        return mainKScriptFile.getAPIContext().widgets();
    }

    protected IGameAPI getGame() {
        return mainKScriptFile.getAPIContext().game();
    }

    protected IGroundItemsAPI getGroundItems() {
        return mainKScriptFile.getAPIContext().groundItems();
    }

    protected IGrandExchangeAPI getGrandExchange() {
        return mainKScriptFile.getAPIContext().grandExchange();
    }

    protected IQuestAPI getQuests() {
        return mainKScriptFile.getAPIContext().quests();
    }

    protected ITradeAPI getTrade() {
        return mainKScriptFile.getAPIContext().trade();
    }

    protected IPricingAPI getPrice() {
        return mainKScriptFile.getAPIContext().pricing();
    }
}
