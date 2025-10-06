package game.behaviors;

import game.creatures.Creature;

/**
 * Meadow rule: spawned creatures can consume ground items (same effect as Explorer).
 * We delegate to Creature so we don't depend on your item/tile APIs here.
 */
public class GroundConsumption implements ConsumptionBehavior {
    @Override
    public void onTurn(Creature self) {
        if (self == null) return;
        self.consumeOneGroundItemIfAvailable(); // implement this hook in Creature
    }
}
