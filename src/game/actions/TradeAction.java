package game.actions;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.trading.Trader;

import java.util.List;

/**
 * Lets the player view and trade items with the Trader.
 * @author Jeffrey Lam PO Yuen (35306750)
 * @version 1.1.0
 * @since 1.1.0
 */
public class TradeAction extends Action {

    private final Trader trader;

    /**
     * Parameterised constructor
     * @param trader the trader object
     */
    public TradeAction(Trader trader) {
        this.trader = trader;
    }

    /**
     * Perform the Action.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened with the item and its price
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        StringBuilder sb = new StringBuilder(trader + " offers the following deals:\n");
        List<Item> items = actor.getItemInventory();

        for (Item item : items) {
            double price = trader.getPrice(item);
            sb.append(String.format(" - %s: $%.2f\n", item.toString(), price));
        }

        return sb.toString();
    }

    /**
     * Describe what action will be performed if this Action is chosen in the menu.
     *
     * @param actor The actor performing the action.
     * @return the action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " trades with " + trader;
    }
}