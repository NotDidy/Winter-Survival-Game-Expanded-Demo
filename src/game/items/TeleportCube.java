package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.TeleportDevice;

import java.util.List;

public class TeleportCube extends Item implements TeleportDevice {


    /**
    *TeleportCube Constructor
    */
    public TeleportCube(){
        super("Teleport Cube", '□', true);
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
