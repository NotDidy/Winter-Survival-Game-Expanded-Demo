package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.Behaviours.SaplingGrowthBehaviour;

public abstract class AbstractSapling extends AbstractFruitTree {
    private final SaplingGrowthBehaviour<? super AbstractSapling> behaviour;

    public AbstractSapling(char displayChar, String name, SaplingGrowthBehaviour<? super AbstractSapling> behaviour) {
        super(displayChar, name, Integer.MAX_VALUE);
        this.behaviour = behaviour;
        this.disableAbility(TreeAbilities.CAN_PRODUCE);
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        behaviour.tick(location, this);
    }

    @Override
    public abstract void produceFruit(Location location);

    public abstract void growInto(Location location, String biome);
}
