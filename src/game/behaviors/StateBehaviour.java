package game.behaviors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.StateAction;
import game.creatures.StatefulCreature;
import game.statuses.StateChangeCooldown;

/**
 * Represents a StateBehaviour for StatefulCreatures to execute StateActions
 */
public class StateBehaviour implements Behaviour {
    private final StatefulCreature statefulCreature;

    public StateBehaviour(StatefulCreature statefulCreature) {
        this.statefulCreature = statefulCreature;
    }
    /**
     * Generates State Action for the StatefulCreature.
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return an Action that actor can perform, or null if actor can't do this.
     * @author John Devadhas (34380507)
     */
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        if (actor.asCapability(StatefulCreature.class).isPresent() && !actor.hasStatus(StateChangeCooldown.class)) {
            actor.addStatus(new StateChangeCooldown());
            return new StateAction(statefulCreature);
        }
        return null;
    }
}
