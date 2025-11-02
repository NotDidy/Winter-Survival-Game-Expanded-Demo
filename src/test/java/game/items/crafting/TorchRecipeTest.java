package game.items.crafting;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.items.Stick;
import game.items.Torch;
import game.statuses.BurningStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test Suite for TorchRecipe crafting execution
 * @author John Devadhas (34380507)
 * @version 1.3.0
 * @since 1.3.0
 */
class TorchRecipeTest {
    private TorchRecipe torchRecipe;
    private Actor mockActor;
    private Item mockStick;
    private Item mockTorch;

    /**
     * Initial set up for all tests.
     */
    @BeforeEach
    public void setUp() {
        // Ingredient: 1 Wood
        Map<Class<? extends Item>, Integer> ingredients = new HashMap<>();
        ingredients.put(Stick.class, 1);
        torchRecipe = new TorchRecipe(ingredients);

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

        boolean canCraft = torchRecipe.canCraft(mockActor);
        assertFalse(canCraft, "Actor should not be able to craft without ingredients");
    }

    /**
     * Tests the condition of the ability to craft with the actor's inventory being null.
     */
    @Test
    public void testCanCraftWithNullInventory() {
        when(mockActor.getItemInventory()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> torchRecipe.canCraft(mockActor),
                "Null inventory should throw exception or be handled");
    }

    /**
     * Tests the condition of the ability to craft with the actor's inventory not having the required item to craft with.
     */
    @Test
    public void testCanCraftWithWrongItems() {
        // Actor has 1 Stick
        List<Item> inventory = Collections.singletonList(mockTorch);
        when(mockActor.getItemInventory()).thenReturn(inventory);

        boolean canCraft = torchRecipe.canCraft(mockActor);
        assertFalse(canCraft, "Actor should not be able to craft without the required ingredients.");
    }

    /**
     * Tests the edge case condition of the ability to craft with the actor's inventory having the required item to craft with.
     */
    @Test
    public void testCanCraft() {
        // Actor has 1 Stick
        List<Item> inventory = Collections.singletonList(new Stick());
        when(mockActor.getItemInventory()).thenReturn(inventory);

        boolean canCraft = torchRecipe.canCraft(mockActor);
        assertTrue(canCraft, "Actor should be able to craft when ingredients are available");
    }

    /**
     * Tests the edge case condition of the ability to craft with the actor's inventory having the required item to craft with along with other items.
     */
    @Test
    public void testCanCraftWithExtraItems() {
        List<Item> inventory = Arrays.asList(new Stick(), mockStick, mockTorch);
        when(mockActor.getItemInventory()).thenReturn(inventory);

        boolean canCraft = torchRecipe.canCraft(mockActor);
        assertTrue(canCraft, "Actor should be able to craft when ingredients are available");
    }

    /**
     * Tests the craft method with the actor not having the required Stick item
     */
    @Test
    public void testUnsuccessfulCraftWithIncorrectItems() {
        // Actor does not have the necessary stick item to craft
        when(mockActor.getItemInventory()).thenReturn(Arrays.asList(mockTorch));

        String result = torchRecipe.craft(mockActor);
        assertTrue(result.contains("does not have the required materials"),
                "Crafting should fail if ingredients are missing");
    }

    /**
     * Tests the craft method with the Actor having the required Stick Item.
     */
    @Test
    public void testSuccessfulCraft() {
        // Actor has all required items
        List<Item> inventory = new ArrayList<>(Arrays.asList(new Stick()));
        when(mockActor.getItemInventory()).thenReturn(inventory);

        // Mock behavior for add/remove
        doNothing().when(mockActor).removeItemFromInventory(any(Item.class));
        doNothing().when(mockActor).addItemToInventory(any(Item.class));

        String result = torchRecipe.craft(mockActor);

        // Verify stick was removed and a torch was added
        verify(mockActor, times(1)).removeItemFromInventory(any(Stick.class));
        verify(mockActor, times(1)).addItemToInventory(any(Torch.class));

        assertTrue(result.contains("crafted a"), "Successful craft should mention crafting a torch");
    }

    /**
     * Tests the itemsToRemove list that it is reset after every craft.
     */
    @Test
    public void testReusabilityAfterCraft() {
        when(mockActor.getItemInventory()).thenReturn(Arrays.asList(mockStick));
        torchRecipe.canCraft(mockActor); // populates internal list
        torchRecipe.craft(mockActor);    // clears internal list
        assertFalse(torchRecipe.canCraft(mockActor),
                "Recipe should not work after previous craft (itemsToRemove should be cleared)");
    }

    /**
     * Tests the Actor being able to craft the torch portably.
     * For the current implementation, the actor can hold the crafting table in their inventory and
     * craft the torch if the actor has burning status.
     */
    @Test
    public void testIsPortableCraftable() {
        when(mockActor.hasStatus(BurningStatus.class)).thenReturn(true);
        assertTrue(torchRecipe.isPortableCraftable(mockActor),
                "Should be portable craftable when actor has BurningStatus");

        when(mockActor.hasStatus(BurningStatus.class)).thenReturn(false);
        assertFalse(torchRecipe.isPortableCraftable(mockActor),
                "Should not be portable craftable without BurningStatus");
    }
}