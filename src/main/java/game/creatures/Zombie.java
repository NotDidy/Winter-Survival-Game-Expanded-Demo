package game.creatures;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.behaviors.AttackBehaviour;
import game.behaviors.WanderBehaviour;
import game.items.CursedAmulet;
import game.items.CursedItem;
import game.weapons.Claw;

/**
 * A creature that spawns if player has the cursed amulet within their inventory for more than 5 turns
 * @author Muhammad Johan Arief (33523061)
 * @version 1.3.0
 * @since 1.3.0
 */
public class Zombie extends Creature {
    private static final int ATTACK_PRIORITY = 30;
    private static final int WANDER_PRIORITY = 50;
    private final CursedItem linkedAmulet;

    /**
     * Constructor
     * @param name
     * @param displayChar
     * @param hitPoints
     * @param amulet
     */
    public Zombie(String name, char displayChar, int hitPoints, CursedAmulet amulet) {
        super(name, displayChar, hitPoints);
        this.addBehaviour(ATTACK_PRIORITY, new AttackBehaviour());
        this.addBehaviour(WANDER_PRIORITY, new WanderBehaviour());
        this.setIntrinsicWeapon(new Claw());
        this.linkedAmulet = amulet;
    }

    /**
     * Resets CursedAmulet tick timer and drops cursed amulet on the ground (if died to status effects)
     * @param map where the actor fell unconscious
     * @return display output
     */
    @Override
    public String unconscious(GameMap map) {
        if (linkedAmulet != null) {
            map.locationOf(this).addItem(linkedAmulet);
            linkedAmulet.liftCurse();
        }

        map.removeActor(this);
        return this + " vanishes, the cursed amulet is dropped onto the ground.";
    }

    /**
     * Resets CursedAmulet tick timer and drops cursed amulet on the ground
     * @param actor the attacker
     * @param map where the actor fell unconscious
     * @return
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {
        if (linkedAmulet != null) {
            map.locationOf(this).addItem(linkedAmulet);
            linkedAmulet.liftCurse();
        }
        map.removeActor(this);
        return this + " vanishes, the cursed amulet is dropped onto the ground.";
    }
}
