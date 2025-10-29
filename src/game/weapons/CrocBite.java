package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/** Crocodile bite: 80 dmg, 75% hit. */
public class CrocBite extends IntrinsicWeapon {
    private static final int DAMAGE = 80;
    private static final String VERB = "bites";
    private static final int HIT_RATE = 75;
    private static final String NAME = "bite";

    public CrocBite() {
        super(DAMAGE, VERB, HIT_RATE, NAME);
    }
}
