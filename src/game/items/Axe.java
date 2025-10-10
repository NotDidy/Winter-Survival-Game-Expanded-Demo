package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.coatings.Coatable;
import game.coatings.Coating;
import game.statuses.Bleedable;
import game.statuses.BleedingStatus;

import java.util.Random;

/**
 * An Axe that inflict bleeding onto other actors
 * Deals 15 damage with 75% hit chance and a 50% chance of inflicting bleed.
 *
 * @author Johan
 */

public class Axe extends Item implements Weapon, Coatable {
    public static final String DEFAULT_NAME = "Axe";
    public static final char DEFAULT_DISPLAY_CHAR = 'p';
    public static final boolean DEFAULT_PORTABLE = true;
    public static final int DEFAULT_DAMAGE = 15;
    public static final int DEFAULT_HIT_RATE = 75;
    public static final int DEFAULT_BLEED_RATE = 50;
    public static final String DEFAULT_VERB = "swings at";

    protected int damage;
    protected int hitRate;
    protected int bleedRate;
    protected String verb;
    private Coating coating;

    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public Axe(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
        this.damage = DEFAULT_DAMAGE;
        this.hitRate = DEFAULT_HIT_RATE;
        this.bleedRate = DEFAULT_BLEED_RATE;
        this.verb = DEFAULT_VERB;
    }


    /**
     * Performs an attack with the axe
     * @param attacker The attacking actor
     * @param target The actor being attacked
     * @param map The game map
     * @return A string to display onto the console
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();
        if (!(rand.nextInt(100) <= this.hitRate)) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(damage);



        //Inflicting bleed based on bleedRate
        if ((rand.nextInt(100) <= this.bleedRate)) {
            // Dependency on Bleedable
            if (target.asCapability(Bleedable.class).isPresent()) {
                Bleedable bleedable = target.asCapability(Bleedable.class).get();
                target.addStatus(new BleedingStatus(bleedable,2, 10));
            }
        }

        if (coating != null) {
            coating.applyEffect(attacker, target, map);
        }




        return String.format("%s %s %s for %d damage", attacker, verb, target, damage);
    }

    @Override
    public void applyCoating(Coating coating) {
        this.coating = coating;
    }
}
