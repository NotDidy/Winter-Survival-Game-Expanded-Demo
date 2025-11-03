package game.trading;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.actions.TradeAction;


/**
 * Trader NPC with API-driven item prices.
 * @author Jeffrey Lam PO Yuen (35306750)
 * @version 1.3.0
 * @since 1.3.0
 */
public class Trader extends Actor {

    private final PriceSource priceSource;

    /**
     * Trader with the given attributes and a default
     *
     * @param name The name of trader
     * @param displayChar The character representing the trader on the map.
     * @param hitPoints The initial health points of the trader.
     */
    public Trader(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.priceSource = new CoinDeskPriceSource();
    }

    /**
     * Retrieves the current price for the specified item.
     *
     * @param item The item for which to fetch the price.
     * @return The current market price of the item.
     */
    public double getPrice(Item item) {
        return priceSource.getPrice(item.toString());
    }

    /**
     * The trader’s default turn behavior. In this case, the trader does nothing.
     *
     * @param actions collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do
     * interesting things in conjunction with Action.getNextAction()
     * @param map the map containing the Actor
     * @param display the I/O object to which messages may be written
     * @return The action to perform this turn.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return lastAction;
    }

    /**
     * Defines the list of actions available when another actor
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction String representing the direction of the other Actor
     * @param map current GameMap
     * @return A list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        actions.add(new TradeAction(this));
        return actions;
    }
}
