package game.behaviors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.IntimidateAction;
import game.statuses.StunnedStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a behaviour an Actor can have that loudly intimidates surrounding Actors.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class IntimidateBehaviour implements Behaviour {
    public static final int DEFAULT_INTIMIDATE_RANGE = 3;
    public static final int DEFAULT_BEHAVIOUR_PRIORITY = 20;
    private final int intimidateRange;

    /**
     * IntimidateBehaviour constructor.
     * @param intimidateRange   The range of Actors that are intimidated.
     */
    public IntimidateBehaviour (int intimidateRange) {
        this.intimidateRange = intimidateRange;
    }
    /**
     * Generates Intimidate action for the actor if there are other actors next to or adjacent to the actor.
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return an Action that actor can perform, or null if actor can't do this.
     * @author John Devadhas (34380507)
     */
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        List<Actor> actorsIntimidated = new ArrayList<>();
        Location actorLocation = map.locationOf(actor);
        for (Location nearbyLocation : actorLocation.getNearbyLocations(this.intimidateRange)) {
            if (nearbyLocation.containsAnActor() && !nearbyLocation.getActor().hasStatus(StunnedStatus.class))
                actorsIntimidated.add(nearbyLocation.getActor());
        }
        return actorsIntimidated.isEmpty() ? null : new IntimidateAction(actorsIntimidated);
    }

    /**
     * Retrieves Class simple name for toString for display purposes
     * @return      Class simple name
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
