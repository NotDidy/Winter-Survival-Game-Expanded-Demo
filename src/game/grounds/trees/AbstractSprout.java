package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.Behaviours.SproutGrowthBehaviour;

public abstract class AbstractSprout extends Ground {
    private final SproutGrowthBehaviour<? super AbstractSprout> behaviour;
    private int age = 0;

    public AbstractSprout(char displayChar, String name, SproutGrowthBehaviour<? super AbstractSprout> behaviour) {
        super(displayChar, name);
        this.behaviour = behaviour;
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        behaviour.tick(location, this);
    }

    public int getAge() {
        return age;
    }

    public void incrementAge() {
        age++;
    }
    public abstract void produceFruit(Location location);

    public abstract void growInto(Location location, String biome);
}

