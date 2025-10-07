package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;
import game.grounds.TeleportDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a portable teleportation device that allows an {@link Actor} to
 * travel between different {@link GameMap}s.
 * <p>
 * The {@code TeleCube} can be used both when placed on the ground and when held
 * in an actor's inventory. It links two or more maps and provides teleportation
 * options to each linked map.
 * </p>
 * <p>
 * The cube occasionally malfunctions, teleporting the user to a random valid
 * location on the same map instead of the intended destination.
 * </p>
 *
 * @author Wern Zhong Lei
 * @version 1.4
 * @since 1.0
 */
public class TeleCube extends Item {

    //a list of map connected by teleCube
    private final List<GameMap> linkedMaps = new ArrayList<>();
    private final Random random = new Random();


    /**
     * Constructs a new {@code TeleCube} item.
     * The cube is portable and represented by the '□' character.
     */
    public TeleCube() {
        super("Teleport Cube", '□', true);
    }

    /**
     * Links two maps together, enabling teleportation between them.
     * Each map is added only once.
     *
     * @param map1 the first map to link
     * @param map2 the second map to link
     */
    public void linkMaps(GameMap map1, GameMap map2) {
        if (!linkedMaps.contains(map1)) linkedMaps.add(map1);
        if (!linkedMaps.contains(map2)) linkedMaps.add(map2);
    }

    /**
     * Handles teleportation when an actor uses the cube.
     * There is a 50% chance the teleportation will malfunction,
     * sending the actor to a random valid location on the same map.
     *
     * @param actor       the actor using the cube
     * @param source      the location where the teleportation starts
     * @param destination the intended destination
     */
    public void onTeleport(Actor actor, Location source, Location destination) {
        Location target;

        boolean malfunction = random.nextDouble() < 0.5;
        if (malfunction) {
            System.out.println("Teleportation malfunction! Explorer was sent to a random location instead!");
            target = getRandomValidLocation(source.map(), actor);

            //move actor ONLY IF malfunctioned
            target.map().moveActor(actor, target);
        }
        //System.out.println(actor + " teleported to (" + target.x() + "," + target.y() + ") on " + target.map());
    }

    /**
     * Selects a random valid location on the specified map that the actor can enter.
     *
     * @param map   the map to search within
     * @param actor the actor who will teleport
     * @return a valid random {@link Location} for the actor
     */
    private Location getRandomValidLocation(GameMap map, Actor actor) {
        int width = map.getXRange().max() + 1;
        int height = map.getYRange().max() + 1;

        Location randomLocation;
        do {
            int randX = random.nextInt(width);
            int randY = random.nextInt(height);
            randomLocation = map.at(randX, randY);
        } while (!randomLocation.canActorEnter(actor) || randomLocation.containsAnActor());

        return randomLocation;
    }

    /**
     * Returns the available teleportation actions when the cube is on the ground.
     * This allows nearby actors to teleport to any linked map.
     *
     * @param actor     the actor interacting with the cube
     * @param location  the cube's location
     * @param direction the direction relative to the actor
     * @return an {@link ActionList} containing possible teleport actions
     */
    //engine calls this when item on ground
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();

        if (linkedMaps.size() >= 2) {
            for (GameMap map : linkedMaps) {
                Location destination = map.at(1, 1);
                actions.add(new TeleportAction(location, destination, new CubeTeleportAdapter(this)));
            }
        }
        return actions;
    }

    /**
     * Returns the available teleportation actions when the cube is in an actor's inventory.
     * Allows teleportation from the actor's current position to linked map destinations.
     *
     * @param owner the actor holding the cube
     * @param map   the map the actor is currently on
     * @return an {@link ActionList} containing teleport options
     */
    //engine calls this when item in inventory
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();

        if (linkedMaps.size() >= 2) {
            Location source = map.locationOf(owner); // where owner currently stands
            for (GameMap gm : linkedMaps) {
                Location destination = gm.at(1, 1);
                actions.add(new TeleportAction(source, destination, new CubeTeleportAdapter(this)));
            }
        }
        return actions;
    }

    /**
     * Adapter class that allows {@link TeleportAction} to use the cube as a {@link TeleportDevice}.
     * It delegates teleportation logic to the cube's {@link #onTeleport(Actor, Location, Location)} method.
     */
    private static class CubeTeleportAdapter extends TeleportDevice {
        private final TeleCube cube;

        /**
         * Constructs an adapter linked to a specific {@link TeleCube}.
         *
         * @param cube the cube instance being adapted
         */
        CubeTeleportAdapter(TeleCube cube) {
            super('□', cube.toString());
            this.cube = cube;
        }

        /**
         * Delegates teleportation behavior to the cube.
         *
         * @param actor       the actor teleporting
         * @param source      the source location
         * @param destination the target destination
         */
        @Override
        public void onTeleport(Actor actor, Location source, Location destination) {
            cube.onTeleport(actor, source, destination);
        }
    }
}
