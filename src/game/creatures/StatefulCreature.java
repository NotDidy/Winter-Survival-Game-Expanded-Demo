package game.creatures;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.creatures.creatureStates.CreatureState;

/**
 * Represents Creatures that can change states.
 * @author John Devahdas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public interface StatefulCreature {
    /**
     * Updates the current state of the creature.
     * The CreatureState implementations will use this method to perform a transition.
     */
    void setCurrentState(CreatureState newState);

    /**
     *
     * @return
     */
    String changeState(GameMap map);

    String changeWeapon(IntrinsicWeapon weapon);
}
