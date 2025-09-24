package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Represents a GameEntity being unripe
 */
public class Unripe implements Status {
    private int tickDuration;

    /**
     * Parameterised constructor for Unripe class.
     * @param tickDuration The tick duration of the class, status becomes inactive when tick hits 0 and is removed from the GameEntity it's tied to.
     */
    public Unripe(int tickDuration) {
        this.tickDuration = tickDuration;
    }
    /**
     * Indicates whether this status is still active.
     *
     * @return true if active, false otherwise
     */
    @Override
    public boolean isStatusActive() {
        return tickDuration > 0;
    }

    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        tickDuration--;
    }
}
