package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;
import game.items.Apple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A teleportation door that connects two locations or maps.
 * When used, it teleports the actor and burns all tiles surrounding the destination.
 * @author John Devadhas (34380507), Wern Zhong Lei
 * @version 1.2.0
 * @since 1.2.0
 */
public class TeleDoor extends TeleportDevice{
    private int fireGroundTicks;
    private static final int DEFAULT_FIREGROUND_TICKS = 3;
    private static final int BURN_RADIUS = 1;

    /**
     * Creates a TeleDoor represented by '#'.
     */
    public TeleDoor(){
        super('#', "Tele-Door");
        this.destinations = new ArrayList<>();
        this.fireGroundTicks = DEFAULT_FIREGROUND_TICKS;
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
        for (Location nearby : center.getNearbyLocations(BURN_RADIUS)) {
            if ((nearby.getGround().isBurnable())) {
                nearby.setGround(new FireGround(this.fireGroundTicks));
            }
        }
//        for (Location nearby : center.getExits().stream().map(e -> e.getDestination()).toList()) {
//            if ((nearby.getGround().isBurnable())) {
//                nearby.setGround(new FireGround(this.fireGroundTicks));
//            }
//        }
    }
}

