package game.creatures;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actors.Abilities;
import game.creatures.creatureStates.CreatureState;

/**
 * Represents Creatures that can change states.
 * @author John Devahdas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public interface StatefulCreature {

    /**
     * Processes the changing of state.
     * @return      The outcome of changing into the state.
     */
    String changeState(GameMap map);

    /**
     * Replaces the weapon equipped by the Creature
     * @param weapon     The new weapon to be equipped.
     * @return           The outcome of changing weapons.
     */
    String changeWeapon(IntrinsicWeapon weapon);

    /**
     * Enables an ability for the creature
     * @param ability   The ability to be enabled
     * @return          The outcome of enabling the ability.
     */
    String enableCreatureAbility(Abilities ability);

    /**
     * Disables an ability for the creature
     * @param ability   The ability to be disabled
     * @return          The outcome of disabling the ability.
     */
    String disableCreatureAbility(Abilities ability);
}
