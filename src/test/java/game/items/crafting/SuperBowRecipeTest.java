package game.items.crafting;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.items.Bow;
import game.items.Stick;
import game.items.StringItem;
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
 * Test Suite for Super Bow crafting execution
 * @author John Devadhas (34380507)
 * @version 1.3.0
 * @since 1.3.0
 */
class SuperBowRecipeTest {
    private SuperBowRecipe superBowRecipe;
    private Actor mockActor;
    private Item mockStick;
    private Item mockTorch;
    private Item mockBow;
    private Item mockStringItem;

    /**
     * Initial set up for all tests.
     */
    @BeforeEach
    public void setUp() {
        // Ingredient: 3 Sticks and 3 StringItems
        Map<Class<? extends Item>, Integer> ingredients = new HashMap<>();
        ingredients.put(Stick.class, 3);
        ingredients.put(StringItem.class, 3);
        superBowRecipe = new SuperBowRecipe(ingredients, false);

        mockStick = mock(Stick.class);
        mockActor = mock(Actor.class);
        mockTorch = mock(Torch.class);
        mockStringItem = mock(StringItem.class);
        mockBow = mock(Bow.class);
    }

    /**
     * Tests the condition of the ability to craft with no items in the actor's inventory.
     */
    @Test
    public void testCanCraftWithNoItems() {
        // Actor has empty inventory
        when(mockActor.getItemInventory()).thenReturn(Collections.emptyList());

        boolean canCraft = superBowRecipe.canCraft(mockActor);
        assertFalse(canCraft, "Actor should not be able to craft without ingredients");
    }

    /**
     * Tests the condition of the ability to craft with the actor's inventory being null.
     */
    @Test
    public void testCanCraftWithNullInventory() {
        when(mockActor.getItemInventory()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> superBowRecipe.canCraft(mockActor),
                "Null inventory should throw exception or be handled");
    }

    /**
     * Tests the condition of the ability to craft with the actor's inventory not having the required items to craft with.
     * In this scenario the inventory is lacking 1 more StringItem needed
     */
    @Test
    public void testCanCraftWithWrongItems() {
        // Actor has 1 Stick
        List<Item> inventory = Arrays.asList(mockTorch, new Stick(), new Stick(), new Stick(), new StringItem(), new StringItem());
        when(mockActor.getItemInventory()).thenReturn(inventory);

        boolean canCraft = superBowRecipe.canCraft(mockActor);
        assertFalse(canCraft, "Actor should not be able to craft without the required ingredients.");
    }

    /**
     * Tests the edge case condition of the ability to craft with the actor's inventory having the required item to craft with.
     */
    @Test
    public void testCanCraft() {
        // Actor has 1 Stick
        List<Item> inventory = Arrays.asList(new Stick(), new Stick(), new Stick(), new StringItem(), new StringItem(), new StringItem());
        when(mockActor.getItemInventory()).thenReturn(inventory);

        boolean canCraft = superBowRecipe.canCraft(mockActor);
        assertTrue(canCraft, "Actor should be able to craft when ingredients are available");
    }

    /**
     * Tests the edge case condition of the ability to craft with the actor's inventory having the required item to craft with along with other items.
     */
    @Test
    public void testCanCraftWithExtraItems() {
        List<Item> inventory = Arrays.asList(new Stick(), new Stick(), new Stick(), new StringItem(), new StringItem(), new StringItem(), mockStick, mockTorch);
        when(mockActor.getItemInventory()).thenReturn(inventory);

        boolean canCraft = superBowRecipe.canCraft(mockActor);
        assertTrue(canCraft, "Actor should be able to craft when ingredients are available");
    }

    /**
     * Tests the craft method with the actor not having the required items
     */
    @Test
    public void testUnsuccessfulCraftWithIncorrectItems() {
        // Actor does not have the necessary items to craft
        when(mockActor.getItemInventory()).thenReturn(Arrays.asList(mockTorch));

        String result = superBowRecipe.craft(mockActor);
        assertTrue(result.contains("does not have the required materials"),
                "Crafting should fail if ingredients are missing");
    }

    /**
     * Tests the craft method with the Actor having the required Stick Item.
     */
    @Test
    public void testSuccessfulCraft() {
        // Actor has all required items
        List<Item> inventory = new ArrayList<>(Arrays.asList(new Stick(), new Stick(), new Stick(), new StringItem(), new StringItem(), new StringItem()));
        when(mockActor.getItemInventory()).thenReturn(inventory);

        // Mock behavior for add/remove
        doNothing().when(mockActor).removeItemFromInventory(any(Item.class));
        doNothing().when(mockActor).addItemToInventory(any(Item.class));

        String result = superBowRecipe.craft(mockActor);

        // Verify stick was removed and a torch was added
        verify(mockActor, times(3)).removeItemFromInventory(any(Stick.class));
        verify(mockActor, times(3)).removeItemFromInventory(any(StringItem.class));
        verify(mockActor, times(1)).addItemToInventory(any(Bow.class));

        assertTrue(result.contains("crafted a"), "Successful craft should mention crafting a torch");
    }

    /**
     * Tests the itemsToRemove list that it is reset after every craft.
     */
    @Test
    public void testReusabilityAfterCraft() {
        when(mockActor.getItemInventory()).thenReturn(Arrays.asList(mockStick));
        superBowRecipe.canCraft(mockActor); // populates internal list
        superBowRecipe.craft(mockActor);    // clears internal list
        assertFalse(superBowRecipe.canCraft(mockActor),
                "Recipe should not work after previous craft (itemsToRemove should be cleared)");
    }

    /**
     * Tests the Actor being able to craft the super bow portably.
     * For the current implementation, the actor cannot craft the super bow portably.
     */
    @Test
    public void testIsPortableCraftable() {
        assertFalse(superBowRecipe.isPortableCraftable(mockActor),
                "SuperBow can not be crafted portably");

    }
}