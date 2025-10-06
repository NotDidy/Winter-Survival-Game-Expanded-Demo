package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Flammable;
import game.grounds.FireGround;
import game.statuses.BurningStatus;

import java.util.Random;

public class FireClaw extends IntrinsicWeapon implements Weapon {
    protected int damage;
    protected int hitRate;
    protected String verb;

    /**
     * Constructor.
      */
    public FireClaw() {
        super(75, "claws", 50, "FireClaw");
    }

    private void spawnFire(GameMap map, Actor attacker) {
        Location attackerLocation = map.locationOf(attacker);
        for (Exit exit : attackerLocation.getExits()) {
            Location nearby = exit.getDestination();
            nearby.setGround(new FireGround(5));
        }
    }

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();
        this.spawnFire(map, attacker);
        if (!(rand.nextInt(100) <= this.hitRate)) {
            return attacker + " misses " + target + ".";
        }
        target.hurt(damage);

        // Dependency on Flammable
        if (target.asCapability(Flammable.class).isPresent()) {
            Flammable flammable = target.asCapability(Flammable.class).get();
            target.addStatus(new BurningStatus(flammable,7, 3));
        }

        return String.format("%s %s %s for %d damage", attacker, verb, target, damage);
    }
}
