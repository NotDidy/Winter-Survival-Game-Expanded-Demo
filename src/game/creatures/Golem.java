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
import game.creatures.creatureStates.CreatureState;
import game.creatures.creatureStates.CrystalState;
import game.creatures.creatureStates.StateManager;
import game.statuses.StateChangeCooldown;
import game.weapons.BareFist;

/**
 * Represents Golem in the game.
 * @author Jeffrey Lam Po Yuen (35306750)
 * @version 1.1.0
 * @since 1.1.0
 */
public class Golem extends Creature implements StatefulCreature{
    private final StateManager stateManager;
    /**
     * Golem constructor
     * @param name          Name of the Golem
     * @param displayChar   The display character of the Golem in game
     * @param hitPoints     The hit points of the Golem
     */
    public Golem(String name, char displayChar, int hitPoints) {
        super("Golem", 'G', 300);
        this.enableAbility(Abilities.CAN_ATTACK);
        this.addBehaviour(10, new StateBehaviour(this));
        this.addBehaviour(30, new AttackBehaviour());
        this.setIntrinsicWeapon(new BareFist());
        this.addStatus(new StateChangeCooldown());
        CreatureState creatureState = new CrystalState();
        this.stateManager = new StateManager(this, creatureState);
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
        if (!this.hasAbility(ability)) {
            this.enableAbility(ability);
            outcome = this + " now has " + ability + ".";
        }
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
        if (this.hasAbility(ability)) {
            this.disableAbility(ability);
            outcome = ability + " is removed from " + this + ".";
        }
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

