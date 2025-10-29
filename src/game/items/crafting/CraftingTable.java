package game.items.crafting;

import edu.monash.fit2099.engine.items.Item;

/**
 * Represents Crafting Table that contains its own set of unique crafting recipes for items to be exchanged (crafted)
 *
 * @author John Devadhas (34380507)
 * @version 1.3.0
 * @since 1.3.0
 */
public class CraftingTable extends Item {
    public static final String DEFAULT_NAME = "Crafting Table";
    public static final char DEFAULT_DISPLAY_CHAR = '⊞';
    public static final boolean DEFAULT_PORTABLE = true;

    /***
     * CraftingTable Constructor.
     */
    public CraftingTable() {
        super(name, displayChar, portable);
    }
}
