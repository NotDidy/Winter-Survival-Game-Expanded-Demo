package game.behaviors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;

/**
 * Behaviour that allows Actor to attack nearby Actors.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.0.0
 */
public class AttackBehaviour implements Behaviour {
    /**
     * Generates Attack Action for the actor if there are other actors next to or adjacent to the actor.
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return an Action that actor can perform, or null if actor can't do this.
     * @author Riordan D. Alfredo
     */
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (map.isAnActorAt(destination)) {
                Actor targetActor = map.getActorAt(destination);
                AttackAction attackAction = new AttackAction(targetActor, exit.getName());
                return attackAction;
            }
        }
        return null;
    }
}
