package game.items.crafting;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.StringItem;
import game.items.Stick;

import java.util.*;

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
    private final CraftingRegistry craftingRegistry;

    /***
     * CraftingTable Constructor.
     */
    public CraftingTable() {
        super(DEFAULT_NAME, DEFAULT_DISPLAY_CHAR, DEFAULT_PORTABLE);
        this.craftingRegistry = new CraftingRegistry();

        Map<Class<? extends Item>, Integer> superBowIngredients = new HashMap<>();
        superBowIngredients.put(Stick.class, 3);
        superBowIngredients.put(StringItem.class, 3);
        craftingRegistry.addRecipe(new SuperBowRecipe(superBowIngredients, !DEFAULT_PORTABLE));

        Map<Class<? extends Item>, Integer> bedrollIngredients = new HashMap<>();
        bedrollIngredients.put(Stick.class, 5);
        craftingRegistry.addRecipe(new BedrollRecipe(bedrollIngredients, DEFAULT_PORTABLE));

        Map<Class<? extends Item>, Integer> torchIngredients = new HashMap<>();
        torchIngredients.put(Stick.class, 1);
        craftingRegistry.addRecipe(new TorchRecipe(torchIngredients));
    }

    @Override
    public ActionList allowableActions(Location location) {
        Actor actorAtLocation = location.getActor();
        ActionList actions = super.allowableActions(location);
        actions.add(craftingRegistry.getCraftActions(actorAtLocation, !DEFAULT_PORTABLE));
        return actions;
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();
        actions.add(craftingRegistry.getCraftActions(owner, DEFAULT_PORTABLE));
        return actions;
    }
}
