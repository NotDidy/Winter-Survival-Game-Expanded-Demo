package game.creatures;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actors.Abilities;
import game.behaviors.AttackBehaviour;
import game.behaviors.StateBehaviour;
import game.creatures.creatureStates.*;
import game.statuses.StateChangeCooldown;
import game.weapons.Claw;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents Dragon in the game.
 * @author Jeffrey Lam Po Yuen (35306750), John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class Dragon extends Creature implements StatefulCreature{
    private final StateManager stateManager;
    /**
     * Dragon constructor
     * @param name          Name of the Dragon
     * @param displayChar   The display character of the Dragon in game
     * @param hitPoints     The hit points of the Dragon
     */
    public Dragon(String name, char displayChar, int hitPoints) {
        super("Dragon", 'D', 200);
        this.enableAbility(Abilities.CAN_ATTACK);
        this.addBehaviour(10, new StateBehaviour(this));
        this.addBehaviour(30, new AttackBehaviour());
        this.setIntrinsicWeapon(new Claw());
        this.addStatus(new StateChangeCooldown());
        CreatureState creatureState = new WindState();
        Set<CreatureState> creatureStatesSet = new HashSet<>();
        creatureStatesSet.add(creatureState);
        creatureStatesSet.add(new FireState());
        creatureStatesSet.add(new LightningState());
        this.stateManager = new StateManager(this, creatureState, creatureStatesSet);
        this.stateManager.addTransition(WindState.class, Set.of(FireState.class));
        this.stateManager.addTransition(FireState.class, Set.of(LightningState.class));
        this.stateManager.addTransition(LightningState.class, Set.of(WindState.class));
    }

    @Override
    public String changeState(GameMap map) {
        return this.stateManager.handleStateTransition(map);
    }

    @Override
    public String changeWeapon(IntrinsicWeapon weapon) {
        if (this.getIntrinsicWeapon().getClass() != weapon.getClass()) {
            this.setIntrinsicWeapon(weapon);
            return this + " equipped " + weapon + ".";
        }
        return "";
    }

    /**
     * Enables an ability for the creature
     *
     * @param ability The ability to be enabled
     * @return The outcome of enabling the ability.
     */
    @Override
    public String enableCreatureAbility(Abilities ability) {
        String outcome = "";
        if (!this.hasAbility(ability))
            outcome = this + " now has " + ability + ".";
        return outcome;
    }

    /**
     * Disables an ability for the creature
     *
     * @param ability The ability to be disabled
     * @return The outcome of disabling the ability.
     */
    @Override
    public String disableCreatureAbility(Abilities ability) {
        String outcome = "";
        if (this.hasAbility(ability))
            outcome = ability + " is removed from " + this + ".";
        return outcome;
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
        for (Behaviour behaviour : this.behaviours.values()) {
            Action action = behaviour.generateAction(this, map);
            if (action != null) {
                return action;
            }
        }
        return lastAction.getNextAction();
    }
}
//test