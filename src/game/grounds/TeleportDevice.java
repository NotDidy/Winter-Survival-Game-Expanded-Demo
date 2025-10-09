package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract ground type that represents a teleportation device.
 * Teleport devices allow actors to teleport locally or across maps.
 * Subclasses define specific teleportation behaviors and destinations.
 *
 * <p>Examples include {@link TeleDoor} and {@link TeleCircle}.</p>
 *
 * @author Wern Zhong Lei
 * @version 1.2.3
 * @since 1.2.0
 */
public abstract class TeleportDevice extends Ground {

    //list of possible teleport destinations
    protected List<Location> destinations = new ArrayList<>();
    //boolean indicating if local tp (same map) is possible
    protected boolean teleportLocally = true;

    /**
     * Creates a teleport device with the given display character and name.
     *
     * @param displayChar the character representing this ground
     * @param name the name of this teleport device
     */
    public TeleportDevice(char displayChar, String name) {
        super(displayChar, name);
    }

    /**
     * Teleport devices cannot be burned.
     *
     * @return false always
     */
    @Override
    public boolean isBurnable() {
        return false;
    }

    /**
     * Adds a new teleport destination if not already registered.
     *
     * @param location the {@link Location} to add as a teleport destination
     */
    public void addDestination(Location location) {
        if (!destinations.contains(location)) {
            destinations.add(location);
        }
    }

    /**
     * Provides a list of allowable teleport actions for the actor at this location.
     * Includes both cross-map and local teleport options.
     *
     * @param actor the {@link Actor} interacting with this device
     * @param location the current {@link Location} of the device
     * @param direction the direction of the actor relative to the device
     * @return an {@link ActionList} containing all possible teleport actions
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();

        //cross map tp
        for (Location dest : destinations) {
            actions.add(new TeleportAction(location, dest, this));
        }

        //local map tp
        if (teleportLocally) {
            actions.add(new TeleportAction(location, location.map().at(1, 1), this));
        }
        return actions;
    }

    /**
     * Called after teleportation to perform any custom effects.
     * Default behavior: do nothing. Subclasses may override.
     *
     * @param actor the {@link Actor} who teleported
     * @param source the source {@link Location}
     * @param destination the destination {@link Location}
     */
    public abstract void onTeleport(Actor actor, Location source, Location destination);
}