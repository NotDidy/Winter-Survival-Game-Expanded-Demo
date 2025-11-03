package game.items.crafting;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * CraftingRegistry Class Test cases
 * @author John Devadhas (34380507)
 * @version 1.3.0
 * @since 1.3.0
 */
class CraftingRegistryTest {
    private CraftingRegistry craftingRegistry;
    private Actor mockActor;
    private Recipe mockPortableRecipe;
    private Recipe mockNonPortableRecipe;
    private Recipe mockUncraftableRecipe;

    /**
     * Initial set up for test cases.
     */
    @BeforeEach
    public void setUp() {
        craftingRegistry = new CraftingRegistry();
        mockActor = mock(Actor.class);
        mockPortableRecipe = mock(Recipe.class);
        mockNonPortableRecipe = mock(Recipe.class);
        mockUncraftableRecipe = mock(Recipe.class);
    }

    /**
     * Tests that addRecipe correctly adds recipes to the registry.
     */
    @Test
    public void testAddRecipe() {
        craftingRegistry.addRecipe(mockPortableRecipe);
        List<Recipe> recipes = craftingRegistry.getRecipes(mockActor, false);

        assertEquals(1, recipes.size(), "Registry should contain one recipe after adding.");
        assertTrue(recipes.contains(mockPortableRecipe), "Added recipe should exist in registry.");
    }

    /**
     * Tests getRecipes when portableCraftable = false (should return all recipes regardless of portable flag).
     */
    @Test
    public void testGetRecipesWhenPortableCraftableIsFalse() {
        craftingRegistry.addRecipe(mockPortableRecipe);
        craftingRegistry.addRecipe(mockNonPortableRecipe);

        List<Recipe> result = craftingRegistry.getRecipes(mockActor, false);
        assertEquals(2, result.size(), "Should return all recipes when portableCraftable = false");
    }

    /**
     * Tests getRecipes when portableCraftable = true (should only return recipes that are portable).
     */
    @Test
    public void testGetRecipesWhenPortableCraftableIsTrue() {
        when(mockPortableRecipe.isPortableCraftable(mockActor)).thenReturn(true);
        when(mockNonPortableRecipe.isPortableCraftable(mockActor)).thenReturn(false);

        craftingRegistry.addRecipe(mockPortableRecipe);
        craftingRegistry.addRecipe(mockNonPortableRecipe);

        List<Recipe> result = craftingRegistry.getRecipes(mockActor, true);

        assertEquals(1, result.size(), "Should return only portable recipes.");
        assertTrue(result.contains(mockPortableRecipe), "Portable recipe should be included.");
        assertFalse(result.contains(mockNonPortableRecipe), "Non-portable recipe should not be included.");
    }

    /**
     * Tests getRecipes when no recipes exist.
     */
    @Test
    public void testGetRecipesWhenEmpty() {
        List<Recipe> result = craftingRegistry.getRecipes(mockActor, false);
        assertTrue(result.isEmpty(), "Should return an empty list when no recipes exist.");
    }

    /**
     * Tests getCraftActions returning only craftable recipes.
     */
    @Test
    public void testGetCraftActionsWithCraftableRecipe() {
        when(mockPortableRecipe.isPortableCraftable(mockActor)).thenReturn(true);
        when(mockPortableRecipe.canCraft(mockActor)).thenReturn(true);

        craftingRegistry.addRecipe(mockPortableRecipe);

        ActionList actions = craftingRegistry.getCraftActions(mockActor, true);
        assertEquals(1, actions.size(), "Should contain one craft action for craftable recipe.");
    }

    /**
     * Tests getCraftActions returning no actions when recipe cannot be crafted.
     */
    @Test
    public void testGetCraftActionsWithUncraftableRecipe() {
        when(mockUncraftableRecipe.isPortableCraftable(mockActor)).thenReturn(true);
        when(mockUncraftableRecipe.canCraft(mockActor)).thenReturn(false);

        craftingRegistry.addRecipe(mockUncraftableRecipe);

        ActionList actions = craftingRegistry.getCraftActions(mockActor, true);
        assertEquals(0, actions.size(), "Should return no actions when recipe cannot be crafted.");
    }

    /**
     * Tests getCraftActions when portableCraftable is false (should include all craftable recipes).
     */
    @Test
    public void testGetCraftActionsNonPortableMode() {
        when(mockPortableRecipe.canCraft(mockActor)).thenReturn(true);
        when(mockNonPortableRecipe.canCraft(mockActor)).thenReturn(true);

        craftingRegistry.addRecipe(mockPortableRecipe);
        craftingRegistry.addRecipe(mockNonPortableRecipe);

        ActionList actions = craftingRegistry.getCraftActions(mockActor, false);

        assertEquals(2, actions.size(), "Should return all craftable recipes when portable flag is false.");
    }

    /**
     * Tests getCraftActions when there are no recipes registered.
     */
    @Test
    public void testGetCraftActionsWhenEmpty() {
        ActionList actions = craftingRegistry.getCraftActions(mockActor, false);
        assertEquals(0, actions.size(), "Should return empty ActionList when no recipes are registered.");
    }
}