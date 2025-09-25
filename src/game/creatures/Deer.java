package game.creatures;

/**
 * Represents Deer in game.
 * @author John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.1.0
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
    }
}
