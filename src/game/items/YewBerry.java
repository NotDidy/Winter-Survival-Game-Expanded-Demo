package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ConsumeAction;

/**
 * YewBerry item that can be consumed by the Explorer.
 * Restores 3 HP and reduces thirst by 2.
 */
public class YewBerry extends Item implements Consumable {
    private static final int ACTOR_HEALTH = 0;

    /**
     * YewBerry constructor.
     */
    public YewBerry() {
        super("YewBerry", 'x', true);

    }

    /**
     * Generates actions that can be executed by the actor that has the YewBerry.
     * @param owner the actor that owns the item
     * @param map the map where the actor is performing the action on
     * @return An ActionList object containing all possible actions.
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();
        actions.add(new ConsumeAction(this));
        return actions;
    }

    /**
     * Processes the consumption of the YewBerry object by an actor which kills them.
     * @param actor     The actor that consumes the YewBerry
     * @return          The result of consuming the YewBerry.
     */
    @Override
    public String consumedBy(Actor actor) {
        actor.modifyAttribute(BaseAttributes.HEALTH, ActorAttributeOperation.UPDATE, ACTOR_HEALTH);
        actor.removeItemFromInventory(this);
        return actor + " consumes " + this + ". ";
    }
}
