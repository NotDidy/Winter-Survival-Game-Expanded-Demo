package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;
import game.grounds.FireGround;
import game.grounds.TeleportDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Add description
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class TeleportCube extends Item implements TeleportDevice {
    private final List<Location> destinations;
    private final Random random;
    private static final double MALFUNCTION_CHANCE = 0.5;

    /**
    *TeleportCube Constructor
    */
    public TeleportCube(){
        super("Teleport Cube", '□', true);
        this.destinations = new ArrayList<>();
        this.random = new Random();
    }

    @Override
    public List<Location> getDestinations() {
        return new ArrayList<>(destinations);
    }

    @Override
    public void teleport(Actor actor, Location intendedDestination) {
        Location actualDestination = intendedDestination;
        GameMap currentMap = intendedDestination.map();

        if (random.nextDouble() < MALFUNCTION_CHANCE){
            actualDestination = getRandomLocation(currentMap);
        }

        burnSurroundings (actualDestination);
        currentMap.moveActor(actor, actualDestination);
    }

    @Override
    public void addDestination(Location location) {
        if (!destinations.contains(location)) {
            destinations.add(location);
        }
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);

        //add teleport actions for each destination (only when in inventory)
        for (Location destination : destinations) {
            actions.add(new TeleportAction(this, destination, "cube"));
        }
        return actions;
    }

    /**
     * Get a random location within the map
     * @param map The game map
     * @return A random location
     */
    private Location getRandomLocation(GameMap map) {
        int x = random.nextInt(map.getXRange().max());
        int y = random.nextInt(map.getYRange().max());
        return map.at(x, y);
    }

    /**
     * Burn all surrounding locations
     * @param center The center location
     */
    private void burnSurroundings(Location center) {
        for (Exit exits : center.getExits()) {
            exits.getDestination().setGround(new FireGround(3));
        }
    }
}

