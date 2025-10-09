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
import game.weapons.BareFist;
import game.weapons.BodySlam;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents Golem in the game.
 * @author Jeffrey Lam Po Yuen (35306750), John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
 */
public class Golem extends Creature implements StatefulCreature{
    private final StateManager stateManager;
    private static final int STATE_PRIORITY = 10;
    private static final int ATTACK_PRIORITY = 30;
    /**
     * Golem constructor
     * @param name          Name of the Golem
     * @param displayChar   The display character of the Golem in game
     * @param hitPoints     The hit points of the Golem
     */
    public Golem(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.enableAbility(Abilities.CAN_ATTACK);
        this.addBehaviour(ATTACK_PRIORITY, new AttackBehaviour());
        this.addBehaviour(STATE_PRIORITY, new StateBehaviour(this));
        this.setIntrinsicWeapon(new BodySlam());
        this.addStatus(new StateChangeCooldown());
        CreatureState creatureState = new CrystalState();
        Set<CreatureState> creatureStatesSet = new HashSet<>();
        creatureStatesSet.add(creatureState);
        creatureStatesSet.add(new IronState());
        creatureStatesSet.add(new EarthState());
        this.stateManager = new StateManager(this, creatureState, creatureStatesSet);
        this.stateManager.addTransition(CrystalState.class, Set.of(IronState.class));
        this.stateManager.addTransition(IronState.class, Set.of(EarthState.class));
        this.stateManager.addTransition(EarthState.class, Set.of(CrystalState.class));
        System.out.println(stateManager);
    }

    /**
     * Processes state change.
     * @param map   The Gamemap the Golem is in.
     * @return      The outcome of changing the Golem state.
     */
    @Override
    public String changeState(GameMap map) {
        return this.stateManager.handleStateTransition(map);
    }

    /**
     * Processes weapon change.
     * @param weapon     The new weapon to be equipped.
     * @return           The outcome of changing the Golem's weapon.
     */
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

