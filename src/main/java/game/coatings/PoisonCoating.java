package game.coatings;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.statuses.Poisonable;
import game.statuses.PoisonedStatus;

public class PoisonCoating implements Coating {
    public static final int DEFAULT_TICK_AMOUNT = 5;
    public static final int DEFAULT_DAMAGE = 4;
    private final int tickAmount;
    private final int damage;
    private final String name;

    public PoisonCoating(String name, int tickAmount, int reduceWarmthAmount) {
        this.name = name;
        this.tickAmount = tickAmount;
        this.damage = reduceWarmthAmount;
    }

    @Override
    public void applyEffect(Actor attacker, Actor target, GameMap map) {
        if (target.asCapability(Poisonable.class).isPresent()) {
            Poisonable poisonable = target.asCapability(Poisonable.class).get();
            target.addStatus(new PoisonedStatus(poisonable,this.tickAmount, this.damage));
        }

    }

    @Override
    public String getName() {
        return this.name;
    }
}