package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Asleep Status class representation.
 *
 * @author John Devadhas
 * @version 1.1.0
 * @since 1.0.0
 */
public class StunnedStatus implements Status {
    public static final int DEFAULT_STUN_DURATION = 3;
    private int stunnedDuration;

    /**
     * Parameterised constructor for StunnedStatus class.
     * @param stunnedDuration The sleep duration of the class, status becomes inactive when stunnedDuration hits 0 and is removed from the GameEntity it's tied to.
     */
    public StunnedStatus (int stunnedDuration) {
        this.stunnedDuration = stunnedDuration;
    }
    /**
     * Indicates whether this status is still active.
     *
     * @return true if active, false otherwise
     */
    @Override
    public boolean isStatusActive() {
        return stunnedDuration > 0;
    }

    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        gameEntity.asCapability(Actor.class).ifPresent(actor -> {
            if(location.map().contains(actor)) {
                Display display = new Display();
                display.println(actor + " is stunned for " + this.stunnedDuration + " more turns!");
                this.stunnedDuration--;
                if (!actor.isConscious()) {
                    display.println(location.getActor().unconscious(location.map()));
                }
            }

        });
    }
}
