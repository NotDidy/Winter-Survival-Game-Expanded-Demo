package game.items;

import edu.monash.fit2099.engine.items.Item;

/**
 * Represents a Stick object.
 * @author John Devadhas
 * @version 1.3.0
 * @since 1.3.0
 */
public class Stick extends Item {
    public static final String DEFAULT_NAME = "Stick";
    public static final char DEFAULT_DISPLAY_CHAR = '/';
    public static final boolean DEFAULT_PORTABLE = true;
    /***
     * Constructor.
     */
    public Stick() {
        super(DEFAULT_NAME, DEFAULT_DISPLAY_CHAR, DEFAULT_PORTABLE);
    }
}
