package game.trading;

/**
 * Interface for getting dynamic item prices.
 * @author Jeffrey Lam PO Yuen (35306750)
 * @version 1.3.0
 * @since 1.3.0
 */
public interface PriceSource {
    /**
     * Retrieves the current price of a given item.
     *
     * @param itemName the name of the item whose price is requested
     * @return the price of the item in game currency
     */
    double getPrice(String itemName);
}