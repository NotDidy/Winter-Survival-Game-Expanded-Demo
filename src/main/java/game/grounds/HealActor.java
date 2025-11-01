package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Represents a contract that allows this class to heal an Actor
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public interface HealActor {
    /**
     * Default method that processes if the current Ground can heal the Actor or not
     * @param actor     The Actor to be healed
     * @return          default true
     */
    default boolean canHealActor(Actor actor) {
        return true;
    }
}
