package game.spawning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.creatures.Creature;
import game.effects.SpawnEffect;

public class Spawner {
    private final Location location;
    private final SpawnSchedule schedule;
    private final List<Supplier<Creature>> candidates = new ArrayList<>();
    private final List<SpawnEffect> effects = new ArrayList<>();
    private final Random rnd;

    public Spawner(Location location, SpawnSchedule schedule, Random rnd) {
        this.location = location;
        this.schedule = schedule;
        this.rnd = rnd;
    }

    public Spawner addCandidate(Supplier<Creature> ctor) {
        candidates.add(ctor);
        return this;
    }

    public Spawner addEffect(SpawnEffect effect) {
        effects.add(effect);
        return this;
    }

    public void onTick(int turn) {
        if (candidates.isEmpty() || !schedule.shouldSpawn(turn)) return;

        Creature newborn = candidates.get(rnd.nextInt(candidates.size())).get();
        for (SpawnEffect e : effects) e.apply(newborn);

        GameMap map = location.map();
        if (map == null || location.containsAnActor()) return;

        try {
            map.addActor(newborn, location);   // GameMap.addActor(...) throws GameEngineException
        } catch (GameEngineException ignored) {
            // if the engine rejects the add (e.g., invalid location), skip this spawn
        }
    }
}
