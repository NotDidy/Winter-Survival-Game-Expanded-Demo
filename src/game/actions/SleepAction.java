package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Sleepable;
import game.statuses.Asleep;

/**
 * SleepAction class to perform sleep.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class SleepAction extends Action {
    Sleepable sleepable;
    int roundsOfSleep;

    /**
     * Parameterised constructor
     * @param sleepable The sleepable object.
     */
    public SleepAction (Sleepable sleepable) {
        this.sleepable = sleepable;
        this.roundsOfSleep = (int) (Math.random() * 5) + 6;
    }
    /**
     * Perform the Action. Modified the execution() method of SleepAction from conwayslife.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened (the result of the action being performed) that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (!actor.hasStatus(Asleep.class))
            actor.addStatus(new Asleep(this.roundsOfSleep));
        this.roundsOfSleep--;
        return actor + " is sleeping for " + roundsOfSleep + " more rounds";
    }

    /**
     * Describe what action will be performed if this Action is chosen in the menu.
     *
     * @param actor The actor performing the action.
     * @return the action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " sleeps for " + roundsOfSleep + " turns";
    }

    @Override
    public Action getNextAction() {
        if (roundsOfSleep > 0)
            return this;
        return null;
    }
}
