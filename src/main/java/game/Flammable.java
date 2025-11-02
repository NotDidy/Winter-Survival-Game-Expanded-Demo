package game;

/**
 * Represents classes that are flammable
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public interface Flammable {
    /**
     * Harms the class object by a certain amount of damage.
     * @param damage    The damage applied to the class object.
     */
    void burn(int damage);
}
