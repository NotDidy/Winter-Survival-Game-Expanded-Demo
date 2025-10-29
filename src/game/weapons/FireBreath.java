package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Flammable;
import game.grounds.FireGround;
import game.statuses.BurningStatus;

import java.util.Random;

/**
 * Represents a Fire Breath.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class FireBreath extends IntrinsicWeapon{
    private static final int DAMAGE = 15;
    private static final String VERB = "breathes fire at";
    private static final int HIT_RATE= 30;
    private static final String NAME = "Fire Breath";

    /**
     * Constructor.
      */
    public FireBreath() {
        super(DAMAGE, VERB, HIT_RATE, NAME);
    }

    /**
     * Processes the spawning of FireGround in the surrounding area of the attacker
     * @param map       The GameMap object
     * @param attacker  The attacker who uses Fire Breath.
     */
    private void spawnFire(GameMap map, Actor attacker) {
        Location attackerLocation = map.locationOf(attacker);
        // System.out.println(attackerLocation);
        for (Exit exit : attackerLocation.getExits()) {
            // System.out.println(exit);
            Location nearby = exit.getDestination();
            nearby.setGround(new FireGround(FireGround.DEFAULT_TURNS_REMAINING));
        }
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
        this.spawnFire(map, attacker);
        if (!(rand.nextInt(100) <= HIT_RATE)) {
            return attacker + " misses " + target + ".";
        }
        target.hurt(DAMAGE);

        // Dependency on Flammable
        if (target.asCapability(Flammable.class).isPresent()) {
            Flammable flammable = target.asCapability(Flammable.class).get();
            target.addStatus(new BurningStatus(flammable,BurningStatus.DEFAULT_BURN_DURATION, BurningStatus.DEFAULT_DAMAGE));
        }

        return String.format("%s %s %s for %d damage", attacker, VERB, target, DAMAGE);
    }
}
