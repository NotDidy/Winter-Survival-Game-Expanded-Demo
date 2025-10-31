package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.Behaviours.GenericSaplingBehaviour;
import game.grounds.trees.Behaviours.SproutGrowthBehaviour;
import game.items.Apple;

public class WildAppleSprout extends AbstractSprout {
    public WildAppleSprout(String biome, SproutGrowthBehaviour<? super AbstractSprout> behaviour) {
        super(',', "Wild Apple Sprout", biome, behaviour);
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
        var saplingBehaviour = new GenericSaplingBehaviour(5, true);
        location.setGround(new WildAppleSapling(biome, saplingBehaviour));
        }
    }
