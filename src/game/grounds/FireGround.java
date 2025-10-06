package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Flammable;
import game.statuses.BurningStatus;

public class FireGround extends Ground {
    //extinguish on the 4th tick so it burns for "3" tick
    private int turnsRemaining = 4;

    public FireGround() {
        super('^', "Fire Ground");
    }

    @Override
    public void tick(Location location) {
        //burn any actor on fireGround
        if (location.containsAnActor()) {
            Actor actor = location.getActor();
            actor.addStatus(new BurningStatus(actor));
        }

        //countdown fire duration
        turnsRemaining--;

        //change fireGround to dirtGround
        if (turnsRemaining <= 0) {
            location.setGround(new DirtGround());
        }
    }
}
