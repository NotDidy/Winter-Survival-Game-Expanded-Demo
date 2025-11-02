package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Represents objects that can be consumed.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.0.0
 */
public interface Consumable {
    /**
     * Processes the consumption of the Apple object by an actor.
     * @param actor     The actor that consumes the Apple
     * @return          The result of consuming the Apple.
     */
    public String consumedBy(Actor actor);
}
