package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/**
 * Claw class that deals 25 damage points with a 50% chance.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class Claw extends IntrinsicWeapon {
    /**
     * Constructor for Claw that deals 25 damage points with a 50% chance.
     */
    public Claw() {
        super(75, "claws", 80, "Claw");
    }
}
