package game.statuses;
/**
 * Represents classes that are poisonable
 * @author Johan
 */
public interface Poisonable {
    /**
     * Harms the class object by a certain amount of damage.
     *
     * @param damage The damage applied to the class object.
     */
    void poison(int damage);
}