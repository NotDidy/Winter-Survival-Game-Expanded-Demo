package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.TeleportDevice;

/**
 * Add description
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class TeleportAction extends Action {

    private final Location destination;
    private final TeleportDevice device;
    private final String deviceType;


    public TeleportAction(TeleportDevice device, Location destination, String deviceType){
        this.device = device;
        this.destination = destination;
        this.deviceType = deviceType;
    }


    @Override
    public String execute(Actor actor, GameMap map) {
        device.teleport(actor, destination);
        return actor + " teleported using " + device + " to " + destination;
    }

    @Override
    public String menuDescription(Actor actor) {
        String mapName = destination.map().toString();
        return actor + " teleports to " + mapName + " using " + deviceType;
    }
}
