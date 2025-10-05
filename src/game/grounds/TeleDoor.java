package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeleDoor extends Ground implements TeleportDevice{

    private final List<Location> destinations;

    public TeleDoor(){
        super('#', "Tele-Door");
        this.destinations = new ArrayList<>();
    }

    @Override
    public List<Location> getDestinations() {
        return new ArrayList<>(destinations);
    }

    @Override
    public void teleport(Actor actor, Location destination) {
        // Burn all surrounding locations at destination
        burnSurroundings(destination);

        // Move actor to destination
        destination.map().moveActor(actor, destination);
    }

    @Override
    public void addDestination(Location location) {
        if (!destinations.contains(location)) {
            destinations.add(location);
        }
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);

        //add teleport actions for each destination
        for (Location destination : destinations) {
            actions.add(new TeleportAction(this, destination, "door"));
        }
        return actions;
    }

    /**
     * Burn all surrounding locations (creates fire ground)
     * @param center The center location to burn surroundings of
     */
    private void burnSurroundings(Location center) {
        for (Location nearby : center.getNearbyLocations(1)) {
            if (nearby.getGround() != this) { // Don't burn other teleport doors
                nearby.setGround(new FireGround());
            }
        }
    }
}

