package game.behaviors;

import game.creatures.Creature;

/**
 * REQ2: Strategy for how a creature consumes items each turn.
 */
public interface ConsumptionBehavior {
    /** Invoked once per turn by the creature. */
    void onTurn(Creature c);
}
