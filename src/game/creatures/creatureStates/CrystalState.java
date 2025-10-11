package game.creatures.creatureStates;

import game.actors.Abilities;
import game.creatures.StatefulCreature;

/**
 * Represents Crystal State of a creature.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class CrystalState implements CreatureState {

    /**
     * Returns details regarding a specific state.
     * @return The details of the state.
     */
    @Override
    public String stateDetails(StatefulCreature creature) {
        String abilityOutput = creature.enableCreatureAbility(Abilities.IMMUNE_TO_ATTACKS);
        return creature + " is in Crystal state. " + abilityOutput;
    }

    /**
     * Returns details regarding transitioning from a specific state.
     *
     * @param creature The StatefulCreature with a state.
     * @return The details of transitioning from the state.
     */
    @Override
    public String transitionFromState(StatefulCreature creature) {
        return " " + creature.disableCreatureAbility(Abilities.IMMUNE_TO_ATTACKS);
    }

}
