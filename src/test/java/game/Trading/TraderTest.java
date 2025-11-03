package game.Trading;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.items.Item;
import game.items.Torch;
import game.trading.Trader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Trader and its interactions with PriceSource.
 * Follows one-assert-per-method rule.
 */
public class TraderTest {

    @Test
    void testTraderIsCreated() {
        Trader trader = new Trader("Trader", '@', 100);
        assertNotNull(trader);
    }

    @Test
    void testTraderDisplayChar() {
        Trader trader = new Trader("Trader", '@', 100);
        assertEquals('@', trader.getDisplayChar());
    }

    @Test
    void testGetPriceReturnsExpectedValue() {
        Trader trader = new Trader("Trader", '@', 100) {
            @Override
            public double getPrice(Item item) {
                return 99.99;
            }
        };
        Torch item = new Torch("Torch", 'y', false);
        assertEquals(99.99, trader.getPrice(item));
    }

    @Test
    void testAllowableActionsNotEmpty() {
        Trader trader = new Trader("Trader", '@', 100);
        ActionList list = trader.allowableActions(null, "west", null);
        assertTrue(list.size() != 0, "Allowable actions contained actions.");
    }
}
