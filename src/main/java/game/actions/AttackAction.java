package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actors.Abilities;

/**
 * Class representing an action to attack. Note that the attacker must have a
 * weapon, e.g., an intrinsic weapon or a weapon item. Otherwise, the execute
 * method will throw an error.
 *
 * @author Adrian Kristanto, John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.0.0
 */
public class AttackAction extends Action {

    /**
     * The Actor that is to be attacked
     */
    private Actor target;

    /**
     * The direction of incoming attack.
     */
    private String direction;

    private Weapon weapon;
    /**
     * Constructor.
     *
     * @param target the Actor to attack
     * @param direction the direction where the attack should be performed (only
     * used for display purposes)
     */
    public AttackAction(Actor target, String direction) {
        this.target = target;
        this.direction = direction;
        this.weapon = null;
    }

    /**
     * Constructor.
     *
     * @param target the Actor to attack
     * @param direction the direction where the attack should be performed (only
     * used for display purposes)
     * @param weapon    The weapon used to attack
     */
    public AttackAction(Actor target, String direction, Weapon weapon) {
        this.target = target;
        this.direction = direction;
        this.weapon = weapon;
    }


    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened (the result of the action being performed) that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Weapon weaponToUse = this.weapon != null ? this.weapon : actor.getIntrinsicWeapon();

        String result = "";
        if (target.hasAbility(Abilities.IMMUNE_TO_ATTACKS))
            result = target + " is immune. " + actor + " does not deal damage.";
        else {
            result = weaponToUse.attack(actor, target, map);
            if (target.hasAbility(Abilities.COUNTER_ATTACK))
                result += " " + target + " counters and " + target.getIntrinsicWeapon().attack(target, actor, map);
        }
        if (!target.isConscious()) {
            result += "\n" + target.unconscious(actor, map);
        }

        return result;
    }

    /**
     * Describe what action will be performed if this Action is chosen in the menu.
     *
     * @param actor The actor performing the action.
     * @return the action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        String weaponName = (weapon != null)
                ? weapon.toString()
                : actor.getIntrinsicWeapon().toString();

        return actor + " attacks " + target + " at " + direction + " with " + weaponName;
    }
}
