package game.effects;

import game.behaviors.ConsumptionBehavior;
import game.creatures.Creature;

/** Assign a specific consumption behaviour to the newborn (e.g., GroundConsumption). */
public class SetConsumptionBehavior implements SpawnEffect {
    private final ConsumptionBehavior behavior;

    public SetConsumptionBehavior(ConsumptionBehavior behavior) {
        this.behavior = behavior;
    }

    @Override
    public void apply(Creature c) {
        c.setConsumptionBehavior(behavior);
    }
}
