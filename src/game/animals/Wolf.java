package game.animals;


import game.behaviors.AttackBehaviour;
import game.weapons.Bite;

/**
 * Class that represents Wolf animal.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class Wolf extends Creature {
    /**
     * Wolf constructor
     * @param name          Name of the wolf
     * @param displayChar   The display character of the wolf in game
     * @param hitPoints     The hit points of the wolf
     */
    public Wolf(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addBehaviour(30, new AttackBehaviour());
        this.setIntrinsicWeapon(new Bite());
    }
}
