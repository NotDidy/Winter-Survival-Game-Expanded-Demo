package game.statuses;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for cursed items
 * @author Muhammad Johan Arief (33523061)
 * @version 1.3.0
 * @since 1.3.0
 */
public interface Curseable {
    void inflictCurse(Actor actor, GameMap map);
    void triggerCurse(Actor actor, GameMap map);
    void liftCurse();
    boolean isCursedActive();
}