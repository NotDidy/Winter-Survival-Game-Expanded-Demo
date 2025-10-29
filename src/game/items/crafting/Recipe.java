package game.items.crafting;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents Crafting Recipes.
 *
 * @author John Devadhas
 * @version 1.3.0
 * @since 1.3.0
 */
public abstract class Recipe {
    private final String name;
    private final Map<Class<? extends Item>, Integer> ingredients;
    private final List<Item> itemsToRemove;
    private final Item craftedItem;
    private final boolean isPortableCraftable;

    public Recipe(String name, Map<Class<? extends Item>, Integer> ingredients, Item craftedItem, boolean isPortableCraftable) {
        this.name = name;
        this.ingredients = ingredients;
        this.itemsToRemove = new ArrayList<>();
        this.craftedItem = craftedItem;
        this.isPortableCraftable = isPortableCraftable;
    }

    public String getName() {
        return name;
    }

    public boolean isPortableCraftable() {
        return isPortableCraftable;
    }

    public Item getCraftedItem() {
        return craftedItem;
    }

    public boolean canCraft(Actor actor) {
        this.itemsToRemove.clear();

        // Saves the actor's item inventory in a temporary list
        Map<Class<? extends Item>, List<Item>> grouped = actor.getItemInventory().stream()
                .collect(Collectors.groupingBy(Item::getClass));
        
        // Iterates over this recipe's ingredients to check if the Actor has least the same amount of items for each item.
        for (Map.Entry<Class<? extends Item>, Integer> entry : ingredients.entrySet()) {
            List<Item> availableItems = grouped.get(entry.getKey());
            if (availableItems == null || availableItems.size() < entry.getValue()) {
                return false;
            }
            // Add the exact number of instances that will be consumed
            this.itemsToRemove.addAll(availableItems.subList(0, entry.getValue()));
        }
        return true;
    }

    public String craft(Actor actor) {
        if (!canCraft(actor)) {
            return "You don’t have the required materials!";
        }
        for (Item item : this.itemsToRemove) {
            actor.removeItemFromInventory(item);
        }
        actor.addItemToInventory(craftedItem);

        return "You crafted a " + craftedItem + "!";
    }
}

