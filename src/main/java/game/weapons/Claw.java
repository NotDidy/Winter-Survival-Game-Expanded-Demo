package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/**
 * Claw class that deals 25 damage points with a 50% chance.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class Claw extends IntrinsicWeapon {
    private static final int DAMAGE = 25;
    private static final String VERB = "claws";
    private static final int HIT_RATE= 50;
    private static final String NAME = "Claw";
    /**
     * Constructor for Claw that deals 25 damage points with a 50% chance.
     */
    public Claw() {
        super(DAMAGE, VERB, HIT_RATE, NAME);
    }
}
