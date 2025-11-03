package game.grounds.trees.behaviours;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.YewBerryTree;

import java.util.List;
import java.util.function.Supplier;

/**
 * Drops one item on the tree's tile if any actor is within a given radius.
 */
public class ProximityDropBehaviour implements TreeBehaviour {
    private final int radius;
    private final Supplier<Item> itemSupplier;

    public ProximityDropBehaviour(int radius, Supplier<Item> itemSupplier) {
        this.radius = Math.max(1, radius);
        this.itemSupplier = itemSupplier;
    }

    @Override
    public void tick(YewBerryTree tree, Location location) {
        List<Location> nearby = location.getNearbyLocations(radius);
        boolean someoneNearby = nearby.stream().anyMatch(loc -> loc.getActor() != null);
        if (someoneNearby) {
            location.addItem(itemSupplier.get());
        }
    }
}
