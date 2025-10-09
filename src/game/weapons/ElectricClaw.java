package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Flammable;
import game.grounds.FireGround;
import game.statuses.BurningStatus;
import game.statuses.StunnedStatus;

import java.util.Random;

public class ElectricClaw extends IntrinsicWeapon {
    private static final int DAMAGE = 25;
    private static final String VERB = "strikes";
    private static final int HIT_RATE= 40;
    private static final String NAME = "ElectricClaw";

    /**
     * Constructor.
     */
    public ElectricClaw() {
        super(DAMAGE, VERB, HIT_RATE, NAME);
    }

    /**
     * Processes the attack.
     * @param attacker the actor who performed the attack
     * @param target   the actor who is the target of the attack
     * @param map      the map on which the attack was executed
     * @return         The outcome of the attack.
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();
        if (!(rand.nextInt(100) <= this.hitRate)) {
            return attacker + " misses " + target + ".";
        }
        target.hurt(damage);
        if (!target.hasStatus(StunnedStatus.class))
            target.addStatus(new StunnedStatus(StunnedStatus.DEFAULT_STUN_DURATION));

        return String.format("%s %s %s for %d damage", attacker, verb, target, damage);
    }
}
