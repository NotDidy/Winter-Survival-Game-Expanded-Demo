package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.CoatWeaponAction;
import game.coatings.Coatable;
import game.coatings.SnowCoating;

/**
 * A class representing snow on the ground.
 * @author Adrian Kristanto
 */
public class Snow extends Ground {
    public Snow() {
        super('.', "Snow");
    }
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();


        if (!location.containsAnActor() || location.getActor() != actor) {
            return actions;
        }


        for (Item item : actor.getItemInventory()) {
            item.asCapability(Coatable.class).ifPresent(coatable -> {
                actions.add(new CoatWeaponAction(coatable, new SnowCoating(), null));
            });
        }

        return actions;
    }
}
