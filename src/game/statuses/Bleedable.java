package game.statuses;
/**
 * Represents classes that are bleedable
 * @author Johan
 */
public interface Bleedable {
    /**
     * Harms the class object by a certain amount of damage.
     * @param damage    The damage applied to the class object.
     */
    void bleed(int damage);
}
