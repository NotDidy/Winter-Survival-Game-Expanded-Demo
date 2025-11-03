package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.crafting.Recipe;

/**
 * CraftAction class to execute crafting related actions
 * @author John Devadhas
 * @version 1.3.0
 * @since 1.3.0
 */
public class CraftAction extends Action {
    private final Recipe recipe;

    /**
     * CraftAction constructor.
     * @param recipe    The crafting recipe to execute
     */
    public CraftAction(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * CraftAction execution
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return  The result of the CraftAction
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return recipe.craft(actor);
    }

    /**
     * The menu description of the CraftAction
     * @param actor The actor performing the action.
     * @return  The display String for the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " crafts a " + recipe;
    }
}
