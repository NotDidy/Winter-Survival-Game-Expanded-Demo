package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.statuses.Poisonable;
import game.statuses.PoisonedStatus;

import java.util.Random;

/**
 * Represents PoisonIvy that is used as a weapon to attack and irritate.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class PoisonIvy extends IntrinsicWeapon {
    private static final int DAMAGE = 10;
    private static final int HIT_RATE = 40;
    private static final String NAME = "Poison Ivy";
    private static final String VERB = "sprouts " + NAME + " at ";

    /**
     * PoisonIvy Constructor.
     */
    public PoisonIvy() {
        super(DAMAGE, VERB, HIT_RATE, NAME);
    }

    /**
     * Processes the attack.
     *
     * @param attacker the actor who performed the attack
     * @param target   the actor who is the target of the attack
     * @param map      the map on which the attack was executed
     * @return The outcome of the attack.
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {

        Random rand = new Random();
        if (!(rand.nextInt(100) <= HIT_RATE)) {
            return attacker + " misses " + target + ".";
        }
        target.hurt(DAMAGE);
        if (target.asCapability(Poisonable.class).isPresent()) {
            Poisonable poisonable = target.asCapability(Poisonable.class).get();
            target.addStatus(new PoisonedStatus(poisonable,PoisonedStatus.DEFAULT_POISON_DURATION, PoisonedStatus.DEFAULT_DAMAGE));
        }
        return String.format("%s %s %s for %d damage. ", attacker, VERB, target, DAMAGE);
    }
}
