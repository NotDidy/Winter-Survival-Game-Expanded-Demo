package game.creatures;

import game.behaviors.AttackBehaviour;
import game.weapons.Claw;

/**
 * Represents Bear in the game.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class Bear extends Creature {

    /**
     * Bear constructor
     * @param name          Name of the Bear
     * @param displayChar   The display character of the wolf in game
     * @param hitPoints     The hit points of the wolf
     */
    public Bear(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.warmth = 50; // REQ2: starting warmth for Bear
        this.addBehaviour(30, new AttackBehaviour());
        this.setIntrinsicWeapon(new Claw());
    }
}
