package game.items;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.creatures.Ghost;
import java.util.Random;

/**
 * A weapon that curses the player if held for too long.
 */
public class CursedWeapon extends Item implements Weapon {

    public static final String DEFAULT_NAME = "Cursed Weapon";
    public static final char DEFAULT_DISPLAY_CHAR = '?';
    public static final boolean DEFAULT_PORTABLE = true;
    public static final int DEFAULT_DAMAGE = 45;
    public static final int DEFAULT_HIT_RATE = 90;
    public static final String DEFAULT_VERB = "swings at";
    public static final String GHOST_NAME = "Accursed Spectre";

    protected int damage;
    protected int hitRate;
    protected String verb;

    private final int curseThreshold = 5; // Turns before curse triggers
    private int turnsHeld = 0;
    private boolean isCursedActive = false;

    public CursedWeapon(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
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
    public void tick(Location location, Actor actor) {
        // Only track time when held by a player
        if (actor != null && !isCursedActive) {
            turnsHeld++;

            if (turnsHeld >= curseThreshold) {
                triggerCurse(actor, location.map());
            }
        }
    }

    private void triggerCurse(Actor actor, GameMap map) {
        turnsHeld = 0;
        isCursedActive = true;

        // Drop the weapon
        actor.removeItemFromInventory(this);

        Location actorLocation = map.locationOf(actor);
        Display display = new Display();

        // Try to spawn one ghost in a random adjacent tile
        for (Exit exit : actorLocation.getExits()) {
            Location spawnLocation = exit.getDestination();
            if (!spawnLocation.containsAnActor()) {
                try {
                    Ghost ghost = new Ghost(GHOST_NAME, 'G', 50);
                    map.addActor(ghost, spawnLocation);
                    break;
                } catch (GameEngineException e) {
                    display.println("Failed to spawn ghost.");
                }
            }

        }



        display.println(actor + " has spawned a ghost from their cursed weapon.");
    }

    /** Called by Ghost upon being defeated */
    public void removeCurse() {
        isCursedActive = false;
    }
}
