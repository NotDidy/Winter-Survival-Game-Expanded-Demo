package game.items.crafting;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.items.Bow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SuperBowRecipe implements Recipe {
    private static final String DEF_SUPER_BOW_NAME = "Super Bow";
    private static final int DEF_SUPER_BOW_MIN_RANGE = 2;
    private static final int DEF_SUPER_BOW_MAX_RANGE = 10;
    private static final int DEF_SUPER_BOW_DAMAGE = 20;
    private final Map<Class<? extends Item>, Integer> ingredients;
    private final List<Item> itemsToRemove;
    private final boolean isPortableCraftable;


    public SuperBowRecipe(Map<Class<? extends Item>, Integer> ingredients, boolean isPortableCraftable) {
        this.ingredients = ingredients;
        this.itemsToRemove = new ArrayList<>();
        this.isPortableCraftable = isPortableCraftable;
    }

    @Override
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

    @Override
    public String craft(Actor actor) {
        if (!canCraft(actor)) {
            return actor + " does not have the required materials.";
        }
        for (Item item : this.itemsToRemove) {
            actor.removeItemFromInventory(item);
        }
        Item superBow = new Bow(DEF_SUPER_BOW_NAME, DEF_SUPER_BOW_MIN_RANGE, DEF_SUPER_BOW_MAX_RANGE, DEF_SUPER_BOW_DAMAGE);
        actor.addItemToInventory(superBow);

        return actor + " crafted a " + superBow + "!";
    }

    @Override
    public boolean isPortableCraftable(Actor actor) {
        return this.isPortableCraftable;
    }
}
