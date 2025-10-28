package game.creatures;

import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.behaviors.AttackBehaviour;

/**
 * Represents Crocodile in game.
 * REQ2: HP 300, warmth starts 55, wanders, bites for 80 dmg at 75% hit.
 * Unconscious (removed) when warmth reaches 0 (handled by Creature.playTurn()).
 */
public class Crocodile extends Creature {
    private static final int ATTACK_PRIORITY = 30;
    private static final int WARMTH_START = 55;

    public Crocodile(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addBehaviour(ATTACK_PRIORITY, new AttackBehaviour());
        // intrinsic bite: 80 dmg, 75% hit
        this.setIntrinsicWeapon(new IntrinsicWeapon(80, "bites", 75, "bite"));
        // warmth starts at 55
        this.addNewStatistic(CreatureAttributes.WARMTH, new BaseActorAttribute(WARMTH_START));
    }

    // convenience constructor with defaults
    public Crocodile() {
        this("Crocodile", '<', 300);
    }
}
