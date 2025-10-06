package game.creatures.creatureStates;

import edu.monash.fit2099.engine.positions.GameMap;
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
        creature.setCurrentState(new EarthState());
        return creature + " is in Earth state and returns to its original state";
    }

    /**
     * Returns details regarding a specific state.
     * @return The details of the state.
     */
    @Override
    public String stateDetails(StatefulCreature creature) {
        creature.changeWeapon(new Claw());
        return "";
    }
}
