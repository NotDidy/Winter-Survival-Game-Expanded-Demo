package game.creatures;

import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;

/**
 * Represents Deer in game.
 */
public class Deer extends Creature {

    /**
     * Deer constructor.
     *
     * @param name          Name of the deer.
     * @param displayChar   The displayCharacter of the deer.
     * @param hitPoints     The hitpoint amount of the deer
     */
    public Deer(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);

        // Warmth level starts at 10
        this.addNewStatistic(CreatureAttributes.WARMTH, new BaseActorAttribute(10));
    }
}
