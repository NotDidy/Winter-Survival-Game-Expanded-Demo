package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Location;

public class WildAppleSapling extends AbstractAppleTree {
    private int age = 0;
    private static final int GROWTH_DURATION = 5;

    public WildAppleSapling(){
        super('t', "Wild Apple Sapling", 2);
    }

    @Override
    public void tick(Location location){
        super.tick(location);
        age++;
        if (age>= GROWTH_DURATION){
            location.setGround(new WildAppleTree());
        }
    }
}
