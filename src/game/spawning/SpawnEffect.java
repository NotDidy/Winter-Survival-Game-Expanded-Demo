package game.spawning;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/** Strategy invoked when a creature is spawned by any spawner. */
public interface SpawnEffect {
    void apply(Location spawnerLocation, Actor spawned);
}
