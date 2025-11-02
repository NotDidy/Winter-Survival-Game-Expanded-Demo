package game.spawning;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

import java.util.function.Supplier;

/** For each exit, drop the item with probability p (0..1). */
public class DropItemAtEachExitWithChance implements SpawnEffect {
    private final Supplier<Item> itemFactory;
    private final double probability;

    public DropItemAtEachExitWithChance(Supplier<Item> itemFactory, double probability) {
        this.itemFactory = itemFactory;
        this.probability = probability;
    }

    @Override
    public void apply(Location spawnerLocation, Actor spawned) {
        for (Exit e : spawnerLocation.getExits()) {
            if (Math.random() < probability) {
                e.getDestination().addItem(itemFactory.get());
            }
        }
    }
}
