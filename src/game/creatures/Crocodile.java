package game.creatures;

import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import game.behaviors.AttackBehaviour;
import game.spawning.PoisonActorsInSurroundings;
import game.weapons.CrocBite;

public class Crocodile extends Creature {

    /** The execution priority for Crocodile's attack behaviour. */
    private static final int ATTACK_PRIORITY = 30;


    /** The initial warmth attribute value for the Crocodile. */
    private static final int WARMTH_START = 55;

    /**
     * Constructs a new Crocodile with a specified name, display character, and health.
     * <p>
     * Registers its attack behaviour, assigns its intrinsic weapon, initialises
     * warmth statistics, and defines its poison spawn effect.
     * </p>
     *
     * @param name         the name of the Crocodile.
     * @param displayChar  the character used to represent the Crocodile on the map.
     * @param hitPoints    the Crocodile's initial hit points.
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
    /**
     * Default Crocodile constructor with standard attributes.
     * <p>
     * Uses predefined name, display character, and health values.
     * </p>
     */

    public Crocodile() {
        this("Crocodile", '<', 300);
    }
}
