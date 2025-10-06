package game.effects;

import game.creatures.Creature;

/** Effect applied to a newly spawned creature. */
public interface SpawnEffect {
    void apply(Creature c);
}
