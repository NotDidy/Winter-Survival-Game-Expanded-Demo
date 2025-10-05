package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.creatures.StatefulCreature;
import game.creatures.creatureStates.CreatureState;

/**
 * Represents change of state of the creatures
 * @author John Devadhas (34380507) Jeffrey Lam Po Yuen (35306750)
 * @version 1.1.0
 * @since 1.1.0
 */
public class StateAction extends Action {
    private StatefulCreature statefulCreature;

    public StateAction (StatefulCreature statefulCreature) {
        this.statefulCreature = statefulCreature;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened (the result of the action being performed) that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return this.statefulCreature.changeState();
    }

    /**
     * Describe what action will be performed if this Action is chosen in the menu.
     *
     * @param actor The actor performing the action.
     * @return the action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return "";
    }
}
