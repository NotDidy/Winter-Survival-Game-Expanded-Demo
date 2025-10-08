package game.coatings;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Represents a coating that can be applied to weapons or other items.
 */
public interface Coating {

    /**
     * Apply the coating’s effect when this weapon hits a target.
     */
    void applyEffect(Actor attacker, Actor target, GameMap map);

    /**
     * @return a description name for this coating (e.g. "Snow", "Yewberry").
     */
    String getName();
}
