package game.statuses;
/**
 * Represents classes that are able to be afflicted with frostbite
 * @author Johan
 */
public interface Frostbittenable {
    /**
     * Harms the class object by a certain amount of damage.
     *
     * @param damage The damage applied to the class object.
     */
    void frostbite(int damage);
}