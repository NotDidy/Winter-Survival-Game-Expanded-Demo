package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Drinkable;

/**
 * DrinkAction class to drink from a Drinkable object.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class DrinkAction extends Action {
    private Drinkable drinkable;

    /**
     * Parameterised constructor for DrinkAction.
     * @param drinkable     The drinkable object.
     */
    public DrinkAction (Drinkable drinkable) {
        this.drinkable = drinkable;
    }

    /**
     * Get the drinkable object for this action instance.
     * @return  the drinkable object.
     */
    public Drinkable getDrinkable() {
        return drinkable;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened (the result of the action being performed) that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return this.getDrinkable().drunkBy(actor);
    }

    /**
     * Describe what action will be performed if this Action is chosen in the menu.
     *
     * @param actor The actor performing the action.
     * @return the action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes " + this.getDrinkable();
    }
}
