package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/**
 * Body Slam Intrinsic Weapon to be used by an Actor.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class BodySlam extends IntrinsicWeapon{
    private static final int DAMAGE = 15;
    private static final String VERB = "slams";
    private static final int HIT_RATE = 60;
    private static final String NAME = "Body";

    /**
     * Constructor
     */
    public BodySlam() {
        super(DAMAGE, VERB, HIT_RATE, NAME);
    }
}
