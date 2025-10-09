package game.creatures;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Flammable;
import game.actions.SilentAction;
import game.actors.Abilities;
import game.actions.AttackAction;
import game.behaviors.WanderBehaviour;
import game.statuses.Bleedable;
import game.statuses.Frostbittenable;
import game.statuses.Poisonable;

import java.util.Map;
import java.util.TreeMap;

/**
 * Abstract Creature class that has WanderBehaviour and can be attacked.
 * @author Jeffrey Lam Po Yuen (35306750), John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public abstract class Creature extends Actor implements Flammable, Bleedable, Poisonable, Frostbittenable {
    private final static int WANDER_PRIORITY = 50;
    private final static int REDUCE_COLD_AMOUNT = 1;
    protected final Map<Integer, Behaviour> behaviours = new TreeMap<>();

    /**
     * Creature constructor
     * @param name          Name of the creature.
     * @param displayChar   Display character in the map of the creature
     * @param hitPoints     Health points of the character.
     */
    public Creature(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.enableAbility(Abilities.CAN_ATTACK);
        this.behaviours.put(50, new WanderBehaviour());
        this.addNewStatistic(CreatureAttributes.WARMTH, new BaseActorAttribute(20));
        this.behaviours.put(WANDER_PRIORITY, new WanderBehaviour());
    }

    /**
     * Adds Behaviour object into the Creature.
     *
     * @param priority  The priority of the Behaviour object over other behaviours.
     * @param behaviour The Behaviour object to be added.
     */
    public void addBehaviour(int priority, Behaviour behaviour) {
        this.behaviours.put(priority, behaviour);
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
        if(otherActor.hasAbility(Abilities.CAN_ATTACK)){
            for (var item : otherActor.getItemInventory()) {
                item.asCapability(Weapon.class).ifPresent(weapon -> {
                    actions.add(new AttackAction(this, direction, weapon));
                });
            }
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
        if (this.hasStatistic(CreatureAttributes.WARMTH) && !this.hasAbility(Abilities.COLD_RESISTANT)) {
            this.modifyAttribute(CreatureAttributes.WARMTH, ActorAttributeOperation.DECREASE, REDUCE_COLD_AMOUNT);
            display.println(this + " feels cold!");
            // If warmth reaches 0 → remove creature (unconscious)
            if (this.getAttribute(CreatureAttributes.WARMTH) <= 0) {
                display.println(map.locationOf(this).getActor().unconscious(map));
                return new SilentAction();
            }
        }

        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.generateAction(this, map);
            if (action != null) {
                return action;
            }
        }
        return lastAction.getNextAction();
    }

    @Override
    public void burn(int damage) {
        this.hurt(damage);
    }

    @Override
    public void bleed(int damage) {
        this.hurt(damage);
    }

    @Override
    public void poison(int damage) {
        this.hurt(damage);
    }

    @Override
    public  void frostbite(int points) {
        this.modifyAttribute(CreatureAttributes.WARMTH, ActorAttributeOperation.DECREASE, points);
    }
}
