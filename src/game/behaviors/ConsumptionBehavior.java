package game.behaviors;

import game.creatures.Creature;

/** REQ2: strategy for how (or whether) a creature consumes ground items each turn. */
public interface ConsumptionBehavior {
    /** Called once per turn by the creature. Keep the side effects limited to consumption. */
    void onTurn(Creature self);
}
