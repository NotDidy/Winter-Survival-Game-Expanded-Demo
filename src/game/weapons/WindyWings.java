package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
//import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

//import java.util.List;
import java.util.Random;

/**
 * Represent Wings as an IntrinsicWeapon.
 * @author John Devadhas (34380507)
 * @version 1.2.0
 * @since 1.2.0
 */
public class WindyWings extends IntrinsicWeapon {
    private static final int DAMAGE = 15;
    private static final String VERB = "smacks";
    private static final int HIT_RATE= 70;
    private static final String NAME = "Wings";

    /**
     * Constructor.
     */
    public WindyWings() {
        super(DAMAGE, VERB, HIT_RATE, NAME);
    }

    /**
     * Processes the attack.
     * @param attacker the actor who performed the attack
     * @param target   the actor who is the target of the attack
     * @param map      the map on which the attack was executed
     * @return         The outcome of the attack.
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();
        if (!(rand.nextInt(100) <= HIT_RATE)) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(DAMAGE);
        boolean computeTargetMove = computeTargetMove(attacker, target, map);
        String pushResult = computeTargetMove ? target + " is pushed!" : "";
        //==== Alternative way of moving target using exits, doesn't work as well as above implementation (line 46-47) due to extra calculations ====//
//        Location newTargetLocation = getChosenLocation(attacker,target,map);
//        map.moveActor(target,newTargetLocation);
//        String pushResult = target + " is pushed!";
        return String.format("%s %s %s for %d damage. ", attacker, VERB, target, DAMAGE) + pushResult;
    }

    /**
     * Processes a target actor being moved away from an attacker
     * @param attacker  The attacker that the target is being moved away from
     * @param target    The target being moved
     * @param map       The GameMap object that contains both attacker and target
     * @return          True if the target has been moved.
     *                  False if the target was not moved.
     */
    private boolean computeTargetMove(Actor attacker, Actor target, GameMap map) {
        Location attackerLocation = map.locationOf(attacker);
        Location targetLocation = map.locationOf(target);
        int dx = Integer.signum(targetLocation.x() - attackerLocation.x());
        int dy = Integer.signum(targetLocation.y() - attackerLocation.y());

        int newX = targetLocation.x() + dx;
        int newY = targetLocation.y() + dy;

        Location pushLocation = safeLocation(map, newX, newY);

        // Try direct push
        boolean pushed = pushLocation != null && attemptMove(target, pushLocation, map);

        if (!pushed) {
            if (dx == 0) { // Vertical push → attacker below or above target
                // Try left then right
                pushed = attemptMove(target, safeLocation(map, targetLocation.x() - 1, targetLocation.y()), map)
                        || attemptMove(target, safeLocation(map, targetLocation.x() + 1, targetLocation.y()), map);
            }
            else if (dy == 0) { // Horizontal push → left or right
                // Try up then down
                pushed = attemptMove(target, safeLocation(map, targetLocation.x(), targetLocation.y() - 1), map)
                        || attemptMove(target, safeLocation(map, targetLocation.x(), targetLocation.y() + 1), map);
            }
            else { // Diagonal attacker → try both axes
                Location horiz = safeLocation(map, targetLocation.x() + dx, targetLocation.y());
                Location vert  = safeLocation(map, targetLocation.x(), targetLocation.y() + dy);

                // Try horizontal first
                pushed = horiz != null && attemptMove(target, horiz, map);

                // If not moved, try vertical
                if (!pushed && vert != null) {
                    pushed = attemptMove(target, vert, map);
                }
            }
        }
        return pushed;
    }

    /**
     * Helper function for alternative way of finding a suitable location to move target to using Exits.
     * @param attacker  The Actor attacking
     * @param target    The target Actor attacked by the attacker
     * @param map       The GameMap object
     * @return          a new Location object of the target to be placed
     */
//    private Location getChosenExitLocation(Actor attacker, Actor target, GameMap map) {
//        List<Exit> attackerExits = map.locationOf(attacker).getExits();
//        Location targetLocation = map.locationOf(target);
//        Location chosenLocation = null;
//        for (Exit targetExit: targetLocation.getExits()) {
//            Location dest = targetExit.getDestination();
//            if (!map.isAnActorAt(dest)) {
//                chosenLocation = dest;
//                // Primary choice: Not in attacker exits and empty
//                if (!attackerExits.contains(targetExit))
//                    return dest;
//            }
//        }
//        return chosenLocation;
//    }

    /**
     * Checks if the target can be moved to a specific Location
     * @param target        The target to be moved
     * @param destination   The Location to be moved into
     * @param map           The GameMap object that should currently contain the target and destination
     * @return              True if the target can be moved to the destination<br>
     *                      False if the target cannot be moved to the destination.
     */
    private boolean attemptMove(Actor target, Location destination, GameMap map) {
        if (map.contains(target) && destination.canActorEnter(target) && !destination.containsAnActor()) {
            map.moveActor(target, destination);
            return true;
        }
        return false;
    }

    /**
     * Processes if the coordinate is an actual location in the GameMap object
     * @param map   The GameMap object
     * @param x     The x coordinate
     * @param y     The y coordinate
     * @return      The Location object according to the x and y coordinate.
     */
    private Location safeLocation(GameMap map, int x, int y) {
        if (map.getXRange().contains(x) && map.getYRange().contains(y)) {
            return map.at(x, y);
        }
        return null;
    }
}
