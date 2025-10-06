package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.Random;

/**
 * A teleportation circle that allows teleportation between maps
 * and also locally to position (1,1).
 *
 * Unlike TeleDoor, this does not burn surroundings when used.
 *
 * @author John
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
        if (teleportLocally) {
            // Local teleport always available
            return source.map().at(1, 1);
        }

        // Prefer explicit destination if set
        if (destinationLocation != null) {
            return destinationLocation;
        }
        // Otherwise fallback to the first destination in the linked list
        else if (!destinations.isEmpty()) {
            return destinations.get(0);
        }

        // Safety fallback: stay in place
        return source;
    }

    @Override
    public void onTeleport(Actor actor, Location source, Location destination) {
        burnSurroundings(source);

        if (random.nextDouble() < 0.5) {
            System.out.println("Teleportation malfunction! You were sent to a random location instead!");
            destination = getRandomValidLocation(source.map(), actor);
        }

        // Move the actor to the chosen destination
        destination.map().moveActor(actor, destination);
    }

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

    private void burnSurroundings(Location center) {
        for (Location nearby : center.getExits().stream().map(e -> e.getDestination()).toList()) {
            if (nearby.getGround().isBurnable()) {
                nearby.setGround(new FireGround());
            }
        }
    }
}