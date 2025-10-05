package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeleportCircle extends Ground implements TeleportDevice{

    private final List<Location> destinations;
    private final Random random;

    public TeleportCircle(){
        super('O', "TeleportCircle");
        this.destinations = new ArrayList<>();
        this.random = new Random();
    }

    @Override
    public List<Location> getDestinations() {
        return new ArrayList<>(destinations);
    }

    @Override
    public void teleport(Actor actor, Location destination) {
        Location source = destination.map().locationOf(actor);

        //burn one random surrounding location around source
        burnRandomSurrounding(source);

        //move actor to new destination
        destination.map().moveActor(actor, destination);
    }

    @Override
    public void addDestination(Location location) {
        if (!destinations.contains(location)) {
            destinations.add(location);
        }
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction){
        ActionList actions = super.allowableActions(actor,location,direction);

        for (Location destination : destinations){
            actions.add(new TeleportAction(this, destination, "circle"));
        }
        return actions;
    }

    /**
     * Burn one random surrounding location
     * @param center The center location
     */
    private void burnRandomSurrounding(Location center){
        List<Location>surroundings = center.getNearbyLocations(1);
        if(!surroundings.isEmpty()){
            Location randomLocation = surroundings.get(random.nextInt(surroundings.size()));
            if (randomLocation.getGround() != this) {
                randomLocation.setGround (new FireGround());
            }
        }
    }
}
