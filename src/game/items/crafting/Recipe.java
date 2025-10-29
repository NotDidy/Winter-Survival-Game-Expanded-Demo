package game.items.crafting;

import edu.monash.fit2099.engine.actors.Actor;

public interface Recipe {
    boolean canCraft(Actor actor);
    String craft(Actor actor);
    boolean isPortableCraftable(Actor actor);
}
