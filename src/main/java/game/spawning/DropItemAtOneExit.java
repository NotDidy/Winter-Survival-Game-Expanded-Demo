package game.spawning;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/** Drops exactly one item in a randomly chosen exit of the spawner tile. */
public class DropItemAtOneExit implements SpawnEffect {
    private final Supplier<Item> itemFactory;

    public DropItemAtOneExit(Supplier<Item> itemFactory) {
        this.itemFactory = itemFactory;
    }

    @Override
    public void apply(Location spawnerLocation, Actor spawned) {
        List<Exit> exits = new ArrayList<>(spawnerLocation.getExits());
        if (exits.isEmpty()) return;
        Exit e = exits.get((int) (Math.random() * exits.size()));
        e.getDestination().addItem(itemFactory.get());
    }
}
