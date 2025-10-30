package game.interfaces;

import edu.monash.fit2099.engine.items.Item;
import java.util.List;

/**
 * Represents any actor capable of trading items.
 */
public interface Tradeable {
    List<Item> getInventoryItems();

    void removeItem(Item item);

    void addItem(Item item);

    String getName();
}
