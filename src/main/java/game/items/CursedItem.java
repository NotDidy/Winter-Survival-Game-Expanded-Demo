package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.actors.Actor;
import game.statuses.Curseable;


/**
 * An abstract item type that allows items to have cursed effects
 * Curses are only inflicted based on how long the player has a cursed item in their inventory.
 * Subclasses are the items themselves that offer unique effects at the cost of a curse
 *
 * <p>Examples include {@link game.items.CursedSword} and {@link game.items.CursedAmulet}.</p>
 *
 * @author Muhammad Johan Arief (33523061)
 * */

public abstract class CursedItem extends Item implements Curseable {
    protected int curseTimer = 0;
    protected int curseThreshold;
    protected boolean cursedActive = false;

    public CursedItem(String name, char displayChar, boolean portable, int curseThreshold) {
        super(name, displayChar, portable);
        this.curseThreshold = curseThreshold;
    }

    @Override
    public void inflictCurse(Actor actor, GameMap map) {
        if (!cursedActive) {
            curseTimer++;
            if (curseTimer >= curseThreshold) {
                triggerCurse(actor, map);
            }
        }
    }

    @Override
    public void liftCurse() {
        curseTimer = 0;
        cursedActive = false;
    }

    @Override
    public boolean isCursedActive() {
        return cursedActive;
    }

    @Override
    public void tick(Location location, Actor actor) {
        if (actor != null) {
            inflictCurse(actor, location.map());
        }
    }
}
