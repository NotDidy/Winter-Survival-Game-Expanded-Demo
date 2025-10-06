package game.coatings;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.statuses.PoisonedStatus;
import game.statuses.Poisonable;

public class YewberryCoating implements Coating {

    @Override
    public void applyEffect(Actor attacker, Actor target, GameMap map) {
        // Apply poison to target if poisonable
        target.asCapability(Poisonable.class).ifPresent(poisonable -> {
            target.addStatus(new PoisonedStatus(poisonable, 5, 4)); // 5 turns, 4 dmg/turn
        });
    }

    @Override
    public String getName() {
        return "Yewberry";
    }
}
