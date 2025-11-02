package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/**
 * Class representing an intrinsic weapon called a bare fist.
 * This intrinsic weapon deals 25 damage points with a 50% chance
 * to hit the target.
 * @author Adrian Kristanto
 */
public class BareFist extends IntrinsicWeapon {
    private static final int DAMAGE = 25;
    private static final String VERB = "punches";
    private static final int HIT_RATE= 50;
    private static final String NAME = "bare fist";

    /**
     * BareFist constructor.
     */
    public BareFist() {
        super(DAMAGE, VERB, HIT_RATE, NAME);
    }
}