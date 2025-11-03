package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.DrinkAction;
import game.actors.PlayerAttributes;

/**
 * Bottle item class representation.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.0.0
 */
public class Bottle extends Item implements Drinkable {
    private int drinkAmount;
    private static final int HYDRATION_AMOUNT = 4;
    public static final String DEFAULT_NAME = "Bottle";
    public static final char DEFAULT_DISPLAY_CHAR = 'o';
    public static final boolean DEFAULT_PORTABLE = true;

    /***
     * Constructor.
     */
    public Bottle() {
        super(DEFAULT_NAME, DEFAULT_DISPLAY_CHAR, DEFAULT_PORTABLE);
        this.drinkAmount = 5;
    }

    /**
     * Retrieves the drink amount of the bottle.
     * @return  The drink amount of the bottle.
     */
    public int getDrinkAmount() {
        return drinkAmount;
    }

    /**
     * Sets the drink amount of the bottle.
     * @param drinkAmount  The new drink amount of the bottle.
     */
    public void setDrinkAmount(int drinkAmount) {
        this.drinkAmount = drinkAmount;
    }

    /**
	 * List of allowable actions that the item can perform to its owner
	 * or to the current map while being carried by an actor
     * @param owner the actor that owns the item
	 * @param map the map where the actor is performing the action on
	 * @return an unmodifiable list of Actions
	 */
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);
        if (this.getDrinkAmount() > 0)
            actions.add(new DrinkAction(this));
        return actions;
    }

    /**
     * Processes an item drinking result by an actor.
     *
     * @param actor The actor that drank the item object.
     * @return A String object containing details of the result.
     */
    @Override
    public String drunkBy(Actor actor) {
        actor.modifyAttribute(PlayerAttributes.HYDRATION, ActorAttributeOperation.INCREASE, HYDRATION_AMOUNT);
        return actor + " drinks from the " + this + " and restores " + HYDRATION_AMOUNT + " hydration points.";
    }
}
