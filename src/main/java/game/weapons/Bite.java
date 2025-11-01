package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/**
 * Bite class that deals 25 damage points with a 50% chance.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class Bite extends IntrinsicWeapon {
    private static final int DAMAGE = 50;
    private static final String VERB = "bites";
    private static final int HIT_RATE= 50;
    private static final String NAME = "bite";
    /**
     * Constructor for Bite that deals 25 damage points with a 50% chance.
     */
    public Bite() {
        super(DAMAGE, VERB, HIT_RATE, NAME);
    }
}
