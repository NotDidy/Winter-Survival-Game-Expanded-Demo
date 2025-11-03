package game.creatures;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.behaviors.AttackBehaviour;
import game.behaviors.WanderBehaviour;
import game.items.CursedItem;
import game.items.CursedSword;
import game.weapons.Claw;

/**
 * A hostile ghost that spawns when a cursed weapon is held too long.
 * The ghost attacks the player and vanishes upon defeat, lifting the curse.
 *
 * @author Muhammad Johan Arief (33523061)
 * @version 1.3.0
 * @since 1.3.0
 */
public class Ghost extends Creature {

    private static final int ATTACK_PRIORITY = 30;
    private static final int WANDER_PRIORITY = 50;
    private final CursedItem linkedWeapon;


    /**
     * Constructor
     * @param name
     * @param displayChar
     * @param hitPoints
     * @param cursedWeapon
     */
    public Ghost(String name, char displayChar, int hitPoints, CursedSword cursedWeapon) {
        super(name, displayChar, hitPoints);
        this.addBehaviour(ATTACK_PRIORITY, new AttackBehaviour());
        this.addBehaviour(WANDER_PRIORITY, new WanderBehaviour());
        this.setIntrinsicWeapon(new Claw());
        this.linkedWeapon = cursedWeapon;
    }

    /**
     * When the ghost dies, it drops the cursed weapon again and resets its curse. (died to status effects)
     * @param map where the ghost fell unconscious
     */
    @Override
    public String unconscious(GameMap map) {


        // Drop the cursed weapon at the ghost’s current location
        if (linkedWeapon != null) {
            map.locationOf(this).addItem(linkedWeapon);
            linkedWeapon.liftCurse();
        }

        // Remove the ghost from the map
        map.removeActor(this);
        return this + " vanishes, the cursed weapon is dropped onto the ground.";
    }

    /**
     * When the ghost dies, it drops the cursed weapon again and resets its curse.
     * @param map where the ghost fell unconscious
     * @param actor the attacker
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {


        // Drop the cursed weapon at the ghost’s current location
        if (linkedWeapon != null) {
            map.locationOf(this).addItem(linkedWeapon);
            linkedWeapon.liftCurse();
        }

        // Remove the ghost from the map
        map.removeActor(this);
        return this + " vanishes, the cursed weapon is dropped onto the ground.";
    }
}



