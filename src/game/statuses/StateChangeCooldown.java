package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;

/**
 *
 */
public class StateChangeCooldown implements Status {
    private static final int COOLDOWN_TIMER = 3;
    private int currentTimer;

    /**
     *
     */
    public StateChangeCooldown () {
        this.currentTimer = COOLDOWN_TIMER;
    }


    /**
     * Indicates whether this status is still active.
     *
     * @return true if active, false otherwise
     */
    @Override
    public boolean isStatusActive() {
        return currentTimer > 0;
    }

    /**
     *
     * @param gameEntity the entity this status is attached to
     * @param location  The location
     */
    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        currentTimer--;
    }
}
