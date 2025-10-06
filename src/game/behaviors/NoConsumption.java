package game.behaviors;

import game.creatures.Creature;

/** Default behaviour: creature does not consume anything from the ground. */
public class NoConsumption implements ConsumptionBehavior {
    @Override
    public void onTurn(Creature self) {
        // no-op
    }
}
