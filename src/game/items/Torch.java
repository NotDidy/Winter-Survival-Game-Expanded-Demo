package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Flammable;
import game.statuses.BurningStatus;

import java.util.Random;

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

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();
        if (!(rand.nextInt(100) <= this.hitRate)) {
            return attacker + " misses " + target + ".";
        }
        target.hurt(damage);


        target.asCapability(Flammable.class).ifPresent(flammable -> {
            target.addStatus(new BurningStatus(flammable, 7, 3));
        });

        return String.format("%s %s %s for %d damage", attacker, verb, target, damage);
    }
}
