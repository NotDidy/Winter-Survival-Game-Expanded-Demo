package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;
import game.Flammable;

/**
 * Add description
 * @author John Devadhas (34380507), Wern Zhong Lei
 * @version 1.2.0
 * @since 1.2.0
 */
public class BurningStatus implements Status {
    public static final int DEFAULT_BURN_DURATION = 7;
    public static final int DEFAULT_DAMAGE = 3;
    private final int damage;
    private int turnsRemaining;
    private final Flammable flammable;

    /**
     * Constructor.
     * @param flammable     The Flammable object that is burning.
     * @param tickAmount    How many turns the FLammable is burning for.
     * @param damage        The damage inflicted at each turn.
     */
    public BurningStatus(Flammable flammable, int tickAmount, int damage){
        this.damage = damage;
        this.turnsRemaining = tickAmount;
        this.flammable = flammable;
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
        gameEntity.asCapability(Actor.class).ifPresent(actor -> {
            if(location.map().contains(actor)) {
                Display display = new Display();
                //apply burning damage
                this.flammable.burn(damage);
                this.turnsRemaining--;
                display.println(this.flammable + " burns for " + this.damage + " damage. Duration left: " + this.turnsRemaining + " turns");
                if (!actor.isConscious()) {

                    display.println(location.getActor().unconscious(location.map()));
                }
            }

        });
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
