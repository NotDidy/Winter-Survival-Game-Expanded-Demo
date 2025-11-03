package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.trees.behaviours.TreeBehaviour;
import game.items.YewBerry;

import java.util.ArrayList;
import java.util.List;

public class YewBerryTree extends AbstractTree {
    private final List<TreeBehaviour> behaviours = new ArrayList<>();
    public YewBerryTree() {
        super('Y', "Yew Berry Tree", 5);
    }

    @Override
    protected void produceFruit(Location location) {
        for (Exit exit : location.getExits()) {
            if (Math.random() >= TreeConfiguration.FRUIT_DROP_THRESHOLD) {
                exit.getDestination().addItem(new YewBerry());
            }
        }
    }

    public void addBehaviour(TreeBehaviour behaviour) {
        if (behaviour != null) behaviours.add(behaviour);
    }

    public void clearBehaviours() {
        behaviours.clear();
    }
}
