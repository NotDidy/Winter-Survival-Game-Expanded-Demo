package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.statuses.BurningStatus;

public class FireGround extends Ground {
    private int turnsRemaining;

    public FireGround() {
        super('^', "Fire Ground");
        this.turnsRemaining = 3;
    }

    @Override
    public void tick(Location location) {
        //reduce tick
        tick(location);
        turnsRemaining--;

        //change fireGround to dirtGround
        if (turnsRemaining <= 0) {
            location.setGround(new DirtGround());
        }
    }

    public boolean canActorEnter(Actor actor) {
        if (actor != null) {
            actor.addStatus(new BurningStatus());
        }
        return true;
    }

    /**
     * Apply burning status to an actor
     * @param actor The actor to burn
     */
    public void applyBurningStatus(Actor actor) {
        //check if actor has burning status
        BurningStatus existingStatus = getBurningStatus(actor);

        if (existingStatus != null) {
            //stack burning effect
            existingStatus.addStack();
        }else {
            //apply new burning status
            BurningStatus burningStatus = new BurningStatus();
            actor.addStatus(burningStatus);
        }
    }

    /**
     * Get existing burning status from actor
     * @param actor The actor to check
     * @return The burning status or null if not found
     */
    private BurningStatus getBurningStatus(Actor actor) {
        for (Object status : actor.statuses()) {
            if (status instanceof BurningStatus) {
                return (BurningStatus) status;
            }
        }
        return null;
    }
}
