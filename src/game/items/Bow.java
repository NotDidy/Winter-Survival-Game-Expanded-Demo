package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actions.AttackAction;

import java.util.Random;

/**
 * A Bow that can attack enemies within 3 tiles.
 * Deals 5 damage with a 25% chance to hit.
 *
 * @author Johan
 */
public class Bow extends Item implements Weapon {

    private final int damage = 5;
    private final int hitRate = 25;
    private final String verb = "shoots";

    public Bow(String name, char displayChar, boolean portable) {
        super("Bow", 'c', true);
    }

    /**
     * Adds ranged attack actions for all actors within 3 tiles of the owner.
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();
        Location ownerLoc = map.locationOf(owner);

        // Search within 3-tile radius
        for (int x = ownerLoc.x() - 3; x <= ownerLoc.x() + 3; x++) {
            for (int y = ownerLoc.y() - 3; y <= ownerLoc.y() + 3; y++) {
                if (map.getXRange().contains(x) && map.getYRange().contains(y)) {
                    Location targetLoc = map.at(x, y);

                    // Check if there's another actor here (not self)
                    if (map.isAnActorAt(targetLoc) && map.getActorAt(targetLoc) != owner) {
                        Actor target = map.getActorAt(targetLoc);
                        // Add a ranged attack option using this bow
                        actions.add(new AttackAction(target, "range", this));
                    }
                }
            }
        }
        return actions;
    }

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();

        if (rand.nextInt(100) >= hitRate) {
            return attacker + " shoots at " + target + " but misses";
        }

        target.hurt(damage);
        String result = attacker + " " + verb + " " + target + " for " + damage + " damage using a bow.";

        if (!target.isConscious()) {
            result += "\n" + target.unconscious(attacker, map);
        }

        return result;
    }


}
