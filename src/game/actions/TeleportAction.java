package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.TeleportDevice;

/**
 * Represents an action that allows an {@link Actor} to teleport
 * from a source location to a destination using a {@link TeleportDevice}.
 * This action ensures the destination is valid and unoccupied before teleporting.
 *
 * @author Wern Zhong Lei
 * @version 1.8
 * @since 1.0
 */
public class TeleportAction extends Action {

    //source location of teleport
    private final Location source;
    //destination location of teleport
    private final Location destination;
    //teleport device used for teleportation
    private final TeleportDevice device;

    /**
     * Constructor to create a teleport action.
     *
     * @param source the source {@link Location}
     * @param destination the destination {@link Location}
     * @param device the {@link TeleportDevice} used to perform teleportation
     */
    public TeleportAction(Location source, Location destination, TeleportDevice device) {
        this.source = source;
        this.destination = destination;
        this.device = device;
    }

    /**
     * Executes the teleportation by moving the actor to the destination.
     * If the destination is blocked or occupied, attempts to find a nearby valid location.
     *
     * @param actor the {@link Actor} performing the action
     * @param map the {@link GameMap} where the teleportation occurs
     * @return a {@link String} message describing the result of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location target = destination;

        //ensure target is valid
        if (target.containsAnActor() || !target.canActorEnter(actor)) {
            for (Location neighbour : target.getExits().stream().map(e -> e.getDestination()).toList()) {
                if (!neighbour.containsAnActor() && neighbour.canActorEnter(actor)) {
                    target = neighbour;
                    break;
                }
            }
        }

        //if invalid
        if (target.containsAnActor() || !target.canActorEnter(actor)) {
            return actor + " cannot teleport because destination is blocked.";
        }

        //tp the actor according to the device used
        map.moveActor(actor, target);
        //post-tp behaviour (burning)
        device.onTeleport(actor, source, target);
        return actor + " teleports using " + device + " to " + target;
    }

    /**
     * Returns a string describing this teleport action for the game menu.
     *
     * @param actor the {@link Actor} performing the action
     * @return a {@link String} description of the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " teleports using " + device + " to " + destination;
    }
}
