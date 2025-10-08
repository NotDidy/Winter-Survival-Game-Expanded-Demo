package game.grounds.spawners;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.creatures.Creature;
import game.statuses.SpawnStatus;

import java.util.*;
import java.util.function.Supplier;

/** Cave (‘C’): spawns every 5 turns. */
public class Cave extends Ground implements Spawner {
    private static final int PERIOD_TURNS = 5;

    private final Set<Supplier<? extends Creature>> spawnables = new HashSet<>();

    public Cave() {
        super('C', "Cave");
        this.enableAbility(SpawnerAbilities.CAN_SPAWN);
        this.addStatus(new SpawnStatus(PERIOD_TURNS));
    }

    @Override public boolean addSpawnableActor(Supplier<? extends Creature> s){ return spawnables.add(s); }
    @Override public boolean removeSpawnableActor(Supplier<? extends Creature> s){ return spawnables.remove(s); }
    public Set<Supplier<? extends Creature>> getSpawnableActors(){ return new HashSet<>(spawnables); }

    @Override
    public void tick(Location location) {
        if (!this.hasAbility(SpawnerAbilities.CAN_SPAWN)) return;
        if (this.hasStatus(SpawnStatus.class)) return;           // still cooling down
        try {
            spawnActor(location);
            this.addStatus(new SpawnStatus(PERIOD_TURNS));       // reset cooldown
        } catch (GameEngineException e) {
            new Display().println("Cave spawn failed: " + e.getMessage());
        }
    }

    @Override
    public void spawnActor(Location location) throws GameEngineException {
        if (location.containsAnActor() || spawnables.isEmpty()) return;

        var list = new ArrayList<>(spawnables);
        Creature c = list.get((int)(Math.random() * list.size())).get();
        location.map().addActor(c, location);
    }
}
