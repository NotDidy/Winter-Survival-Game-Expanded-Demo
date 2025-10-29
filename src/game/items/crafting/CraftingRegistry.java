package game.items.crafting;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import game.actions.CraftAction;

import java.util.*;

public class CraftingRegistry {
    private final List<Recipe> recipes = new ArrayList<>();

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public List<Recipe> getRecipes(boolean isPortableCraftable) {
        List<Recipe> recipesList = new ArrayList<>();
        for (Recipe recipe: this.recipes) {
            if (!isPortableCraftable)
                recipesList.add(recipe);
            else {
                if (recipe.isPortableCraftable())
                    recipesList.add(recipe);
            }
        }
        return recipesList;
    }

    public ActionList getCraftActions (Actor actor, boolean isPortableCraftable) {
        ActionList actions = new ActionList();
        for (Recipe recipe: this.getRecipes(isPortableCraftable))
            if (recipe.canCraft(actor))
                actions.add(new CraftAction(recipe));
        return actions;
    }
}