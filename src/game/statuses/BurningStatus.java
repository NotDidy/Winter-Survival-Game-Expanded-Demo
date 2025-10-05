package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;
import game.Flammable;

public class BurningStatus implements Status {
    private static final int TICK_AMOUNT = 5;
    private static final int DAMAGE = 5;
    private int turnsRemaining;
    private final Flammable flammable;

    public BurningStatus(Flammable flammable){
        this.turnsRemaining = TICK_AMOUNT;
        this.flammable = flammable;
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
    }

    public int getTurnsRemaining(){
        return turnsRemaining;
    }
}
