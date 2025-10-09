package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.statuses.StunnedStatus;

import java.util.Random;

/**
 * Represents Electric CLaw that can stun other target Actors.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class ElectricClaw extends IntrinsicWeapon {
    private static final int DAMAGE = 25;
    private static final String VERB = "strikes";
    private static final int HIT_RATE = 40;
    private static final String NAME = "Electric Claw";

    /**
     * Constructor.
     */
    public ElectricClaw() {
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
        if (!target.hasStatus(StunnedStatus.class))
            target.addStatus(new StunnedStatus(StunnedStatus.DEFAULT_STUN_DURATION));

        return String.format("%s %s %s for %d damage", attacker, VERB, target, DAMAGE);
    }
}
