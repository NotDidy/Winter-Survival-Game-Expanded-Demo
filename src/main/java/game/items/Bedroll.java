package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.SleepAction;

/**
 * Bedroll item class representation.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.0.0
 */
public class Bedroll extends Item implements Sleepable {

    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public Bedroll(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
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
