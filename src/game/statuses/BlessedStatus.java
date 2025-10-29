package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A status that grants passive healing to an actor each turn.
 * Used by CursedAmulet while held by the player.
 */
public class BlessedStatus implements Status {

    private static final int DEFAULT_HEAL_AMOUNT = 2;
    private int healAmount;
    private int turnsActive = 0;

    public BlessedStatus() {
        this(DEFAULT_HEAL_AMOUNT);
    }

    public BlessedStatus(int healAmount) {
        this.healAmount = healAmount;
    }

    @Override
    public boolean isStatusActive() {
        return true;
    }

    @Override
    public void tickStatus(GameEntity entity, Location location) {
        entity.asCapability(Actor.class).ifPresent(actor -> {
            turnsActive++;
            actor.heal(healAmount);
            Display display = new Display();
            display.println(actor + " heals passively from Blessed for (" + healAmount + " HP).");
        });
    }

    @Override
    public String toString() {
        return "Blessed (+" + healAmount + " HP/turn)";
    }
}
