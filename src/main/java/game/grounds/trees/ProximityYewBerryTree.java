package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.items.YewBerry;

/**
 * A mature YewBerry tree variant used when a Wolf is spawned (REQ2).
 * Drops a yewberry if an actor is within surrounding locations (no 5-turn cycle).
 */
public class ProximityYewBerryTree extends Ground {
    public ProximityYewBerryTree() {
        super('Y', "YewBerry Tree (Proximity)");
        this.enableAbility(TreeAbilities.CAN_PRODUCE);
    }

    @Override
    public void tick(Location location) {
        if (!this.hasAbility(TreeAbilities.CAN_PRODUCE)) return;

        boolean actorNearby = false;
        for (Exit exit : location.getExits()) {
            if (exit.getDestination().containsAnActor()) {
                actorNearby = true;
                break;
            }
        }
        if (actorNearby) {
            // drop one berry in a random exit
            if (!location.getExits().isEmpty()) {
                Exit e = location.getExits().get((int) (Math.random() * location.getExits().size()));
                e.getDestination().addItem(new YewBerry());
            }
        }
    }
}
