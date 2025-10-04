package game.creatures;

import game.creatures.creatureStates.CreatureState;

public interface StatefulCreature {
    /**
     * Updates the current state of the creature.
     * The CreatureState implementations will use this method to perform a transition.
     */
    void setCurrentState(CreatureState newState);

    /**
     *
     * @return
     */
    String changeState();
}
