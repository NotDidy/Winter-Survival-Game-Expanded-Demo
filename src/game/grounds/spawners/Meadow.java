package game.grounds.spawners;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.behaviors.ConsumeBehaviour;
import game.creatures.Creature;
import game.statuses.SpawnStatus;

import java.util.*;
import java.util.function.Supplier;

/** Meadow (‘w’): every 7 turns, then 50% chance; spawns can consume ground items. */
public class Meadow extends Ground implements Spawner {
    private static final int PERIOD_TURNS = 7;
    private static final double SPAWN_CHANCE = 0.5;
    private static final int CONSUME_PRIORITY = 40;

    private final Set<Supplier<? extends Creature>> spawnables = new HashSet<>();

    public Meadow() {
        super('w', "Meadow");
        this.enableAbility(SpawnerAbilities.CAN_SPAWN);
        this.addStatus(new SpawnStatus(PERIOD_TURNS));
    }

    @Override public boolean addSpawnableActor(Supplier<? extends Creature> s){ return spawnables.add(s); }
    @Override public boolean removeSpawnableActor(Supplier<? extends Creature> s){ return spawnables.remove(s); }
    public Set<Supplier<? extends Creature>> getSpawnableActors(){ return new HashSet<>(spawnables); }

    @Override
    public void tick(Location location) {
        if (!this.hasAbility(SpawnerAbilities.CAN_SPAWN)) return;
        if (this.hasStatus(SpawnStatus.class)) return;
        try {
            spawnActor(location);
            this.addStatus(new SpawnStatus(PERIOD_TURNS));
        } catch (GameEngineException e) {
            new Display().println("Meadow spawn failed: " + e.getMessage());
        }
    }

    @Override
    public void spawnActor(Location location) throws GameEngineException {
        if (location.containsAnActor() || spawnables.isEmpty() || Math.random() >= SPAWN_CHANCE) return;

        var list = new ArrayList<>(spawnables);
        Creature c = list.get((int)(Math.random() * list.size())).get();
        c.addBehaviour(CONSUME_PRIORITY, new ConsumeBehaviour());

        location.map().addActor(c, location);
    }
}
