package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;

import java.util.ArrayList;
import java.util.List;

public abstract class TeleportDevice extends Ground {

    protected List<Location> destinations = new ArrayList<>();

    public TeleportDevice(char displayChar, String name) {
        super(displayChar, name);
    }

    public void addDestination(Location location) {
        if (!destinations.contains(location)) {
            destinations.add(location);
        }
    }

    public List<Location> getDestinations() {
        return new ArrayList<>(destinations);
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        for (Location dest : destinations) {
            actions.add(new TeleportAction(location, dest, this));
        }
        return actions;
    }

    public void onTeleport(Actor actor, Location source, Location destination) {
        // Default: do nothing
    }
}