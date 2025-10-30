package game.grounds.trees.Behaviours;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.WildAppleTree;
import game.grounds.trees.WildAppleSprout;
import game.items.Apple;

public class PlainsSproutBehaviour implements SproutGrowthBehaviour {
    private static final int GROWTH_DURATION = 3;

    @Override
    public void tick(Location location, WildAppleSprout sprout) {
        sprout.incrementAge();
        // Produce fruit every turn
        sprout.produceFruit(location);

        if (sprout.getAge() >= GROWTH_DURATION) {
            location.setGround(new WildAppleTree("Plains"));
        }
    }
}