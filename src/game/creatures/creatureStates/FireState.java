package game.creatures.creatureStates;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.creatures.StatefulCreature;
import game.weapons.FireClaw;

/**
 * Represents Fire State of a creature.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class FireState implements CreatureState{
    /**
     * Processes the state transition of the Creature. Creature may or may not change states everytime this method is called.
     *
     * @param creature The creature that is changing states
     * @return The outcome of the method
     */
    @Override
    public String handleStateTransition(StatefulCreature creature, GameMap map) {
        creature.setCurrentState(new WindState());
        return creature + " is in Wind state and blows away Player.";
    }

    /**
     * Returns details regarding a specific state.
     * @return The details of the state.
     */
    @Override
    public String stateDetails(StatefulCreature creature) {
        IntrinsicWeapon weapon = new FireClaw();
        creature.changeWeapon(weapon);
        return creature + " is in Fire state and equips " + weapon + ".";
    }
}
