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
    /**
     * Returns details regarding transitioning from a specific state.
     * @param creature  The StatefulCreature with a state.
     * @return          The details of transitioning from the state.
     */
    String transitionFromState(StatefulCreature creature);
}
