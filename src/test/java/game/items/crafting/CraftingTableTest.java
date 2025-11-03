package game.items.crafting;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * CraftingTable Class Test cases
 * @author John Devadhas (34380507)
 * @version 1.3.0
 * @since 1.3.0
 */
class CraftingTableTest {
    private CraftingTable craftingTable;
    private CraftingRegistry mockRegistry;
    private Location mockLocation;
    private Actor mockActor;
    private GameMap mockMap;

    @BeforeEach
    public void setUp() {
        craftingTable = new CraftingTable();
        mockRegistry = mock(CraftingRegistry.class);
        mockLocation = mock(Location.class);
        mockActor = mock(Actor.class);
        mockMap = mock(GameMap.class);
    }

    /**
     * Tests that the CraftingTable initializes with the correct default name, display char, and portability.
     */
    @Test
    public void testDefaultProperties() {
        assertEquals("Crafting Table", craftingTable.toString(), "Default name should be Crafting Table");
        assertEquals('⊞', craftingTable.getDisplayChar(), "Default display character should be ⊞");
    }

    /**
     * Tests that the constructor automatically registers the expected default recipes.
     */
    @Test
    public void testDefaultRecipesRegistered() throws Exception {
        // Use reflection to access private craftingRegistry
        var field = CraftingTable.class.getDeclaredField("craftingRegistry");
        field.setAccessible(true);
        CraftingRegistry registry = (CraftingRegistry) field.get(craftingTable);

        List<Recipe> recipes = registry.getRecipes(mockActor, false);
        assertEquals(3, recipes.size(), "CraftingTable should register 3 default recipes on initialization");
        assertTrue(recipes.stream().anyMatch(r -> "Super Bow".equals(r.toString())), "Should contain SuperBowRecipe");
        assertTrue(recipes.stream().anyMatch(r -> "Bedroll".equals(r.toString())),"Should contain BedrollRecipe");
        assertTrue(recipes.stream().anyMatch(r -> "Torch".equals(r.toString())),"Should contain TorchRecipe");
    }

    /**
     * Tests that addRecipe correctly delegates to the craftingRegistry.
     */
    @Test
    public void testAddRecipeDelegation() throws Exception {
        Recipe mockRecipe = mock(Recipe.class);

        // Replace internal registry with mock
        var field = CraftingTable.class.getDeclaredField("craftingRegistry");
        field.setAccessible(true);
        field.set(craftingTable, mockRegistry);

        craftingTable.addRecipe(mockRecipe);

        verify(mockRegistry, times(1)).addRecipe(mockRecipe);
    }

    /**
     * Tests allowableActions(Location) adds craft actions retrieved from the registry.
     */
    @Test
    public void testAllowableActionsOnGround() throws Exception {
        ActionList mockActionList = new ActionList();
        when(mockLocation.getActor()).thenReturn(mockActor);
        when(mockRegistry.getCraftActions(mockActor, false)).thenReturn(mockActionList);

        // Inject mock registry
        var field = CraftingTable.class.getDeclaredField("craftingRegistry");
        field.setAccessible(true);
        field.set(craftingTable, mockRegistry);

        ActionList actions = craftingTable.allowableActions(mockLocation);

        assertNotNull(actions, "Allowable actions should not be null.");
        verify(mockRegistry, times(1)).getCraftActions(mockActor, false);
    }

    /**
     * Tests allowableActions(Actor, GameMap) adds craft actions for portable crafting.
     */
    @Test
    public void testAllowableActionsWhileHeld() throws Exception {
        ActionList mockActionList = new ActionList();
        when(mockRegistry.getCraftActions(mockActor, true)).thenReturn(mockActionList);

        // Inject mock registry
        var field = CraftingTable.class.getDeclaredField("craftingRegistry");
        field.setAccessible(true);
        field.set(craftingTable, mockRegistry);

        ActionList actions = craftingTable.allowableActions(mockActor, mockMap);

        assertNotNull(actions, "Allowable actions should not be null.");
        verify(mockRegistry, times(1)).getCraftActions(mockActor, true);
    }


    /**
     * Tests allowableActions(Actor, GameMap) when registry returns empty actions.
     */
    @Test
    public void testAllowableActionsEmptyCraftActions() throws Exception {
        when(mockRegistry.getCraftActions(mockActor, true)).thenReturn(new ActionList());

        var field = CraftingTable.class.getDeclaredField("craftingRegistry");
        field.setAccessible(true);
        field.set(craftingTable, mockRegistry);

        ActionList result = craftingTable.allowableActions(mockActor, mockMap);

        assertEquals(0, result.size(), "Should return empty ActionList when registry returns none.");
    }
}