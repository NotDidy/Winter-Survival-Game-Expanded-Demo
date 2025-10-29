package game.items.crafting;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.CraftAction;

import java.util.Map;

/**
 * Represents Crafting Table that contains its own set of unique crafting recipes for items to be exchanged (crafted).
 * All Crafting Recipes can be executed when the Crafting Table is dropped (placed on the ground) and some recipes
 * can be crafted when the crafting table is in the actor's inventory depending on the recipe's rules.
 *
 * @author John Devadhas (34380507)
 * @version 1.3.0
 * @since 1.3.0
 */
public class CraftingTable extends Item {
    public static final String DEFAULT_NAME = "Crafting Table";
    public static final char DEFAULT_DISPLAY_CHAR = '⊞';
    public static final boolean DEFAULT_PORTABLE = true;

    /***
     * CraftingTable Constructor.
     */
    public CraftingTable() {
        super(DEFAULT_NAME, DEFAULT_DISPLAY_CHAR, DEFAULT_PORTABLE);
    }

    @Override
    public ActionList allowableActions(Location location) {
        ActionList actions = super.allowableActions(location);

        for (Recipe recipe : registry.getRecipes(false)) {
            actions.add(new CraftAction(recipe));
        }

        return actions;
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();

        for (Recipe recipe : registry.getRecipes(true)) {
            actions.add(new CraftAction(recipe));
        }

        return actions;
    }
}
