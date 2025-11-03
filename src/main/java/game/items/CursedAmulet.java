package game.items;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;

import game.creatures.Zombie;
import game.statuses.BlessedStatus;
import java.util.List;

/**
 * A Cursed amulet that heals the player passively every turn if it is within the player's inventory.
 * Spawns a Zombie after a determined amount of turns
 * @author Muhammad Johan Arief bin Muliadee
 * @version 1.3.0
 * @since 1.3.0
 */
public class CursedAmulet extends CursedItem {
    public static final String DEFAULT_NAME = "Cursed Amulet";
    public static final char DEFAULT_DISPLAY_CHAR = '&';
    public static final boolean DEFAULT_PORTABLE = true;
    public static final String ZOMBIE_NAME = "Zombie";
    public static final int DEFAULT_CURSE_THRESHOLD = 5;
    public static final char ZOMBIE_CHAR = '@';
    public static final int ZOMBIE_HP = 80;
    public static final int AMULET_HEAL_AMOUNT = 2;
    private final BlessedStatus blessedStatus = new BlessedStatus(this);

    /**
     * Constructor
     * @param name
     * @param displayChar
     * @param portable
     * @param cursedThreshold
     */
    public CursedAmulet(String name, char displayChar, boolean portable, int cursedThreshold) {
        super(name, displayChar, portable, cursedThreshold);
    }

    /**
     * Adds the blessed status if the actor does not have it. Calls for the curse trigger after the timer matches the threshold
     * @param location The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location location, Actor actor) {
        if (actor != null) {
            if (!actor.hasStatus(BlessedStatus.class)) {

                actor.addStatus(blessedStatus);
            }


            // Increase curse timer only if not already cursed
            if (!cursedActive) {
                curseTimer++;
                if (curseTimer >= DEFAULT_CURSE_THRESHOLD) {
                    triggerCurse(actor, location.map());
                }
            }
        }
    }

    /**
     * Spawns a Zombie within a random adjacent tile from the player. Removes the amulet from the inventory and links it to the zombie
     * @param actor player
     * @param map current map
     */
    @Override
    public void triggerCurse(Actor actor, GameMap map) {
        cursedActive = true;
        actor.removeItemFromInventory(this);
        actor.removeStatus(blessedStatus);

        List<Exit> exits = map.locationOf(actor).getExits();
        Display display = new Display();

        for (Exit exit : exits) {
            if (Math.random() >= 0.7) {
                Location spawn = exit.getDestination();
                if (!spawn.containsAnActor()) {
                    try {
                        map.addActor(new Zombie(ZOMBIE_NAME, ZOMBIE_CHAR, ZOMBIE_HP, this), spawn);
                        display.println(actor + " has spawned an " + ZOMBIE_NAME + " from their cursed amulet.");
                        return;
                    } catch (GameEngineException e) {
                        display.println("Failed to summon ghost.");
                    }
                }
            }
        }
    }
}
