package game.items;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;

import game.creatures.Zombie;

import java.util.List;


public class CursedAmulet extends CursedItem {
    public static final String DEFAULT_NAME = "Cursed Amulet";
    public static final char DEFAULT_DISPLAY_CHAR = '&';
    public static final boolean DEFAULT_PORTABLE = true;
    public static final String ZOMBIE_NAME = "Zombie";
    public static final int DEFAULT_CURSE_THRESHOLD = 5;
    public static final char ZOMBIE_CHAR = '@';
    public static final int ZOMBIE_HP = 80;

    public CursedAmulet(String name, char displayChar, boolean portable, int cursedThreshold) {
        super(name, displayChar, portable, cursedThreshold);
    }

    @Override
    public void triggerCurse(Actor actor, GameMap map) {
        cursedActive = true;
        actor.removeItemFromInventory(this);
        map.locationOf(actor).addItem(this);

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
