package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Flammable;
import game.statuses.BurningStatus;

public class FireGround extends Ground {
    private int turnsRemaining;

    public FireGround() {
        super('^', "Fire Ground");
        this.turnsRemaining = 3;
    }

    @Override
    public void tick(Location location) {
        turnsRemaining--;
        //change fireGround to dirtGround
        if (turnsRemaining == 0) {
            location.setGround(new DirtGround());
        } else {
            if (location.containsAnActor()) {
                // Dependency on Flammable
                Flammable flammableActor = location.getActorAs(Flammable.class);
                if (flammableActor != null)
                    // Dependency on Actor
                    location.getActor().addStatus(new BurningStatus(flammableActor,5, 5));
            }
        }
    }
}
