package game.creatures;

import game.actors.Abilities;
import game.behaviors.AttackBehaviour;
import game.weapons.BareFist;

/**
 * Represents Golem in the game.
 * @author Jeffrey Lam Po Yuen (35306750)
 * @version 1.1.0
 * @since 1.1.0
 */
public class Golem extends Creature implements StatefulCreature{
    /**
     * Golem constructor
     * @param name          Name of the Golem
     * @param displayChar   The display character of the Golem in game
     * @param hitPoints     The hit points of the Golem
     */
    public Golem(String name, char displayChar, int hitPoints) {
        super("Golem", 'G', 300);
        this.enableAbility(Abilities.CAN_ATTACK);
        this.addBehaviour(30, new AttackBehaviour());
        this.setIntrinsicWeapon(new BareFist());
    }
}
