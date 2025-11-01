package game.creatures;

import game.behaviors.AttackBehaviour;
import game.weapons.Claw;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import game.items.YewBerry;
import game.spawning.DropItemAtEachExitWithChance;

/**
 * Represents Bear in the game.
 */
public class Bear extends Creature {
    private static final int ATTACK_PRIORITY = 30;
    private static final int WARMTH_MAX = 50;

    /**
     * Bear constructor
     * @param name          Name of the Bear
     * @param displayChar   The display character of the bear in game
     * @param hitPoints     The hit points of the bear
     */
    public Bear(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addBehaviour(ATTACK_PRIORITY, new AttackBehaviour());
        this.setIntrinsicWeapon(new Claw());

        // Warmth level starts at 50
        this.addNewStatistic(CreatureAttributes.WARMTH, new BaseActorAttribute(WARMTH_MAX));
        this.addSpawnEffect(new DropItemAtEachExitWithChance(YewBerry::new, 0.5));
    }
}