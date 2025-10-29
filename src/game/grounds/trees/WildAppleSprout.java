package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

public class WildAppleSprout extends Ground {
    private int age = 0;
    private static final int GROWTH_DURATION = 3;

    public WildAppleSprout(){
        super(',', "Wild Apple Sprout");
    }

    @Override
    public void tick(Location location){
        super.tick(location);
        age++;
        if (age>= GROWTH_DURATION){
            location.setGround(new WildAppleSapling());
        }
    }
}
