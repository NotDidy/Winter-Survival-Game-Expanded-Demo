package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Apple;

public class YewBerrySapling extends Ground {
    private int age = 0;
    private static final int GROWTH_INTERVAL = 3;
    private static final double GROWTH_CHANCE = 0.5;
    private int plainsFruitCounter = 0;
    private static final int PLAINS_FRUIT_INTERVAL = 2;

    public YewBerrySapling() {
        super('b', "Yew Berry Sapling");
    }

    public void tick(Location location) {
        super.tick(location);
        age++;

        //get map name
        String mapName = location.map().toString();

        //FOREST MAP
        if (mapName.contains("Forest")) {
            if (age % GROWTH_INTERVAL == 0 && Math.random() < GROWTH_CHANCE) {
                location.setGround(new WildAppleSapling());
            }
        }
        //PLAINS MAP
        else if (mapName.contains("Plains")) {
            plainsFruitCounter++;

            //produce yewberry every 2 turn
            if (plainsFruitCounter % PLAINS_FRUIT_INTERVAL == 0) {
                produceFruit(location);
            }
            //50% growth chance every 3 turn
            if (age % GROWTH_INTERVAL == 0 && Math.random() < GROWTH_CHANCE) {
                location.setGround(new YewBerryTree());
            }
        }
    }
        private void produceFruit (Location location){
            // Each turn, drop apples around the sprout
            for (var exit : location.getExits()) {
                if (Math.random() >= 0.7) {
                    exit.getDestination().addItem(new Apple());
                }
            }
        }
    }
