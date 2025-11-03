package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actions.AttackAction;
import game.coatings.Coatable;
import game.coatings.Coating;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A Bow that can shoot targets within 3 tiles but not adjacent.
 * Deals 5 damage with 25% hit chance.
 *
 * @author Johan
 */
public class Bow extends Item implements Weapon, Coatable {
    public static final String DEFAULT_NAME = "Bow";
    public static final char DEFAULT_DISPLAY_CHAR = 'c';
    public static final boolean DEFAULT_PORTABLE = true;
    private Coating coating;
    private static final int DEF_MIN_RANGE = 2;
    private static final int DEF_MAX_RANGE = 3;
    private final int min_range;
    private final int max_range;
    private final static int DEFAULT_DAMAGE = 5;
    private final int damage;
    private final int hitRate = 25;
    private final String verb = "shoots";

    /***
     * Bow Constructor.
     */
    public Bow() {
        super("Bow", 'c', true);
        this.min_range = DEF_MIN_RANGE;
        this.max_range = DEF_MAX_RANGE;
        this.damage = DEFAULT_DAMAGE;
    }

    public Bow(String name, int minRange, int maxRange, int damage) {
        super(name, DEFAULT_DISPLAY_CHAR, DEFAULT_PORTABLE);
        this.min_range = minRange;
        this.max_range = maxRange;
        this.damage = damage;
    }

    /**
     * Adds ranged attack actions for actors within 2–3 tiles of the owner.
     * @param owner the actor holding the bow
     * @param map the game map
     * @return a list of actors that the holder of the bow is able to shoot at
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();

        List<Actor> targetsInRange = getActorsInRange(map, owner, this.min_range, this.max_range);
        for (Actor target : targetsInRange) {
            Location targetLoc = map.locationOf(target);
            String direction = String.format("(%d, %d)", targetLoc.x(), targetLoc.y());
            actions.add(new AttackAction(target, direction, this));
        }

        return actions;
    }

    /**
     * Performs a melee attack with the bow
     * @param attacker The attacking actor
     * @param target The actor being attacked
     * @param map The game map
     * @return A string to display onto the console
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

        if (coating != null) {
            coating.applyEffect(attacker, target, map);
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

    @Override
    public void applyCoating(Coating coating) {
        this.coating = coating;
    }


}
