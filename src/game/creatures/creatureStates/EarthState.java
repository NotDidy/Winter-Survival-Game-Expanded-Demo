package game.creatures.creatureStates;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.creatures.StatefulCreature;
import game.weapons.PoisonIvy;

/**
 * Represents Earth State of a creature.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class EarthState implements CreatureState{

    /**
     * Returns details regarding a specific state.
     * @return The details of the state.
     */
    @Override
    public String stateDetails(StatefulCreature creature) {
        IntrinsicWeapon weapon = new PoisonIvy();
//        String behaviourResult = creature.addBehaviour(IntimidateBehaviour.DEFAULT_BEHAVIOUR_PRIORITY, new IntimidateBehaviour(IntimidateBehaviour.DEFAULT_INTIMIDATE_RANGE));
        return creature + " is in Earth state. " + creature.changeWeapon(weapon) + " "; //+ behaviourResult;
    }

    /**
     * Returns details regarding transitioning from a specific state.
     *
     * @param creature The StatefulCreature with a state.
     * @return The details of transitioning from the state.
     */
    @Override
    public String transitionFromState(StatefulCreature creature) {
        String removeWeaponString = "";
        if (creature.getWeapon() != creature.getDefaultWeapon())
            removeWeaponString = creature + " loses " + creature.getWeapon() + ".";
        creature.changeWeapon(creature.getDefaultWeapon());
        return removeWeaponString;
    }
}
