package game.grounds.spawners;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.creatures.Creature;
import game.statuses.Poisonable;
import game.statuses.PoisonedStatus;

import java.util.*;
import java.util.function.Supplier;

/**
 * Swamp ground (symbol '~').
 * If there is any actor in its surroundings, it has a 50% chance each tick to spawn one registered animal.
 * Animals spawned here are poisoned for 10 turns, 5 damage/turn.
 */
public class Swamp extends Ground implements Spawner {
    private final Set<Supplier<Actor>> spawnableActors = new HashSet<>();
    private static final double SPAWN_IF_NEARBY_CHANCE = 0.5;
    private static final int SELF_POISON_TURNS = 10;
    private static final int SELF_POISON_DMG = 5;

    public Swamp() {
        super('~', "Swamp");
        this.enableAbility(SpawnerAbilities.CAN_SPAWN);
    }

    @Override public boolean addSpawnableActor(Supplier<Actor> actorSupplier) { return spawnableActors.add(actorSupplier); }
    @Override public boolean removeSpawnableActor(Supplier<Actor> actorSupplier) { return spawnableActors.remove(actorSupplier); }
    public Set<Supplier<Actor>> getSpawnableActors() { return new HashSet<>(spawnableActors); }

    @Override
    public void tick(Location location) {
        if (!this.hasAbility(SpawnerAbilities.CAN_SPAWN)) return;

        boolean actorNearby = false;
        for (Exit e : location.getExits()) {
            if (e.getDestination().containsAnActor()) { actorNearby = true; break; }
        }
        if (!actorNearby) return;

        if (Math.random() < SPAWN_IF_NEARBY_CHANCE) {
            try {
                this.spawnActor(location);
            } catch (GameEngineException e) {
                new Display().println("Failed to spawn actor at Swamp: " + e.getMessage());
            }
        }
    }

    @Override
    public void spawnActor(Location location) throws GameEngineException {
        if (location.containsAnActor() || spawnableActors.isEmpty()) return;

        List<Supplier<Actor>> actorList = new ArrayList<>(spawnableActors);
        Actor actor = actorList.get((int) (Math.random() * actorList.size())).get();

        try {
            location.map().addActor(actor, location);

            // Run the creature’s declared spawn effects (deer/bear/wolf/croc special rules)
            actor.asCapability(Creature.class).ifPresent(c -> c.onSpawn(location));

            // Swamp-specific self-poison on the spawned animal (10 turns, 5 dmg/turn)
            actor.asCapability(Poisonable.class).ifPresent(p ->
                    actor.addStatus(new PoisonedStatus(p, SELF_POISON_TURNS, SELF_POISON_DMG))
            );
        } catch (GameEngineException e) {
            throw new GameEngineException("Could not instantiate " + actor + ": " + e);
        }
    }
}
