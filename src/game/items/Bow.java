package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actions.AttackAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A Bow that can shoot targets within 3 tiles but not adjacent.
 * Deals 5 damage with 25% hit chance.
 *
 * @author Johan
 */
public class Bow extends Item implements Weapon {

    private static final int MIN_RANGE = 2;
    private static final int MAX_RANGE = 3;
    private final int damage = 5;
    private final int hitRate = 25;
    private final String verb = "shoots";

    public Bow(String name, char displayChar, boolean portable) {
        super("Bow", 'c', true);
    }

    /**
     * Adds ranged attack actions for actors within 2–3 tiles of the owner.
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();

        List<Actor> targetsInRange = getActorsInRange(map, owner, MIN_RANGE, MAX_RANGE);
        for (Actor target : targetsInRange) {
            actions.add(new AttackAction(target, "range", this));
        }

        return actions;
    }

    /**
     * Performs a ranged attack with this bow.
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();

        if (rand.nextInt(100) >= hitRate) {
            return attacker + " shoots at " + target + " but misses.";
        }

        target.hurt(damage);
        String result = attacker + " " + verb + " " + target + " for " + damage + " damage using a bow.";

        if (!target.isConscious()) {
            result += "\n" + target.unconscious(attacker, map);
        }

        return result;
    }

    /**
     * Helper: Finds all actors within a given tile range (min–max distance)
     */
    private List<Actor> getActorsInRange(GameMap map, Actor source, int minRange, int maxRange) {
        List<Actor> actorsInRange = new ArrayList<>();
        Location origin = map.locationOf(source);

        for (int x : map.getXRange()) {
            for (int y : map.getYRange()) {
                Location loc = map.at(x, y);

                if (map.isAnActorAt(loc)) {
                    Actor target = map.getActorAt(loc);
                    if (target == source) continue;

                    int dx = Math.abs(origin.x() - x);
                    int dy = Math.abs(origin.y() - y);
                    int distance = Math.max(dx, dy);

                    if (distance >= minRange && distance <= maxRange) {
                        actorsInRange.add(target);
                    }
                }
            }
        }
        return actorsInRange;
    }
}
