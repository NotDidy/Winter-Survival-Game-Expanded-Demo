package game.grounds.spawners;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Abilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/** Represents tundra ground (symbol '_') for REQ2 animal spawning.
 *
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class Tundra extends Ground implements Spawner {
    private final Set<Supplier<Actor>> spawnableActors;
    private static final double SPAWN_ACTOR_CHANCE = 0.05;
    private static final int INCREASE_TOTAL_HEALTH = 10;

    /**
     * Tundra constructor
     */
    public Tundra() {
        super('_', "Tundra");
        this.enableAbility(SpawnerAbilities.CAN_SPAWN);
        this.spawnableActors = new HashSet<>();
    }

    /**
     * Adds a new Actor supplier that this Cave can spawn.
     *
     * @param actorSupplier The Actor supplier to be added.
     */
    @Override
    public boolean addSpawnableActor(Supplier<Actor> actorSupplier) {
        return spawnableActors.add(actorSupplier);
    }

    /**
     * Removes a spawnable Actor supplier from this Cave.
     *
     * @param actorSupplier The Actor supplier to remove.
     */
    @Override
    public boolean removeSpawnableActor(Supplier<Actor> actorSupplier) {
        return spawnableActors.remove(actorSupplier);
    }

    /**
     * Returns a copy of the spawnable actor supplier set.
     */
    public Set<Supplier<Actor>> getSpawnableActors() {
        return new HashSet<>(spawnableActors);
    }

    /**
     * Cave tick. Current implementation processes whether it is time for the Cave to spawn actors.
     *
     * @param location The location of the Ground
     */
    public void tick(Location location) {
        if (this.hasAbility(SpawnerAbilities.CAN_SPAWN)) {
            try {
                this.spawnActor(location);
            } catch (GameEngineException e) {
                Display display = new Display();
                display.println("Failed to spawn actor: " + e.getMessage());
            }
        }
    }

    /**
     * Generates Apple objects to be dropped around the AppleTree.
     * @param location  The location of the AppleTree
     */
    @Override
    public void spawnActor(Location location) throws GameEngineException {
        if (location.containsAnActor() || spawnableActors.isEmpty())
            return;
        double random = Math.random();
        if (random < SPAWN_ACTOR_CHANCE) {
            List<Supplier<Actor>> actorList = new ArrayList<>(spawnableActors);
            int randomIndex = (int) (Math.random() * actorList.size());
            Actor actor = actorList.get(randomIndex).get();
            actor.enableAbility(Abilities.COLD_RESISTANT);
            actor.modifyStatsMaximum(BaseAttributes.HEALTH, ActorAttributeOperation.INCREASE, INCREASE_TOTAL_HEALTH);
            try {
                location.map().addActor(actor, location);
            } catch (GameEngineException e) {
                throw new GameEngineException("Could not instantiate " + actor + ": " + e);
            }
        }
    }
}
