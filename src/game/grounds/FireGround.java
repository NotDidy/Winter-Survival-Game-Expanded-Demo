package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Flammable;
import game.statuses.BurningStatus;

/**
 * Represents a patch of ground that is on fire.
 * FireGround burns any actor standing on it by applying a {@link BurningStatus}.
 * It persists for a fixed number of turns, after which it transforms into {@link DirtGround}.
 *
 * @author Wern Zhong Lei
 * @version 1.0
 * @since 1.0
 */
public class FireGround extends Ground {
    private int turnsRemaining;

    /**
     * FireGround constructor.
     * @param turnsRemaining    The number of turns remaining for the FireGround tick span.
     */
    public FireGround(int turnsRemaining) {
        super('^', "Fire Ground");
        this.turnsRemaining = turnsRemaining;
    }

    /**
     * Called once per game tick to update the state of the fire.
     *
     *     <li>Applies {@link BurningStatus} to any actor on this ground.</li>
     *     <li>Decreases the remaining duration of the fire.</li>
     *     <li>Transforms into {@link DirtGround} when the fire expires.</li>
     *
     * @param location the {@link Location} of this ground on the map
     */
    @Override
    public void tick(Location location) {
        turnsRemaining--;
        //change fireGround to dirtGround
        if (turnsRemaining == 0) {
            location.setGround(new DirtGround());
        } else {
            if (location.containsAnActor()) {
                // Dependency on Flammable
                Flammable flammableActor = location.getActorAs(Flammable.class);
                if (flammableActor != null)
                    // Dependency on Actor
                    location.getActor().addStatus(new BurningStatus(flammableActor,5, 5));
            }
        }
    }
}
