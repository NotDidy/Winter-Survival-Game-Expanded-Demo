package game.creatures;

import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import game.behaviors.AttackBehaviour;
import game.spawning.PoisonActorsInSurroundings;
import game.weapons.CrocBite;

/**
 * Crocodile class representation
 * @author Ryu
 * @version 1.3.0
 * @since 1.3.0
 */
public class Crocodile extends Creature {
    private static final int ATTACK_PRIORITY = 30;
    private static final int WARMTH_START = 55;

    /**
     * Default Crocodile constructor with standard attributes.
     * <p>
     * Uses predefined name, display character, and health values.
     * </p>
     */

    public Crocodile() {
        this("Crocodile", '<', 300);
    }

    /**
     * Constructs a new Crocodile with a specified name, display character, and health.
     * <p>
     * Registers its attack behaviour, assigns its intrinsic weapon, initialises
     * warmth statistics, and defines its poison spawn effect.
     * </p>
     *
     * @param name        the name of the Crocodile.
     * @param displayChar the character used to represent the Crocodile on the map.
     * @param hitPoints   the Crocodile's initial hit points.
     */

    public Crocodile(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);

        // Define attack behaviour and intrinsic weapon
        this.addBehaviour(ATTACK_PRIORITY, new AttackBehaviour());
        this.setIntrinsicWeapon(new CrocBite());
        // Add custom warmth statistic
        this.addNewStatistic(CreatureAttributes.WARMTH, new BaseActorAttribute(WARMTH_START));
        // Apply spawn effect: poisons nearby actors when spawned
        this.addSpawnEffect(new PoisonActorsInSurroundings(1, 3, 10));

    }
}