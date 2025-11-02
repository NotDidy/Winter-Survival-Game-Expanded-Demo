package game;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import game.grounds.DirtGround;
import game.grounds.Snow;
import game.grounds.TeleCircle;
import game.grounds.TeleDoor;
import game.grounds.spawners.Cave;
import game.grounds.spawners.Meadow;
import game.grounds.spawners.Tundra;
import game.grounds.trees.Behaviours.GenericSaplingBehaviour;
import game.grounds.trees.Behaviours.GenericSproutBehaviour;
import game.grounds.trees.WildAppleSprout;
import game.grounds.trees.YewBerrySapling;
import game.grounds.spawners.Swamp;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the "Plains" map in the game world.
 * The Plains map is composed primarily of {@link Snow} grounds, and may also
 * contain {@link TeleDoor} and {@link TeleCircle} objects for teleportation.
 * This class extends {@link GameMap} to define a specific layout and the
 * corresponding ground creation logic.
 *
 *
 * <p>
 * The Plains map is initialized with a predefined layout where:
 * <ul>
 *     <li>'.' represents {@link Snow}</li>
 *     <li>'#' represents {@link TeleDoor}</li>
 *     <li>'O' represents {@link TeleCircle}</li>
 * </ul>
 * </p>
 *
 * @author Wern Zhong Lei
 * @version 1.2
 * @since 1.0
 */

public class Plains extends GameMap {

    /**
     * Constructs a new Plains map
     * @throws GameEngineException if there is an error creating the map or registering ground types
     */
    public Plains() throws GameEngineException {
        super("Plains", createGroundCreator(), createMap());
    }

    /**
     * Creates and configures a {@link DefaultGroundCreator} for the Plains map.
     * Registers the following ground types:
     *     <li>'.' → {@link Snow}</li>
     *     <li>'#' → {@link TeleDoor}</li>
     *     <li>'O' → {@link TeleCircle}</li>
     * @return a configured {@link DefaultGroundCreator} instance.
     * @throws GameEngineException if an error occurs while registering the grounds.
     */
    private static DefaultGroundCreator createGroundCreator() throws GameEngineException {
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Snow::new);
        groundCreator.registerGround('#', TeleDoor::new);
        groundCreator.registerGround('O', TeleCircle::new);
        groundCreator.registerGround('+', DirtGround::new);
        groundCreator.registerGround('_', Tundra::new);
        groundCreator.registerGround('C', Cave::new);
        groundCreator.registerGround('w', Meadow::new);
        groundCreator.registerGround('~', Swamp::new);

        var plainsSproutBehaviour = new GenericSproutBehaviour(3, true);
        groundCreator.registerGround(',', () -> new WildAppleSprout("Plains", plainsSproutBehaviour));

        var plainsYewBerryBehaviour = new GenericSaplingBehaviour(3, true, 2, 0.5);
        groundCreator.registerGround('b', () -> new YewBerrySapling("Forest", plainsYewBerryBehaviour));

        return groundCreator;
    }

    /**
     * Defines the layout of the Plains map.
     * The map layout uses a list of strings to represent rows of tiles,
     * where each character corresponds to a specific ground type.
     * @return a {@link List} of {@link String} objects representing the map layout.
     */
    private static List<String> createMap() {
        return Arrays.asList(
                "..........,.............................",
                "..w.......................~.......C.....",
                "....................#...................",
                "...b......O.............................",
                "...................._...................",
                "........................................"
        );
    }
}