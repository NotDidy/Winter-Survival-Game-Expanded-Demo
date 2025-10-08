package game.grounds.spawners;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.positions.Location;
import game.creatures.Creature;

import java.util.function.Supplier;

/** Common API for spawnable grounds (tundra, cave, meadow). */
public interface Spawner {
    /** Register a creature supplier this ground may spawn. */
    boolean addSpawnableActor(Supplier<? extends Creature> actorSupplier);

    /** Unregister a previously added creature supplier. */
    boolean removeSpawnableActor(Supplier<? extends Creature> actorSupplier);

    /** Attempt to spawn according to the ground’s schedule. */
    void spawnActor(Location location) throws GameEngineException;
}
