package game.trading;

/**
 * Interface for getting dynamic item prices.
 * @author Jeffrey Lam PO Yuen (35306750)
 * @version 1.1.0
 * @since 1.1.0
 */
public interface PriceSource {
    double getPrice(String itemName);
}