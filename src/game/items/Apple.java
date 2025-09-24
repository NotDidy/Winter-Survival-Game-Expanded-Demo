package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ConsumeAction;
import game.actors.PlayerAttributes;

/**
 * Apple item that can be consumed by the Explorer.
 * Restores 3 HP and reduces thirst by 2.
 */
public class Apple extends Item implements Consumable {
    private static final int HEAL_AMOUNT = 3;
    private static final int HYDRATION_AMOUNT = 2;

    /**
     * Apple constructor.
     */
    public Apple() {
        super("Apple", 'a', true);

    }

    /**
     * Generates actions that can be executed by the actor that has the Apple.
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
     * Processes the consumption of the Apple object by an actor.
     * @param actor     The actor that consumes the Apple
     * @return          The result of consuming the Apple.
     */
    @Override
    public String consumedBy(Actor actor) {
        actor.heal(HEAL_AMOUNT); // restore health
        actor.modifyAttribute(PlayerAttributes.HYDRATION, ActorAttributeOperation.INCREASE, HYDRATION_AMOUNT);
        actor.removeItemFromInventory(this);
        return actor + " consumes " + this + ", heals by " + HEAL_AMOUNT + " and increases hydration by " + HYDRATION_AMOUNT + " points.";
    }
}
