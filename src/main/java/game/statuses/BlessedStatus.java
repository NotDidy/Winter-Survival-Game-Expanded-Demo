package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;
import game.items.CursedItem;

/**
 * A status that grants passive healing to an actor each turn.
 * Used by CursedAmulet while held by the player.
 *
 * @author Muhammad Johan Arief (33523061)
 * @version 1.3.0
 * @since  1.3.0
 */
public class BlessedStatus implements Status {

    private static final int DEFAULT_HEAL_AMOUNT = 2;
    private CursedItem cursedItem;
    private int healAmount;
    private int turnsActive = 0;
    private boolean isActive = true;

    /**
     * Constructor
     * @param item that holds the curse
     */
    public BlessedStatus(CursedItem item) {
        this.healAmount = DEFAULT_HEAL_AMOUNT;
        this.cursedItem = item;
    }

    public BlessedStatus(int healAmount, CursedItem item) {
        this.healAmount = healAmount;
        this.cursedItem = item;
    }


    /**
     *
     * @return isActive boolean variable
     */

    @Override
    public boolean isStatusActive() {
        return isActive;
    }


    /**
     *
     * @param entity the entity this status is attached to
     * @param location
     */

    @Override
    public void tickStatus(GameEntity entity, Location location) {
        entity.asCapability(Actor.class).ifPresent(actor -> {
            if (actor.getItemInventory().contains(cursedItem)) {
                isActive = true;
                turnsActive++;
                actor.heal(healAmount);
                Display display = new Display();
                display.println(actor + " heals passively from Blessed for (" + healAmount + " HP).");
            } else {
                isActive = false;
            }

        });
    }

    /**
     *
     * @return display output
     */
    @Override
    public String toString() {
        return "Blessed (+" + healAmount + " HP/turn)";
    }
}
