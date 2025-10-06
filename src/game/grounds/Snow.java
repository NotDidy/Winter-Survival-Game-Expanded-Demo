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

    /**
     * A list of allowable actions that the player is able to do when on top of snow
     * @param actor player
     * @param location player location
     * @param direction direction that the action will take place
     * @return A list of actions
     */
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
