package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;

/**
 * Represents a patch of dirt ground in the game world.
 * DirtGround is a basic ground type that can be walked on by actors.
 * are replaced or burned (e.g., by {@link FireGround} or similar effects).
 *
 * @author Wern Zhong Lei
 * @version 1.0
 * @since 1.0
 */
public class DirtGround extends Ground implements HealActor {

    /**
     * Constructs a new {@code DirtGround}.
     * Sets the display character to '+' and the name to "Dirt Ground".
     */
    public DirtGround() {
        super('+', "Dirt Ground");
    }
}
