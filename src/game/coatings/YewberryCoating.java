package game.coatings;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.statuses.Poisonable;
import game.statuses.PoisonedStatus;

public class YewberryCoating implements Coating {

    @Override
    public void applyEffect(Actor attacker, Actor target, GameMap map) {
        if (target.asCapability(Poisonable.class).isPresent()) {
            Poisonable poisonable = target.asCapability(Poisonable.class).get();
            target.addStatus(new PoisonedStatus(poisonable,5, 4));
        }

    }

    @Override
    public String getName() {
        return "Yewberry";
    }
}