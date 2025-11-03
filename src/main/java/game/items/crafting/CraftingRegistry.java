package game.items.crafting;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import game.actions.CraftAction;

import java.util.*;

/**
 * CraftingRegistry class that stores and processes Crafting recipes
 * @author John Devadhas (34380507)
 * @version 1.3.0
 * @since 1.3.0
 */
public class CraftingRegistry {
    private final List<Recipe> recipes;

    /**
     * CraftingRegistry constructor.
     */
    public CraftingRegistry () {
        this.recipes = new ArrayList<>();
    }
    /**
     * Adds a recipe into the Recipe list.
     * @param recipe    The new recipe to be added
     */
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    /**
     * Retrieves a filtered list of recipes according to the recipe being craftable while being portable (on the go)
     * @param actor                 The Actor that is checking for recipes
     * @param isPortableCraftable   boolean of retrieving portable craftable recipes
     * @return                      the list of eligible recipes
     */
    public List<Recipe> getRecipes(Actor actor, boolean isPortableCraftable) {
        List<Recipe> recipesList = new ArrayList<>();
        for (Recipe recipe: this.recipes) {
            if (!isPortableCraftable)
                recipesList.add(recipe);
            else {
                if (recipe.isPortableCraftable(actor))
                    recipesList.add(recipe);
            }
        }
        return recipesList;
    }

    /**
     * Processes an ActionList of all CraftActions for this instance
     * @param actor                 The actor retrieving the CraftActions
     * @param isPortableCraftable   boolean of retrieving portable craftable recipes
     * @return                      An ActionList containing the CraftActions
     */
    public ActionList getCraftActions (Actor actor, boolean isPortableCraftable) {
        ActionList actions = new ActionList();
        for (Recipe recipe: this.getRecipes(actor, isPortableCraftable)) {
            if (recipe.canCraft(actor))
                actions.add(new CraftAction(recipe));
        }
        return actions;
    }
}