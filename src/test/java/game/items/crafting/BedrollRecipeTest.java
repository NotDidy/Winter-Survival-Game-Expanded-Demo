package game.items.crafting;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.actors.PlayerAttributes;
import game.items.Bedroll;
import game.items.Stick;
import game.items.Torch;
import game.statuses.BurningStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * Test Suite for Bedroll crafting execution
 * @author John Devadhas (34380507)
 * @version 1.3.0
 * @since 1.3.0
 */
class BedrollRecipeTest {
    private BedrollRecipe bedrollRecipe;
    private Actor mockActor;
    private Item mockStick;
    private Item mockTorch;

    /**
     * Initial set up for all tests.
     */
    @BeforeEach
    public void setUp() {
        // Ingredient: 5 sticks
        Map<Class<? extends Item>, Integer> ingredients = new HashMap<>();
        ingredients.put(Stick.class, 5 );
        bedrollRecipe = new BedrollRecipe(ingredients, true);

        mockStick = mock(Stick.class);
        mockActor = mock(Actor.class);
        mockTorch = mock(Torch.class);
    }

    /**
     * Tests the condition of the ability to craft with no items in the actor's inventory.
     */
    @Test
    public void testCanCraftWithNoItems() {
        // Actor has empty inventory
        when(mockActor.getItemInventory()).thenReturn(Collections.emptyList());
        when(mockActor.hasStatistic(PlayerAttributes.WARMTH)).thenReturn(true);
        when(mockActor.hasStatistic(PlayerAttributes.HYDRATION)).thenReturn(true);
        when(mockActor.getAttribute(PlayerAttributes.WARMTH)).thenReturn(4);
        when(mockActor.getAttribute(PlayerAttributes.HYDRATION)).thenReturn(4);

        boolean canCraft = bedrollRecipe.canCraft(mockActor);
        assertFalse(canCraft, "Actor should not be able to craft without ingredients");
    }

    /**
     * Tests the condition of the ability to craft with the actor's inventory being null.
     */
    @Test
    public void testCanCraftWithNullInventory() {
        when(mockActor.getItemInventory()).thenReturn(null);
        when(mockActor.hasStatistic(PlayerAttributes.WARMTH)).thenReturn(true);
        when(mockActor.hasStatistic(PlayerAttributes.HYDRATION)).thenReturn(true);
        when(mockActor.getAttribute(PlayerAttributes.WARMTH)).thenReturn(4);
        when(mockActor.getAttribute(PlayerAttributes.HYDRATION)).thenReturn(4);
        assertThrows(NullPointerException.class, () -> bedrollRecipe.canCraft(mockActor),
                "Null inventory should throw exception or be handled");
    }

    /**
     * Tests the condition of the ability to craft with the actor's inventory not having the required items to craft with.
     */
    @Test
    public void testCanCraftWithWrongItems() {
        // Actor only has 4 sticks
        when(mockActor.hasStatistic(PlayerAttributes.WARMTH)).thenReturn(true);
        when(mockActor.hasStatistic(PlayerAttributes.HYDRATION)).thenReturn(true);
        when(mockActor.getAttribute(PlayerAttributes.WARMTH)).thenReturn(4);
        when(mockActor.getAttribute(PlayerAttributes.HYDRATION)).thenReturn(4);
        List<Item> inventory = Arrays.asList(mockTorch, new Stick(), new Stick(), new Stick(), new Stick());
        when(mockActor.getItemInventory()).thenReturn(inventory);

        boolean canCraft = bedrollRecipe.canCraft(mockActor);
        assertFalse(canCraft, "Actor should not be able to craft without the required ingredients.");
    }

    /**
     * Tests the edge case condition of the ability to craft with the actor's inventory having the required items to craft with
     * but cannot craft due to high warmth levels.
     */
    @Test
    public void testCanCraftWithHighWarmth() {
        // Actor has 5 Sticks and has reduced warmth and hydration
        when(mockActor.hasStatistic(PlayerAttributes.WARMTH)).thenReturn(true);
        when(mockActor.hasStatistic(PlayerAttributes.HYDRATION)).thenReturn(true);
        when(mockActor.getAttribute(PlayerAttributes.WARMTH)).thenReturn(5);
        when(mockActor.getAttribute(PlayerAttributes.HYDRATION)).thenReturn(4);
        List<Item> inventory = Arrays.asList(new Stick(), new Stick(), new Stick(), new Stick(), new Stick());
        when(mockActor.getItemInventory()).thenReturn(inventory);

        boolean canCraft = bedrollRecipe.canCraft(mockActor);
        assertTrue(canCraft, "Actor cannot craft bedroll with warmth higher than 5");
    }

    /**
     * Tests the edge case condition of the ability to craft with the actor's inventory having the required items to craft with
     * but cannot craft due to high hydration levels.
     */
    @Test
    public void testCanCraftWithHighHydration() {
        // Actor has 5 Sticks and has reduced warmth and hydration
        when(mockActor.hasStatistic(PlayerAttributes.WARMTH)).thenReturn(true);
        when(mockActor.hasStatistic(PlayerAttributes.HYDRATION)).thenReturn(true);
        when(mockActor.getAttribute(PlayerAttributes.WARMTH)).thenReturn(4);
        when(mockActor.getAttribute(PlayerAttributes.HYDRATION)).thenReturn(5);
        List<Item> inventory = Arrays.asList(new Stick(), new Stick(), new Stick(), new Stick(), new Stick());
        when(mockActor.getItemInventory()).thenReturn(inventory);

        boolean canCraft = bedrollRecipe.canCraft(mockActor);
        assertTrue(canCraft, "Actor cannot craft bedroll with hydration higher than 5");
    }

    /**
     * Tests the edge case condition of the ability to craft with the actor's inventory having the required items to craft with.
     */
    @Test
    public void testCanCraft() {
        // Actor has 5 Sticks and has reduced warmth and hydration
        when(mockActor.hasStatistic(PlayerAttributes.WARMTH)).thenReturn(true);
        when(mockActor.hasStatistic(PlayerAttributes.HYDRATION)).thenReturn(true);
        when(mockActor.getAttribute(PlayerAttributes.WARMTH)).thenReturn(4);
        when(mockActor.getAttribute(PlayerAttributes.HYDRATION)).thenReturn(4);
        List<Item> inventory = Arrays.asList(new Stick(), new Stick(), new Stick(), new Stick(), new Stick());
        when(mockActor.getItemInventory()).thenReturn(inventory);

        boolean canCraft = bedrollRecipe.canCraft(mockActor);
        assertTrue(canCraft, "Actor should be able to craft when ingredients are available");
    }

    /**
     * Tests the edge case condition of the ability to craft with the actor's inventory having the required item to craft with along with other items.
     */
    @Test
    public void testCanCraftWithExtraItems() {
        List<Item> inventory = Arrays.asList(new Stick(), new Stick(), new Stick(), new Stick(), new Stick(), mockTorch);
        when(mockActor.getItemInventory()).thenReturn(inventory);

        boolean canCraft = bedrollRecipe.canCraft(mockActor);
        assertTrue(canCraft, "Actor should be able to craft when ingredients are available");
    }

    /**
     * Tests the craft method with the actor not having the required Stick items
     */
    @Test
    public void testUnsuccessfulCraftWithIncorrectItems() {
        // Actor does not have the necessary stick item to craft
        when(mockActor.getItemInventory()).thenReturn(Arrays.asList(mockTorch));

        String result = bedrollRecipe.craft(mockActor);
        assertTrue(result.contains("does not have the required materials"),
                "Crafting should fail if ingredients are missing");
    }

    /**
     * Tests the craft method with the Actor having the required Stick Item.
     */
    @Test
    public void testSuccessfulCraft() {
        // Actor has all required items
        List<Item> inventory = new ArrayList<>(Arrays.asList(new Stick(), new Stick(), new Stick(), new Stick(), new Stick()));
        when(mockActor.getItemInventory()).thenReturn(inventory);

        // Mock behavior for add/remove
        doNothing().when(mockActor).removeItemFromInventory(any(Item.class));
        doNothing().when(mockActor).addItemToInventory(any(Item.class));

        String result = bedrollRecipe.craft(mockActor);

        // Verify sticks were removed and a bedroll was added
        verify(mockActor, times(5)).removeItemFromInventory(any(Stick.class));
        verify(mockActor, times(1)).addItemToInventory(any(Bedroll.class));

        assertTrue(result.contains("crafted a"), "Successful craft should mention crafting a bedroll");
    }

    /**
     * Tests the itemsToRemove list that it is reset after every craft.
     */
    @Test
    public void testReusabilityAfterCraft() {
        when(mockActor.getItemInventory()).thenReturn(Arrays.asList(new Stick()));
        bedrollRecipe.canCraft(mockActor); // populates internal list
        bedrollRecipe.craft(mockActor);    // clears internal list
        assertFalse(bedrollRecipe.canCraft(mockActor),
                "Recipe should not work after previous craft (itemsToRemove should be cleared)");
    }

    /**
     * Tests the Actor being able to craft the bedroll portably.
     * For the current implementation, the actor can craft portably
     */
    @Test
    public void testIsPortableCraftable() {
        
        assertTrue(bedrollRecipe.isPortableCraftable(mockActor),
                "Should be portable craftable");

    }
}