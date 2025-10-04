package game.creatures.creatureStates;

import game.creatures.StatefulCreature;

/**
 * Represents Wind State of a creature.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class WindState implements CreatureState{

    /**
     * Processes the state transition of the Creature. Creature may or may not change states everytime this method is called.
     *
     * @param creature The creature that is changing states
     * @return The outcome of the method
     */
    @Override
    public String handleStateTransition(StatefulCreature creature) {
        creature.setCurrentState(new LightningState());
        return creature + " is in Lightning state and stuns the Player";
    }
}
