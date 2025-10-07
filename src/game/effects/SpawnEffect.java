package game.effects;

import game.creatures.Creature;

/** REQ2: effect applied to a creature immediately after it is spawned. */
public interface SpawnEffect {
    void apply(Creature c);
}
