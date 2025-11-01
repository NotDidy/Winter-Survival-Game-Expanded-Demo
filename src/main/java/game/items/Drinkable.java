package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Sleepable item interface representation.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public interface Drinkable {
    /**
     * Processes an item drinking result by an actor.
     * @param actor    The actor that drank the item object.
     * @return          A String object containing details of the result.
     */
    public String drunkBy(Actor actor);
}
