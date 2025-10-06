package game.coatings;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.creatures.CreatureAttributes;
import game.statuses.FrostbittenStatus;
import game.statuses.Frostbittenable;

public class SnowCoating implements Coating {

    @Override
    public void applyEffect(Actor attacker, Actor target, GameMap map) {
        if (!target.hasAbility(CreatureAttributes.TUNDRA)) {
            if (target.asCapability(Frostbittenable.class).isPresent()) {
                Frostbittenable frostbittenable = target.asCapability(Frostbittenable.class).get();
                target.addStatus(new FrostbittenStatus(frostbittenable,3, 1));
            }
        }


    }

    @Override
    public String getName() {
        return "Yewberry";
    }
}