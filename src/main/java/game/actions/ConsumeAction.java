package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Consumable;

/**
 * Class representing an action to consume an item (consumable).
 *
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class ConsumeAction extends Action {
    private final Consumable consumable;

    /**
     * Parameterised constructor for ConsumeAction.
     * @param consumable    The consumable that can be consumed.
     */
    public ConsumeAction(Consumable consumable) {
        this.consumable = consumable;
    }

    /**
     * Perform the ConsumeAction and process.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return  The result of consumption
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = consumable.consumedBy(actor);
        if (actor.isConscious())
            return result;
        return result + actor.unconscious(map);
    }

    /**
     * Describe what action will be performed if this Action is chosen in the menu.
     *
     * @param actor The actor performing the action.
     * @return the action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes " + consumable;
    }

}
