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

import java.util.List;
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
    public static final int CURSE_TICK_DURATION = 5;
    public static final char GHOST_CHAR = '}';
    public static final int GHOST_HP = 50;

    protected int damage;
    protected int hitRate;
    protected String verb;

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

            // Turns before curse triggers
            if (turnsHeld >= CURSE_TICK_DURATION) {
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
        List<Exit> exits = actorLocation.getExits();
        Random random = new Random();

        // Randomly attempt spawns until we find a valid one or exhaust options
        for (int i = 0; i < exits.size(); i++) {
            int index = random.nextInt(exits.size());
            Exit randomExit = exits.get(index);
            Location spawnLocation = randomExit.getDestination();

            // Only spawn if tile is empty and walkable
            if (!spawnLocation.containsAnActor() && spawnLocation.canActorEnter(actor)) {
                try {
                    Ghost ghost = new Ghost(GHOST_NAME, GHOST_CHAR, GHOST_HP, this);
                    map.addActor(ghost, spawnLocation);
                    display.println(actor + " has spawned an " + GHOST_NAME + " from their cursed weapon.");
                    return; // stop after spawning one ghost
                } catch (GameEngineException e) {
                    display.println("Failed to spawn ghost.");
                }
            }
        }




    }

    /** Called by Ghost upon being defeated */
    public void removeCurse() {
        isCursedActive = false;
    }
}
