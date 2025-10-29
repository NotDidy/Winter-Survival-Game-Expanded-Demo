package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.SleepAction;

/**
 * Bedroll item class representation.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.0.0
 */
public class Bedroll extends Item implements Sleepable {
    public static final String DEFAULT_NAME = "Bedroll";
    public static final char DEFAULT_DISPLAY_CHAR = '=';
    public static final boolean DEFAULT_PORTABLE = true;


    /***
     * Bedroll Constructor.
     */
    public Bedroll() {
        super(DEFAULT_NAME, DEFAULT_DISPLAY_CHAR, DEFAULT_PORTABLE);
    }

    /**
     * Processes actions that can be performed when this bedroll is dropped.
     *
     * @param location the location of the ground on which the item lies.
     * @return a list of actions that can be performed with this bedroll.
     */
    public ActionList allowableActions(Location location) {
        ActionList actions = super.allowableActions(location);
        actions.add(new SleepAction(this));
        return actions;
    }
}
