package game.items;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.creatures.Ghost;

import java.util.List;
import java.util.Random;

/**
 * A weapon that curses the player if held for too long.
 */
public class CursedWeapon extends CursedItem implements Weapon {

    public static final String DEFAULT_NAME = "Cursed Weapon";
    public static final char DEFAULT_DISPLAY_CHAR = '?';
    public static final boolean DEFAULT_PORTABLE = true;
    public static final int DEFAULT_DAMAGE = 45;
    public static final int DEFAULT_HIT_RATE = 90;
    public static final String DEFAULT_VERB = "swings at";
    public static final String GHOST_NAME = "Accursed Spectre";
    public static final int DEFAULT_CURSE_THRESHOLD = 5;
    public static final char GHOST_CHAR = '}';
    public static final int GHOST_HP = 50;


    protected int damage;
    protected int hitRate;
    protected String verb;



    public CursedWeapon(String name, char displayChar, boolean portable, int cursedThreshold) {
        super(name, displayChar, portable, cursedThreshold);
        this.damage = DEFAULT_DAMAGE;
        this.hitRate = DEFAULT_HIT_RATE;
        this.verb = DEFAULT_VERB;
    }

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();
        if (!(rand.nextInt(100) <= this.hitRate)) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(damage);




        return String.format("%s %s %s for %d damage", attacker, verb, target, damage);
    }


    @Override
    public void triggerCurse(Actor actor, GameMap map) {
        cursedActive = true;
        actor.removeItemFromInventory(this);

        List<Exit> exits = map.locationOf(actor).getExits();
        Display display = new Display();

        for (Exit exit : exits) {
            if (Math.random() >= 0.7) {
                Location spawn = exit.getDestination();
                if (!spawn.containsAnActor()) {
                    try {
                        map.addActor(new Ghost(GHOST_NAME, GHOST_CHAR, GHOST_HP, this), spawn);
                        display.println(actor + " has spawned an " + GHOST_NAME + " from their cursed weapon.");
                        return;
                    } catch (GameEngineException e) {
                        display.println("Failed to summon ghost.");
                    }
                }
            }
        }
    }


}
