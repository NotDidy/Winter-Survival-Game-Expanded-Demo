package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Represents a self-healing action for an actor to execute.
 * @author John Devadhas (34380507)
 * @version
 */
public class HealAction extends Action {
    public static final int DEFAULT_HEAL_AMOUNT = 25;
    private final int healAmount;

    /**
     * Parameterised constructor for HealAction.
     * @param healAmount    The amount the Actor that is healing by.
     */
    public HealAction(int healAmount) {
        this.healAmount = healAmount;
    }

    /**
     * Perform the ConsumeAction and process.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return  The result of consumption
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.heal(healAmount);
        String result = actor + " heals by " + this.healAmount + ".";
        if (actor.isConscious())
            return result;
        return result + actor.unconscious(map);
    }

    /**
     * Describe what action will be performed if this Action is chosen in the menu.
     *
     * @param actor The actor performing the action.
     * @return the action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " heals by " + this.healAmount;
    }

}
