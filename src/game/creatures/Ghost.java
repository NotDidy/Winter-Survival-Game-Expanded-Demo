package game.creatures;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Abilities;
import game.behaviors.AttackBehaviour;
import game.behaviors.WanderBehaviour;
import game.items.CursedWeapon;

/**
 * A hostile ghost that spawns when a cursed weapon is held too long.
 * The ghost attacks the player and vanishes upon defeat, lifting the curse.
 *
 * @author Johan
 */
public class Ghost extends Creature {

    private static final int ATTACK_PRIORITY = 30;

    /**
     * Constructor for Ghost.
     */
    public Ghost(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        // Behaviors: follows and attacks player
        this.addBehaviour(ATTACK_PRIORITY, new AttackBehaviour()); // prioritize chasing player
        this.addBehaviour(50, new WanderBehaviour()); // wander if no target nearby
    }




}
