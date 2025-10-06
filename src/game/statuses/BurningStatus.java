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
    private static final int TICK_AMOUNT = 5;
    private static final int DAMAGE = 5;
    private int turnsRemaining;
    private final Actor target;

    public BurningStatus(Actor target){
        this.turnsRemaining = TICK_AMOUNT;
        this.target = target;

    }

    @Override
    public boolean isStatusActive() {
        return this.getTurnsRemaining() > 0;
    }

    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        //apply burn damage
        this.turnsRemaining--;
        target.hurt(DAMAGE);
    }

    public int getTurnsRemaining(){
        return turnsRemaining;
    }
}
