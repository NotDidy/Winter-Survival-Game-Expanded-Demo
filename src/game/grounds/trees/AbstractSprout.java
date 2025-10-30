package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.Behaviours.SproutGrowthBehaviour;

public abstract class AbstractSprout extends AbstractFruitTree {
    private final SproutGrowthBehaviour<? super AbstractSprout> behaviour;
    private final String biome;

    private int age = 0;

    public AbstractSprout(char displayChar, String name, String biome, SproutGrowthBehaviour<? super AbstractSprout> behaviour) {
        super(displayChar, name, Integer.MAX_VALUE);
        this.behaviour = behaviour;
        this.biome = biome;
        this.disableAbility(TreeAbilities.CAN_PRODUCE);
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        behaviour.tick(location, this);
    }

    public String getBiome(){
        return biome;
    }
    public int getAge(){
        return age;
    }

    public void incrementAge(){
        age++;
    }
    @Override
    public abstract void produceFruit(Location location);

    public abstract void growInto(Location location, String biome);
}

