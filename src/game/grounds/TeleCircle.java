package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.List;
import java.util.Random;

/**
 * A teleportation circle that allows teleportation between maps
 * and also locally to position (1,1).
 *
 * Unlike TeleDoor, this does not burn surroundings when used.
 *
 * @author Wern Zhong Lei
 * @version 1.0
 * @since 1.2.0
 */
public class TeleCircle extends TeleportDevice {

    private GameMap destinationMap;
    private Location destinationLocation;
    private final Random random = new Random();

    public TeleCircle() {
        super('O', "Tele-Circle");
    }

    /**
     * Set the teleport destination for this circle.
     */
    public void setDestination(GameMap map, Location location) {
        this.destinationMap = map;
        this.destinationLocation = location;
    }

    public GameMap getDestinationMap() {
        return destinationMap;
    }

    /**
     * Determine where the teleport goes based on current location.
     */
    @Override
    public Location getDestinationLocation(Location source) {
        Location target = source.map().at(1, 1);

        // If someone is standing there, find a nearby empty location
        if (target.containsAnActor()) {
            for (Location neighbour : target.getExits().stream().map(e -> e.getDestination()).toList()) {
                if (!neighbour.containsAnActor()) {
                    return neighbour;
                }
            }
        }

        return target;
    }

    @Override
    public void onTeleport(Actor actor, Location source, Location destination) {
        burnRandomSurroundings(source);
    }

    private void burnRandomSurroundings(Location center) {
        List<Location> nearbyTiles = center.getExits().stream()
                .map(exit -> exit.getDestination())
                .toList();

        for (Location nearby : nearbyTiles) {
            if (nearby.getGround().isBurnable() && random.nextDouble() < 0.4) {
                nearby.setGround(new FireGround());
            }
        }
    }
}