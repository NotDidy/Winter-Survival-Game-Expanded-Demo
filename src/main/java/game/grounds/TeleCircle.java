package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.List;
import java.util.Random;

/**
 * Represents a teleportation circle that allows actors to teleport both
 * within the same map and between maps.
 * <p>
 * The {@code TeleCircle} functions similarly to {@link TeleDoor}, but
 * without consuming or damaging surrounding tiles when used. When activated,
 * it teleports the actor to a defined destination or to position (1,1) by default.
 * </p>
 *
 * @author Wern Zhong Lei
 * @version 1.4
 * @since 1.0
 */
public class TeleCircle extends TeleportDevice {
    private int fireGroundTicks;
    private static final int DEFAULT_FIREGROUND_TICKS = 4;
    private final Random random = new Random();

    /**
     * Constructs a new {@code TeleCircle} with display character 'O'.
     */
    public TeleCircle() {
        super('O', "Tele-Circle");
        this.fireGroundTicks = DEFAULT_FIREGROUND_TICKS; 
    }

    /**
     * Executes teleportation behavior when used by an actor.+
     * The circle lightly burns a random nearby tile when activated.
     *
     * @param actor       the actor being teleported
     * @param source      the origin location
     * @param destination the destination location
     */
    @Override
    public void onTeleport(Actor actor, Location source, Location destination) {
        burnRandomSurroundings(source);
    }

    /**
     * Burns one random nearby tile around the given location.
     * Exactly one random surrounding tile will be set on fire if it is burnable.
     *
     * @param center the central {@link Location} around which burning occurs
     */
    private void burnRandomSurroundings(Location center) {
        List<Location> nearbyTiles = center.getExits().stream()
                .map(exit -> exit.getDestination())
                .filter(loc -> loc.getGround().asCapability(TeleportDevice.class).isEmpty())
                .toList();

        if (!nearbyTiles.isEmpty()) {
            Location randomTile = nearbyTiles.get(random.nextInt(nearbyTiles.size()));
            randomTile.setGround(new FireGround(this.fireGroundTicks));
        }
    }
}
