package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/**
 * Body Slam Intrinsic Weapon to be used by an Actor.
 */
public class BodySlam extends IntrinsicWeapon {
    private static final int DAMAGE = 30;
    private static final String VERB = "slams";
    private static final int HIT_RATE= 60;
    private static final String NAME = "Body";
    /**
     * Constructor
     */
    public BodySlam() {
        super(DAMAGE, VERB, HIT_RATE, NAME);
    }
}
