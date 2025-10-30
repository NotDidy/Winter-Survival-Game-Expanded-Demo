package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import game.actions.TradeAction;
import game.interfaces.Tradeable;
import game.library.GeminiAPIService;
import game.items.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A merchant who trades items using Gemini for dynamic dialogue.
 */
import game.interfaces.Tradeable;

public class Trader extends Actor implements Tradeable {
    private final List<Item> inventory = new ArrayList<>();
    private final GeminiAPIService api = new GeminiAPIService();
    private Tradeable partner;

    /**
     * The constructor of the Actor class.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the
     *                    display
     * @param hitPoints   the Actor's starting hit points
     */
    public Trader(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map){
        ActionList actions = new ActionList();

        if (otherActor != this){
            actions.add(new TradeAction(this, partner, api));
        }

        return actions;
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display){
        return lastAction;
    }
    // Implement Tradeable
    @Override
    public List<Item> getInventoryItems() { return inventory; }

    @Override
    public void removeItem(Item item) { inventory.remove(item); }

    @Override
    public void addItem(Item item) { inventory.add(item); }

    @Override
    public String getName() { return name; }
}


}



