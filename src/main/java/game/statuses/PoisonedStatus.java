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
public class PoisonedStatus implements Status {
    public static final int DEFAULT_POISON_DURATION = 5;
    public static final int DEFAULT_DAMAGE = 4;
    private final int damage;
    private int turnsRemaining;
    private final Poisonable poisonable;

    /***
     * Constructor.
     *  @param poisonable the actor that is being afflicted with poison
     * @param tickAmount duration of the status
     * @param damage damage that the status inflicts onto the actor
     */
    public PoisonedStatus(Poisonable poisonable, int tickAmount, int damage){
        this.damage = damage;
        this.turnsRemaining = tickAmount;
        this.poisonable = poisonable;
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
     * Applies status effect onto the actor
     * @param gameEntity the actor that has the status
     * @param location location of the actor that has the  status
     */
    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        gameEntity.asCapability(Actor.class).ifPresent(actor -> {
            if(location.map().contains(actor)) {
                Display display = new Display();
                //apply poison damage
                this.poisonable.poison(damage);
                this.turnsRemaining--;
                display.println(this.poisonable + " is poisoned for " + this.damage + " damage. Duration left: " + this.turnsRemaining + " turns");
                if (!actor.isConscious()) {

                    display.println(location.getActor().unconscious(location.map()));
                }
            }

        });
    }

    public int getTurnsRemaining(){
        return turnsRemaining;
    }
}
