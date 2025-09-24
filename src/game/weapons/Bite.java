package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/**
 * Bite class that deals 25 damage points with a 50% chance.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class Bite extends IntrinsicWeapon {
    /**
     * Constructor for Bite that deals 25 damage points with a 50% chance.
     */
    public Bite() {
        super(50, "bites", 50, "bite");
    }
}
