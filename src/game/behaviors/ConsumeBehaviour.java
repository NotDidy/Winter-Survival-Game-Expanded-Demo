package game.behaviors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ConsumeAction;
import game.items.Consumable;

import java.util.ArrayList;
import java.util.List;

public class ConsumeBehaviour implements Behaviour {
    /**
     * Generates Consume Action for the actor if there is a Consumable on the ground.
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return an Action that actor can perform, or null if actor can't do this.
     * @author John Devadhas (34380507)
     */
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        List<Action> actions = new ArrayList<>();
        List<Consumable> consumables = map.locationOf(actor).getItemsAs(Consumable.class);
        for (Consumable consumable : consumables) {
            actions.add(new ConsumeAction(consumable));
        }
        if (!actions.isEmpty()) {
            return actions.get((int) (Math.random() * actions.size()));
        }
        return null;
    }
}
