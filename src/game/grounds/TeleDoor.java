package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Add description
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class TeleDoor extends TeleportDevice{

    private GameMap destinationMap;
    private Location destinationLocation;

    public TeleDoor(){
        super('#', "Tele-Door");
    }

    /**
     * Sets the teleport destination for this door.
     */
    public void setDestination(GameMap map, Location location) {
        this.destinationMap = map;
        this.destinationLocation = location;
    }

    public GameMap getDestinationMap() {
        return destinationMap;
    }

    public Location getDestinationLocation() {
        return destinationLocation;
    }

    @Override
    public void onTeleport(Actor actor, Location source, Location destination) {
        burnSurroundings(destination);
    }

    /**
     * Burns all surrounding tiles around the teleport destination.
     */
    private void burnSurroundings(Location center) {
        for (Location nearby : center.getExits().stream().map(e -> e.getDestination()).toList()) {
            if (!(nearby.getGround() instanceof TeleportDevice)) {
                nearby.setGround(new FireGround());
            }
        }
    }
}


