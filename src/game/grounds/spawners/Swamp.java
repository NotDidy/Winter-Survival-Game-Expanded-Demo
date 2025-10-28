package game.grounds.spawners;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.statuses.Poisonable;
import game.statuses.PoisonedStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Swamp ground (symbol '~').
 * REQ2:
 * - If there is ANY actor in its surroundings, 50% chance to spawn one animal.
 * - Any animal spawned by a swamp is poisoned (10 turns, 5 dmg/turn).
 */
public class Swamp extends Ground implements Spawner {
    private final Set<Supplier<Actor>> spawnableActors = new HashSet<>();
    private static final double SPAWN_CHANCE_WHEN_ACTOR_NEAR = 0.5;
    private static final int SWAMP_SPAWN_POISON_TURNS = 10;
    private static final int SWAMP_SPAWN_POISON_DAMAGE = 5;

    public Swamp() {
        super('~', "Swamp");
        this.enableAbility(SpawnerAbilities.CAN_SPAWN);
    }

    @Override
    public boolean addSpawnableActor(Supplier<Actor> actorSupplier) {
        return spawnableActors.add(actorSupplier);
    }

    @Override
    public boolean removeSpawnableActor(Supplier<Actor> actorSupplier) {
        return spawnableActors.remove(actorSupplier);
    }

    public Set<Supplier<Actor>> getSpawnableActors() {
        return new HashSet<>(spawnableActors);
    }

    @Override
    public void tick(Location location) {
        if (!this.hasAbility(SpawnerAbilities.CAN_SPAWN)) return;
        try {
            this.spawnActor(location);
        } catch (GameEngineException e) {
            new Display().println("Failed to spawn at Swamp: " + e.getMessage());
        }
    }

    @Override
    public void spawnActor(Location location) throws GameEngineException {
        if (location.containsAnActor() || spawnableActors.isEmpty()) return;

        // any actor nearby?
        boolean actorNearby = false;
        for (Exit exit : location.getExits()) {
            if (exit.getDestination().containsAnActor()) {
                actorNearby = true;
                break;
            }
        }
        if (!actorNearby) return;

        if (Math.random() < SPAWN_CHANCE_WHEN_ACTOR_NEAR) {
            List<Supplier<Actor>> actorList = new ArrayList<>(spawnableActors);
            int randomIndex = (int) (Math.random() * actorList.size());
            Actor actor = actorList.get(randomIndex).get();

            try {
                location.map().addActor(actor, location);

                // swamp-spawned animals are poisoned
                actor.asCapability(Poisonable.class).ifPresent(p ->
                        actor.addStatus(new PoisonedStatus(p, SWAMP_SPAWN_POISON_TURNS, SWAMP_SPAWN_POISON_DAMAGE))
                );

                // REQ2: global spawn side-effects (deer/bear/wolf/croc)
                SpawnHooks.applyAfterSpawn(location, actor);

            } catch (GameEngineException e) {
                throw new GameEngineException("Could not instantiate " + actor + ": " + e);
            }
        }
    }
}
