package game.creatures;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.StateAction;
import game.actors.Abilities;
import game.behaviors.AttackBehaviour;
import game.creatures.creatureStates.CreatureState;
import game.creatures.creatureStates.WindState;
import game.statuses.StateChangeCooldown;
import game.weapons.Claw;

/**
 * Represents Dragon in the game.
 * @author Jeffrey Lam Po Yuen (35306750)
 * @version 1.1.0
 * @since 1.1.0
 */
public class Dragon extends Creature implements StatefulCreature{
    private CreatureState currentState;
    /**
     * Dragon constructor
     * @param name          Name of the Dragon
     * @param displayChar   The display character of the Dragon in game
     * @param hitPoints     The hit points of the Dragon
     */
    public Dragon(String name, char displayChar, int hitPoints) {
        super("Dragon", 'D', 200);
        this.enableAbility(Abilities.CAN_ATTACK);
        this.addBehaviour(30, new AttackBehaviour());
        this.setIntrinsicWeapon(new Claw());
        this.addStatus(new StateChangeCooldown());
        this.currentState = new WindState();
    }

    /**
     * Updates the current state of the creature.
     * The CreatureState implementations will use this method to perform a transition.
     *
     * @param newState      The new CreatureState of the Dragon.
     */
    @Override
    public void setCurrentState(CreatureState newState) {
        this.currentState = newState;
    }

    @Override
    public String changeState() {
        return this.currentState.handleStateTransition(this);
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
        if (!this.hasStatus(StateChangeCooldown.class)) {
            this.addStatus(new StateChangeCooldown());
            return new StateAction(this);
        }
        for (Behaviour behaviour : this.behaviours.values()) {
            Action action = behaviour.generateAction(this, map);
            if (action != null) {
                return action;
            }
        }
        return lastAction.getNextAction();
    }
}
