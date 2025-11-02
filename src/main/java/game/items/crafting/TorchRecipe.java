package game.items.crafting;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.items.Torch;
import game.statuses.BurningStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TorchRecipe implements Recipe {
    private static final String TO_STRING = "Torch";
    private final Map<Class<? extends Item>, Integer> ingredients;
    private final List<Item> itemsToRemove;

    public TorchRecipe(Map<Class<? extends Item>, Integer> ingredients) {
        this.ingredients = ingredients;
        this.itemsToRemove = new ArrayList<>();
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
            int zeroIndex = 0;
            this.itemsToRemove.addAll(availableItems.subList(zeroIndex, entry.getValue()));
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
        Item torch = new Torch(Torch.DEFAULT_NAME, Torch.DEFAULT_DISPLAY_CHAR, Torch.DEFAULT_PORTABLE);
        actor.addItemToInventory(torch);
        this.itemsToRemove.clear();

        return actor + " crafted a " + torch + "!";
    }

    @Override
    public boolean isPortableCraftable(Actor actor) {
        return actor.hasStatus(BurningStatus.class);
    }

    /**
     * ToString method
     * @return the String representation of this recipe.
     */
    @Override
    public String toString() {
        return TO_STRING;
    }
}
