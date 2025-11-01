package game.grounds.spawners;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

import java.util.function.Supplier;

public interface Spawner {
    /**
     * Adds a new Actor supplier that this Cave can spawn.
     *
     * @param actorSupplier The Actor supplier to be added.
     */
    public boolean addSpawnableActor(Supplier<Actor> actorSupplier);

    /**
     * Removes a spawnable Actor supplier from this Cave.
     *
     * @param actorSupplier The Actor supplier to remove.
     */
    public boolean removeSpawnableActor(Supplier<Actor> actorSupplier);
    public void spawnActor(Location location) throws GameEngineException;
}
