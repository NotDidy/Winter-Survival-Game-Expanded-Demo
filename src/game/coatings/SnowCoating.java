package game.coatings;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Abilities;
import game.statuses.FrostbittenStatus;
import game.statuses.Frostbittenable;

public class SnowCoating implements Coating {
    private static final int TICK_AMOUNT = 3;
    private static final int REDUCE_WARMTH_AMOUNT = 1;

    @Override
    public void applyEffect(Actor attacker, Actor target, GameMap map) {
        if (!target.hasAbility(Abilities.COLD_RESISTANT)) {
            if (target.asCapability(Frostbittenable.class).isPresent()) {
                Frostbittenable frostbittenable = target.asCapability(Frostbittenable.class).get();
                target.addStatus(new FrostbittenStatus(frostbittenable, TICK_AMOUNT, REDUCE_WARMTH_AMOUNT));
            }
        }


    }

    @Override
    public String getName() {
        return "Snow";
    }
}