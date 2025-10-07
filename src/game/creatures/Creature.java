package game.creatures;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Abilities;
import game.actions.AttackAction;
import game.behaviors.WanderBehaviour;
import game.behaviors.ConsumptionBehavior;
import game.behaviors.NoConsumption;

import java.util.Map;
import java.util.TreeMap;

/**
 * Abstract Creature class that has WanderBehaviour and can be attacked.
 */
public abstract class Creature extends Actor {
    protected final Map<Integer, Behaviour> behaviours = new TreeMap<>();

    // REQ2: per-animal consumption behaviour hook (default = no consumption)
    private ConsumptionBehavior consumptionBehavior = new NoConsumption();

    /**
     * Creature constructor.
     */
    public Creature(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.enableAbility(Abilities.CAN_ATTACK);
        this.behaviours.put(50, new WanderBehaviour());


        this.addNewStatistic(CreatureAttributes.WARMTH, new BaseActorAttribute(1));
    }

    /**
     * Adds Behaviour object into the Creature.
     * @param priority  The priority of the Behaviour object over other behaviours.
     * @param behaviour The Behaviour object to be added.
     */
    public void addBehaviour(int priority, Behaviour behaviour) {
        this.behaviours.put(priority, behaviour);
    }

    // ===== REQ2 methods (engine-driven warmth & consumption) =====

    /** Current warmth value (reads engine attribute). */
    public int getWarmth() {
        return this.getAttribute(CreatureAttributes.WARMTH);
    }

    /** Allows spawners/effects to assign a consumption behaviour (e.g., GroundConsumption). */
    public void setConsumptionBehavior(ConsumptionBehavior behaviour) {
        if (behaviour != null) this.consumptionBehavior = behaviour;
    }

    /** Expose current consumption behaviour (optional use). */
    public ConsumptionBehavior getConsumptionBehavior() {
        return consumptionBehavior;
    }

    /** Utility for spawn effects like +10 HP on tundra. */
    public void addHealth(int delta) {
        this.heal(delta);
    }

    /** Reserved hook if later you want animals to auto-consume ground items. */
    public void consumeOneGroundItemIfAvailable() {
    }

    /**
     * Generates all allowable actions of the Creature
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of available actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasAbility(Abilities.CAN_ATTACK)) {
            actions.add(new AttackAction(this, direction));
        }
        return actions;
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do
     *                   interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // ===== REQ2: warmth ticks every turn (unless cold-resistant), like Player =====
        if (!this.hasAbility(Abilities.COLD_RESISTANT)) {
            this.modifyAttribute(CreatureAttributes.WARMTH, ActorAttributeOperation.DECREASE, 1);
        }

        // Allow any per-turn consumption logic (e.g., GroundConsumption) to run
        consumptionBehavior.onTurn(this);

        // If warmth reaches 0 → remove creature (unconscious)
        if (this.getWarmth() <= 0) {
            map.removeActor(this);
            return new DoNothingAction();
        }

        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.generateAction(this, map);
            if (action != null) {
                return action;
            }
        }
        return lastAction.getNextAction();
    }
}
