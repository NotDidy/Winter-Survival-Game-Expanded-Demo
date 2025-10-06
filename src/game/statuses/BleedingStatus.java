package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;


public class BleedingStatus implements Status {
    private final int damage;
    private int turnsRemaining;
    private final Bleedable bleedable;

    public BleedingStatus(Bleedable bleedable, int tickAmount, int damage){
        this.damage = damage;
        this.turnsRemaining = tickAmount;
        this.bleedable = bleedable;
    }

    @Override
    public boolean isStatusActive() {
        return this.getTurnsRemaining() > 0;
    }

    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        //apply bleed damage
        this.turnsRemaining--;
        this.bleedable.bleed(damage);
    }

    public int getTurnsRemaining(){
        return turnsRemaining;
    }
}
