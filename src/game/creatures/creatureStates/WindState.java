package game.creatures.creatureStates;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.creatures.StatefulCreature;
import game.weapons.Claw;


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
        IntrinsicWeapon weapon = new Claw();
        return creature + " is in Wind state. " + creature.changeWeapon(weapon);
    }
}
