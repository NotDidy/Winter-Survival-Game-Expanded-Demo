package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A teleportation door that connects two locations or maps.
 * When used, it teleports the actor and burns all tiles surrounding the destination.
 *
 * @author Wern Zhong Lei
 * @version 1.4
 * @since 1.0
 */
public class TeleDoor extends TeleportDevice{

    /**
     * Creates a TeleDoor represented by '#'.
     */
    public TeleDoor(){
        super('#', "Tele-Door");
    }

    /**
     * Called after teleportation to apply post-teleport effects.
     * In this case, it burns all tiles surrounding the destination.
     *
     * @param actor the {@link Actor} who teleported
     * @param source the source {@link Location}
     * @param destination the destination {@link Location}
     */
    @Override
    public void onTeleport(Actor actor, Location source, Location destination) {
        burnSurroundings(destination);
    }

    /**
     * Burns all nearby tiles around the given location.
     *
     * @param center the central {@link Location} around which burning occurs
     */
    private void burnSurroundings(Location center) {
        for (Location nearby : center.getExits().stream().map(e -> e.getDestination()).toList()) {
            if ((nearby.getGround().isBurnable())) {
                nearby.setGround(new FireGround());
            }
        }
    }
}


