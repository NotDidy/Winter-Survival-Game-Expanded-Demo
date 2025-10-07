package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;


/**
 * Represents a status effect that sets an {@link Actor} on fire.
 * While active, the burning actor takes damage each tick.
 * The status lasts for a fixed number of ticks.
 *
 * @author Wern Zhong Lei
 * @version 1.0
 * @since 1.0
 */
public class BurningStatus implements Status {
    //number of ticks the burn last
    private static final int TICK_AMOUNT = 5;
    //damage dealt per tick
    private static final int DAMAGE = 5;
    //remaining turns for this status
    private int turnsRemaining;
    //actor affected by burn status
    private final Actor target;


    /**
     * Constructs a new {@code BurningStatus} for the given actor.
     *
     * @param target the actor to apply burn damage to
     */
    public BurningStatus(Actor target){
        this.turnsRemaining = TICK_AMOUNT;
        this.target = target;

    }

    /**
     * Checks whether the burning status is still active.
     *
     * @return {@code true} if there are remaining ticks; {@code false} otherwise
     */
    @Override
    public boolean isStatusActive() {
        return this.getTurnsRemaining() > 0;
    }

    /**
     * Called once per tick to apply burning damage to the actor.
     *
     * @param gameEntity the game entity (expected to be an actor)
     * @param location the location of the actor (not used)
     */
    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        //apply burn damage
        this.turnsRemaining--;
        target.hurt(DAMAGE);
    }

    /**
     * Returns the number of ticks remaining for this status.
     *
     * @return remaining ticks
     */
    public int getTurnsRemaining(){
        return turnsRemaining;
    }
}
