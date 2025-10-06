package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Bleeding status that is applied onto other actors and gradually reduces the actors health.
 *
 * @author Johan
 */
public class BleedingStatus implements Status {
    private final int damage;
    private int turnsRemaining;
    private final Bleedable bleedable;

    /***
     * Constructor.
     *  @param bleedable the actor that is being afflicted to bleeding
     * @param tickAmount duration of the status
     * @param damage damage that the status inflicts onto the actor
     */
    public BleedingStatus(Bleedable bleedable, int tickAmount, int damage){
        this.damage = damage;
        this.turnsRemaining = tickAmount;
        this.bleedable = bleedable;
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
        //apply bleed damage
        this.bleedable.bleed(damage);
        this.turnsRemaining--;
        //display.println(this.bleedable + " bleeds for " + this.damage + " damage. Duration left: " + this.turnsRemaining + " turns");
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
