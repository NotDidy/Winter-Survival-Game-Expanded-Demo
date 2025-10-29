package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Apple;

public class WildAppleSprout extends Ground {
    private int age = 0;
    private static final int FOREST_GROWTH_DURATION = 3;
    private static final int PLAINS_GROWTH_DURATION = 3;

    public WildAppleSprout() {
        super(',', "Wild Apple Sprout");
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        age++;

        //get map name
        String mapName = location.map().toString();

        if (mapName.contains("Forest")) {
            if (age >= FOREST_GROWTH_DURATION) {
                location.setGround(new WildAppleSapling());
            }
        } else if (mapName.contains("Plains")) {
            if (age >= PLAINS_GROWTH_DURATION) {
                location.setGround(new WildAppleTree());
            }
        }
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
