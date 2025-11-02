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
 * @version 1.1.0
 * @since 1.1.0
 */
public class Trader extends Actor {

    private final PriceSource priceSource;

    public Trader(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.priceSource = new CoinDeskPriceSource();
    }

    public double getPrice(Item item) {
        return priceSource.getPrice(item.toString());
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return lastAction;
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        actions.add(new TradeAction(this));
        return actions;
    }
}
