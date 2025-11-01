package game.behaviors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.HealAction;
import game.grounds.HealActor;

/**
 * Represents a Behaviour of an actor that can self-heal if on a specified Ground class.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class HealFromGroundBehaviour implements Behaviour {
    public static final int DEFAULT_BEHAVIOUR_PRIORITY = 20;
    public static final int DEFAULT_HEAL_AMOUNT = 25;
    private final int healAmount;

    /**
     * HealFromGroundBehaviour constructor
     *
     * @param healAmount         The amount that the actor is healed by
     */
    public HealFromGroundBehaviour(int healAmount) {
        this.healAmount = healAmount;
    }

    /**
     * Returns a HealFromGroundAction to heal if possible
     * If actor doesn't need to heal, return null
     *
     * @param actor the Actor enacting the behaviour
     * @param map the map that actor is currently on
     * @return an Action, or null if no HealFromGroundAction is needed.
     */
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        boolean healthIsLow = actor.getMaximumAttribute(BaseAttributes.HEALTH) -  actor.getAttribute(BaseAttributes.HEALTH) >= this.healAmount;
        if(map.locationOf(actor).getGroundAs(HealActor.class) != null && healthIsLow) {
            return new HealAction(this.healAmount);
        }
        else {
            return null;
        }
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
