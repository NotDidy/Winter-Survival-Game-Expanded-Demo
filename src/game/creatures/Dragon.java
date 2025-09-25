package game.creatures;

import game.behaviors.AttackBehaviour;
import game.weapons.BareFist;
import game.weapons.Claw;

/**
 * Represents Dragon in the game.
 * @author Jeffrey Lam Po Yuen (35306750)
 * @version 1.1.0
 * @since 1.1.0
 */
public class Dragon extends Creature implements StatefulCreature{
    /**
     * Dragon constructor
     * @param name          Name of the Dragon
     * @param displayChar   The display character of the Dragon in game
     * @param hitPoints     The hit points of the Dragon
     */
    public Dragon(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addBehaviour(30, new AttackBehaviour());
        this.setIntrinsicWeapon(new Claw());
        this.setIntrinsicWeapon(new BareFist());
    }
}
