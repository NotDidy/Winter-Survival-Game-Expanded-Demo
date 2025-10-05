package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import java.util.List;

/**
 * Add description
 * @author
 * @version 1.2.0
 * @since 1.2.0
 */
public interface TeleportDevice {
    void teleport(Actor actor, Location destination);

    List<Location> getDestinations();

    void addDestination(Location location);
}
