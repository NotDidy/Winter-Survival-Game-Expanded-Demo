package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
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
