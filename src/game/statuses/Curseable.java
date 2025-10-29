package game.statuses;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

public interface Curseable {
    void buildCurse(Actor actor, GameMap map);
    void triggerCurse(Actor actor, GameMap map);
    void liftCurse();
    boolean isCursedActive();
}