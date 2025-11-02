package game.spawning;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/** Replaces ground at exactly one exit with the provided ground. */
public class GrowGroundAtOneExit implements SpawnEffect {
    private final Supplier<Ground> groundFactory;

    public GrowGroundAtOneExit(Supplier<Ground> groundFactory) {
        this.groundFactory = groundFactory;
    }

    @Override
    public void apply(Location spawnerLocation, Actor spawned) {
        List<Exit> exits = new ArrayList<>(spawnerLocation.getExits());
        if (exits.isEmpty()) return;
        Exit e = exits.get((int) (Math.random() * exits.size()));
        e.getDestination().setGround(groundFactory.get());
    }
}
