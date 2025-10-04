package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

import java.util.List;

public class TeleportCircle extends Ground implements TeleportDevice{
    public TeleportCircle(){
        super('O', "TeleportCircle");
    }

    @Override
    public List<Location> getDestinations() {
        return List.of();
    }

    @Override
    public void teleport(Actor actor, Location destination) {

    }

    @Override
    public void addDestination(Location location) {

    }
}
