package game.grounds.spawners;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Abilities;
import game.creatures.Creature;

import java.util.*;
import java.util.function.Supplier;

/** Tundra (‘_’): 5% each turn; spawns are cold-resistant and gain +10 HP. */
public class Tundra extends Ground implements Spawner {
    private static final double SPAWN_CHANCE = 0.05;
    private static final int BONUS_HP = 10;

    private final Set<Supplier<? extends Creature>> spawnables = new HashSet<>();

    public Tundra() {
        super('_', "Tundra");
        this.enableAbility(SpawnerAbilities.CAN_SPAWN);
    }

    @Override public boolean addSpawnableActor(Supplier<? extends Creature> s){ return spawnables.add(s); }
    @Override public boolean removeSpawnableActor(Supplier<? extends Creature> s){ return spawnables.remove(s); }
    public Set<Supplier<? extends Creature>> getSpawnableActors(){ return new HashSet<>(spawnables); }

    @Override
    public void tick(Location location) {
        if (!this.hasAbility(SpawnerAbilities.CAN_SPAWN)) return;
        try { spawnActor(location); }
        catch (GameEngineException e) { new Display().println("Tundra spawn failed: " + e.getMessage()); }
    }

    @Override
    public void spawnActor(Location location) throws GameEngineException {
        if (location.containsAnActor() || spawnables.isEmpty() || Math.random() >= SPAWN_CHANCE) return;

        var list = new ArrayList<>(spawnables);
        Creature c = list.get((int)(Math.random() * list.size())).get();

        // tundra effects
        c.enableAbility(Abilities.COLD_RESISTANT);
        c.modifyStatsMaximum(BaseAttributes.HEALTH, ActorAttributeOperation.INCREASE, BONUS_HP);
        c.heal(BONUS_HP);

        location.map().addActor(c, location);
    }
}
