package game.behaviors;

import game.creatures.Creature;

/** Default: does nothing each turn. */
public class NoConsumption implements ConsumptionBehavior {
    @Override
    public void onTurn(Creature c) { /* no-op */ }
}
