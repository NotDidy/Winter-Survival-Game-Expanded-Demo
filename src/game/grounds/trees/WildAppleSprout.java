package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.Behaviours.SproutGrowthBehaviour;
import game.items.Apple;

public class WildAppleSprout extends AbstractSprout {
    public WildAppleSprout(SproutGrowthBehaviour<? super AbstractSprout> behaviour) {
        super(',', "Wild Apple Sprout", behaviour);
    }

    @Override
    public void produceFruit(Location location) {
        for (var exit : location.getExits()) {
            if (Math.random() >= 0.7) {
                exit.getDestination().addItem(new Apple());
            }
        }
    }
    @Override
    public void growInto(Location location, String biome) {
        switch (biome) {
            case "Forest":
                location.setGround(new WildAppleSapling("Forest"));
                break;
            case "Plains":
                location.setGround(new WildAppleTree("Plains"));
                break;
            default:
                location.setGround(new WildAppleTree("Default"));
        }
    }
}

