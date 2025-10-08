package game.behaviors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.StateAction;
import game.creatures.StatefulCreature;
import game.statuses.StateChangeCooldown;

public class StateBehaviour implements Behaviour {
    private final StatefulCreature statefulCreature;

    public StateBehaviour(StatefulCreature statefulCreature) {
        this.statefulCreature = statefulCreature;
    }
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
        if (!actor.hasStatus(StateChangeCooldown.class)) {
            actor.addStatus(new StateChangeCooldown());
            return new StateAction(statefulCreature);
        }
        return null;
    }
}
