package game.grounds.trees.behaviours;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.YewBerryTree;
import game.items.YewBerry;

public class PeriodicYewDropBehaviour implements TreeBehaviour {
    private int counter;
    private final int period;              // e.g. 5
    private final double dropChancePerExit; // e.g. 0.3

    public PeriodicYewDropBehaviour(int period, double dropChancePerExit) {
        this.period = Math.max(1, period);
        this.dropChancePerExit = Math.max(0, Math.min(1, dropChancePerExit));
        this.counter = 0;
    }

    @Override
    public void tick(YewBerryTree tree, Location location) {
        counter++;
        if (counter >= period) {
            for (Exit exit : location.getExits()) {
                if (Math.random() < dropChancePerExit) {
                    exit.getDestination().addItem(new YewBerry());
                }
            }
            counter = 0;
        }
    }
}
