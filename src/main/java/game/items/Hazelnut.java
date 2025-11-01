package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ConsumeAction;

/**
 * Hazelnut item that can be consumed by the Explorer.
 * Restores 3 HP and reduces thirst by 2.
 */
public class Hazelnut extends Item implements Consumable {
    private static final int INCREASE_TOTAL_HEALTH = 1;

    /**
     * Hazelnut constructor.
     */
    public Hazelnut() {
        super("Hazelnut", 'n', true);

    }

    /**
     * Generates actions that can be executed by the actor that has the Hazelnut.
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
     * Processes the consumption of the Hazelnut object by an actor.
     * @param actor     The actor that consumes the Hazelnut
     * @return          The result of consuming the Hazelnut.
     */
    @Override
    public String consumedBy(Actor actor) {
        actor.modifyStatsMaximum(BaseAttributes.HEALTH, ActorAttributeOperation.INCREASE, INCREASE_TOTAL_HEALTH);
        actor.removeItemFromInventory(this);
        return actor + " consumes " + this + " and increases total health by " + INCREASE_TOTAL_HEALTH + " point.";
    }
}
