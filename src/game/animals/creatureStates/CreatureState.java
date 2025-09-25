package game.animals.creatureStates;

import game.animals.Creature;

/**
 * Represents creatures that can change states
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public interface CreatureState {
    /**
     * Processes the state transition of the Creature. Creature may or may not change states everytime this method is called.
     * @param creature  The creature that is changing states
     * @return          The outcome of the method
     */
    String handleStateTransition (StatefulCreature creature);
}
