package game.animals;

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
     * @param name          Name of the wolf
     * @param displayChar   The display character of the wolf in game
     * @param hitPoints     The hit points of the wolf
     */
    public Bear(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addBehaviour(30, new AttackBehaviour());
        this.setIntrinsicWeapon(new Claw());
    }
}
