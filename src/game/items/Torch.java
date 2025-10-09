package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Flammable;
import game.grounds.FireGround;
import game.statuses.BurningStatus;

import java.util.Random;


/**
 * A Torch that inflict burning onto other actors
 * Deals 10 damage with 50% hit chance and spawns fire around the actor that swings the torch that lasts for 5 turn and burns the target upon a hit for 7 turns
 *
 * @author Johan
 */
public class Torch extends Item implements Weapon {
    protected int damage;
    protected int hitRate;
    protected String verb;
    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public Torch(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
        this.damage = 10;
        this.hitRate = 50;
        this.verb = "swings at";
    }

    /**
     * Spawns fire surrounding the actor after a swing
     * @param attacker The attacking actor
     * @param map The game map
     */
    private void spawnFire(GameMap map, Actor attacker) {
        Location attackerLocation = map.locationOf(attacker);
        for (Exit exit : attackerLocation.getExits()) {
            Location nearby = exit.getDestination();
            nearby.setGround(new FireGround(5));
        }
    }

    /**
     * Performs an attack with the torch
     * @param attacker The attacking actor
     * @param target The actor being attacked
     * @param map The game map
     * @return A string to display onto the console
     */
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
            target.addStatus(new BurningStatus(flammable,BurningStatus.DEFAULT_BURN_DURATION, BurningStatus.DEFAULT_DAMAGE));
        }

        return String.format("%s %s %s for %d damage", attacker, verb, target, damage);
    }
}
