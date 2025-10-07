package game.behaviors;

import game.creatures.Creature;

/**
 * REQ2: Example behaviour – allow creatures to consume edible ground items.
 * Hook into your existing consume logic here if you want them to actually eat.
 */
public class GroundConsumption implements ConsumptionBehavior {
    @Override
    public void onTurn(Creature c) {
        // If you want edible items consumed automatically, implement here.
        // c.consumeOneGroundItemIfAvailable();
    }
}
