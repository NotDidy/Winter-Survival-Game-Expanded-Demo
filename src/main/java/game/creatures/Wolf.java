package game.creatures;

import game.behaviors.AttackBehaviour;
import game.weapons.Bite;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;

/**
 * Class that represents Wolf animal.
 */
public class Wolf extends Creature {
    private static final int ATTACK_PRIORITY = 30;
    private static final int WARMTH_MAX = 25;

    /**
     * Wolf constructor
     *
     * @param name        Name of the wolf
     * @param displayChar The display character of the wolf in game
     * @param hitPoints   The hit points of the wolf
     */
    public Wolf(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addBehaviour(ATTACK_PRIORITY, new AttackBehaviour());
        this.setIntrinsicWeapon(new Bite());

        // Warmth starts at 25
        this.addNewStatistic(CreatureAttributes.WARMTH, new BaseActorAttribute(WARMTH_MAX));


        this.addSpawnEffect(new game.spawning.GrowGroundAtOneExit(() -> {
            var tree = new game.grounds.trees.YewBerryTree();
            tree.enableProximityMode();
            return tree;
        }));
    }
}