package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;
import game.grounds.TeleportDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeleCube extends Item {

    private final List<GameMap> linkedMaps = new ArrayList<>();
    private final Random random = new Random();

    public TeleCube() {
        super("Teleport Cube", '□', true);
    }

    public void linkMaps(GameMap map1, GameMap map2) {
        if (!linkedMaps.contains(map1)) linkedMaps.add(map1);
        if (!linkedMaps.contains(map2)) linkedMaps.add(map2);
    }

    /**
     * Teleport behaviour (called by the adapter).
     * Malfunction sends the user to a random valid location on the source map.
     */
    public void onTeleport(Actor actor, Location source, Location destination) {
        Location target = destination;

        boolean malfunction = random.nextDouble() < 0.5;
        if (malfunction) {
            System.out.println("Teleportation malfunction! Explorer was sent to a random location instead!");
            target = getRandomValidLocation(source.map(), actor);

            // Move actor ONLY IF malfunctioned
            target.map().moveActor(actor, target);
        }
        //System.out.println(actor + " teleported to (" + target.x() + "," + target.y() + ") on " + target.map());
    }

    private Location getRandomValidLocation(GameMap map, Actor actor) {
        int width = map.getXRange().max() + 1;
        int height = map.getYRange().max() + 1;

        Location randomLocation;
        do {
            int randX = random.nextInt(width);
            int randY = random.nextInt(height);
            randomLocation = map.at(randX, randY);
        } while (!randomLocation.canActorEnter(actor) || randomLocation.containsAnActor());

        return randomLocation;
    }

    /* ---------------------------
       ITEM ON GROUND: engine calls this
       --------------------------- */

    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();

        if (linkedMaps.size() >= 2) {
            for (GameMap map : linkedMaps) {
                Location destination = map.at(1, 1);
                // source is the location on which the cube currently sits
                actions.add(new TeleportAction(location, destination, new CubeTeleportAdapter(this)));
            }
        }
        return actions;
    }

    /* ---------------------------
       ITEM IN INVENTORY: engine calls this
       Signature must match Item.allowableActions(Actor, GameMap)
       --------------------------- */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();

        if (linkedMaps.size() >= 2) {
            Location source = map.locationOf(owner); // where owner currently stands
            for (GameMap gm : linkedMaps) {
                Location destination = gm.at(1, 1);
                actions.add(new TeleportAction(source, destination, new CubeTeleportAdapter(this)));
            }
        }
        return actions;
    }

    /**
     * Adapter so TeleportAction (which expects a TeleportDevice) will call this cube's onTeleport.
     * Lightweight: only implements the hooks TeleportAction uses.
     */
    private static class CubeTeleportAdapter extends TeleportDevice {
        private final TeleCube cube;

        CubeTeleportAdapter(TeleCube cube) {
            super('□', cube.toString());
            this.cube = cube;
        }

        @Override
        public Location getDestinationLocation(Location source) {
            return source; //not used by TeleportAction execution path, but required by abstract
        }

        @Override
        public void onTeleport(Actor actor, Location source, Location destination) {
            cube.onTeleport(actor, source, destination);
        }
    }
}
