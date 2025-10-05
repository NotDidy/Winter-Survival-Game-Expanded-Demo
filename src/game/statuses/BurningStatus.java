package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;
import game.Flammable;

/**
 * Add description
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class BurningStatus implements Status {
    private int DAMAGE;
    private int turnsRemaining;
    private final Flammable flammable;

    public BurningStatus(Flammable flammable, int TICK_AMOUNT, int DAMAGE){
        this.DAMAGE = DAMAGE;
        this.turnsRemaining = TICK_AMOUNT;
        this.flammable = flammable;
    }

    public void stackEffect(int additionalDuration, int additionalDamage) {
        this.turnsRemaining += additionalDuration;
        this.DAMAGE += additionalDamage;
    }

    @Override
    public boolean isStatusActive() {
        return this.getTurnsRemaining() > 0;
    }

    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        //apply burn damage
        this.turnsRemaining--;
        this.flammable.burn(DAMAGE);

        gameEntity.asCapability(Actor.class).ifPresent(actor -> {
            if (!actor.isConscious()) {
                System.out.println(actor + " was burned to ashes!");
                location.map().removeActor(actor);
            }
        });




    }

    public int getTurnsRemaining(){
        return turnsRemaining;
    }
}
