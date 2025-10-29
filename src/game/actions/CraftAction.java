package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.crafting.Recipe;

public class CraftAction extends Action {
    private final Recipe recipe;

    public CraftAction(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return recipe.craft(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Craft " + recipe.getName();
    }
}
