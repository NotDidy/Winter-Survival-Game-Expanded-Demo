package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.actors.Actor;
import game.statuses.Curseable;

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
