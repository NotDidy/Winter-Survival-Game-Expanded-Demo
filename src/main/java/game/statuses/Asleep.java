package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Asleep Status class representation.
 *
 * @author John Devadhas
 * @version 1.1.0
 * @since 1.0.0
 */
public class Asleep implements Status {
    private int sleepDuration;

    /**
     * Parameterised constructor for Asleep class.
     * @param sleepDuration The sleep duration of the class, status becomes inactive when sleepDuration hits 0 and is removed from the GameEntity it's tied to.
     */
    public Asleep (int sleepDuration) {
        this.sleepDuration = sleepDuration;
    }
    /**
     * Indicates whether this status is still active.
     *
     * @return true if active, false otherwise
     */
    @Override
    public boolean isStatusActive() {
        return sleepDuration > 0;
    }

    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        sleepDuration--;
    }
}
