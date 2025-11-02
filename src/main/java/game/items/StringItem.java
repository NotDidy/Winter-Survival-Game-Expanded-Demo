package game.items;

import edu.monash.fit2099.engine.items.Item;

/**
 * Represents a String item object (not to be confused with the "" String
 */
public class StringItem extends Item {
    public static final String DEFAULT_NAME = "String";
    public static final char DEFAULT_DISPLAY_CHAR = '~';
    public static final boolean DEFAULT_PORTABLE = true;
    /***
     * Constructor.
     */
    public StringItem() {
        super(DEFAULT_NAME, DEFAULT_DISPLAY_CHAR, DEFAULT_PORTABLE);
    }
}
