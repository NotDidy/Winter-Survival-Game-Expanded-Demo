package game.creatures.creatureStates;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.creatures.StatefulCreature;
import game.weapons.WindyWings;


/**
 * Represents Wind State of a creature.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class WindState implements CreatureState{
    /**
     * Returns details regarding a specific state.
     * @return The details of the state.
     */
    @Override
    public String stateDetails(StatefulCreature creature) {
        IntrinsicWeapon weapon = new WindyWings();
        return creature + " is in Wind state. " + creature.changeWeapon(weapon);
    }

    /**
     * Returns details regarding transitioning from a specific state.
     *
     * @param creature The StatefulCreature with a state.
     * @return The details of transitioning from the state.
     */
    @Override
    public String transitionFromState(StatefulCreature creature) {
        String removeWeaponString = creature + " loses " + creature.getWeapon() + ".";
        creature.changeWeapon(creature.getDefaultWeapon());
        return removeWeaponString;
    }
}
