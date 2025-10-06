package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.TeleportDevice;

public class TeleportAction extends Action {

    private final Location source;
    private final Location destination;
    private final TeleportDevice device;

    public TeleportAction(Location source, Location destination, TeleportDevice device) {
        this.source = source;
        this.destination = destination;
        this.device = device;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        map.moveActor(actor, destination);
        device.onTeleport(actor, source, destination);
        return actor + " teleports to " + destination.x() + "," + destination.y();
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " teleports using " + device + " to (" + destination.x() + "," + destination.y() + ")";
    }
}
