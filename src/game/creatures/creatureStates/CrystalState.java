package game.creatures.creatureStates;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actors.Abilities;
import game.creatures.StatefulCreature;
import game.weapons.Claw;

/**
 * Represents Crystal State of a creature.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class CrystalState implements CreatureState{
    /**
     * Processes the state transition of the Creature. Creature may or may not change states everytime this method is called.
     *
     * @param creature The creature that is changing states
     * @return The outcome of the method
     */
    @Override
    public String handleStateTransition(StatefulCreature creature, GameMap map) {
        CreatureState nextState = new EarthState();
        creature.setCurrentState(nextState);
        return nextState.stateDetails(creature);
    }

    /**
     * Returns details regarding a specific state.
     * @return The details of the state.
     */
    @Override
    public String stateDetails(StatefulCreature creature) {
        IntrinsicWeapon weapon = new Claw();
        String abilityOutput = creature.enableCreatureAbility(Abilities.IMMUNE_TO_ATTACKS);
        return creature + " is in Crystal state. " + weapon + abilityOutput;
    }
}
