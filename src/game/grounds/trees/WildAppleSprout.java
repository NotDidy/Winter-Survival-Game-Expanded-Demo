package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.Behaviours.ForestSproutBehaviour;
import game.grounds.trees.Behaviours.SproutGrowthBehaviour;
import game.items.Apple;

public class WildAppleSprout extends Ground {
    private final SproutGrowthBehaviour behaviour;
    private int age = 0; 

    public WildAppleSprout(SproutGrowthBehaviour behaviour) {
        super(',', "Wild Apple Sprout");
        this.behaviour = behaviour;
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        behaviour.tick(location, this);
    }

    public int getAge(){
        return age;
    }

    public void incrementAge(){
        age++;
    }

    private void produceFruit(Location location) {
        // Each turn, drop apples around the sprout
        for (var exit : location.getExits()) {
            if (Math.random() >= 0.7) {
                exit.getDestination().addItem(new Apple());
            }
        }
    }
}
