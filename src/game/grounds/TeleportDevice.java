package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import java.util.List;

public interface TeleportDevice {
    void teleport(Actor actor, Location destination);

    List<Location> getDestinations();

    void addDestination(Location location);
}
