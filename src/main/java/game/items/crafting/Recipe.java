package game.items.crafting;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Represents a contract for classes that can be used as a Recipe
 * @author John Devadhas (34380507)
 * @version 1.3.0
 * @since 1.3.0
 */
public interface Recipe {

    /**
     * Checks if the Actor can craft using this Recipe.
     * @param actor     The Actor to check for eligibility.
     * @return          true if the Actor can craft.
     *                  false if the Actor cannot craft.
     */
    boolean canCraft(Actor actor);

    /**
     * Executing the crafting of this recipe
     * @param actor     The actor that is crafting
     * @return          The result of crafting
     */
    String craft(Actor actor);

    /**
     * Checks if this recipe can be crafted on the go (when Player is holding the crafting table)
     * @param actor     The Actor to check for eligibility.
     * @return          true if the recipe can be crafted on the go
     *                  false if the recipe cannot be crafted on the go.
     */
    boolean isPortableCraftable(Actor actor);
}
