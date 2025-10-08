package game.creatures.creatureStates;

import game.creatures.StatefulCreature;

/**
 * Represents states of Creatures.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public interface CreatureState {

    /**
     * Returns details regarding a specific state.
     * @return The details of the state.
     */
    String stateDetails(StatefulCreature creature);
}
