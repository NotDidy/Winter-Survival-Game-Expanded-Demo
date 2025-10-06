package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;
import game.Flammable;

/**
 * Add description
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class BurningStatus implements Status {
    private int damage;
    private int turnsRemaining;
    private final Flammable flammable;

    public BurningStatus(Flammable flammable, int tickAmount, int damage){
        this.damage = damage;
        this.turnsRemaining = tickAmount;
        this.flammable = flammable;
    }

    @Override
    public boolean isStatusActive() {
        return this.getTurnsRemaining() > 0;
    }

    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        Display display = new Display();
        //apply burn damage

        this.flammable.burn(damage);
        this.turnsRemaining--;
        display.println(this.flammable + " burns for " + this.damage + " damage. Duration left: " + this.turnsRemaining + " turns");
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
