package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Frostbitten status that is applied onto other actors and gradually reduces the actors health.
 *
 * @author Johan
 */
public class FrostbittenStatus implements Status {
    private final int warmthPoints;
    private int turnsRemaining;
    private final Frostbittenable frostbittenable;

    /***
     * Constructor.
     *  @param frostbittenable the actor that is being afflicted to bleeding
     * @param tickAmount duration of the status
     * @param warmthPoints damage that the status inflicts onto the actor
     */
    public FrostbittenStatus(Frostbittenable frostbittenable, int tickAmount, int warmthPoints){
        this.warmthPoints = warmthPoints;
        this.turnsRemaining = tickAmount;
        this.frostbittenable = frostbittenable;
    }

    /**
     * Checks if the status duration is still active
     * @return A boolean value
     */
    @Override
    public boolean isStatusActive() {
        return this.getTurnsRemaining() > 0;
    }

    /**
     * Applies the bleeding damage onto the actor that has the bleeding status
     * @param gameEntity the actor that has the bleeding status
     * @param location location of the actor that has the bleeding status
     */
    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        Display display = new Display();
        //apply poison damage
        this.frostbittenable.frostbite(warmthPoints);
        this.turnsRemaining--;
        display.println(this.frostbittenable + " reduced its warmth by " + this.warmthPoints + " points. Duration left: " + this.turnsRemaining + " turns");
        gameEntity.asCapability(Actor.class).ifPresent(actor -> {
            if (!actor.isConscious()) {
                display.println(location.getActor().unconscious(location.map()));
            }
        });
    }

    public int getTurnsRemaining(){
        return turnsRemaining;
    }
}
