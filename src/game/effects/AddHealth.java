package game.effects;

import game.creatures.Creature;

/** Tundra effect: add fixed health (e.g., +10). */
public class AddHealth implements SpawnEffect {
    private final int delta;

    public AddHealth(int delta) { this.delta = delta; }

    @Override
    public void apply(Creature c) {
        c.addHealth(delta);
    }
}
