package game;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.*;
import game.actors.Player;
import game.creatures.*;
import game.grounds.Snow;
import game.grounds.TeleCircle;
import game.grounds.TeleDoor;
import game.grounds.TeleportDevice;
import game.grounds.trees.AppleTree;
import game.grounds.trees.HazelnutTree;
import game.grounds.trees.YewBerryTree;
import game.items.TeleCube;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the "Earth" world in the game, serving as the main environment
 * where multiple {@link GameMap} instances (such as the Forest and Plains) are created and connected.
 * <p>
 * The Earth world initializes the terrain, actors, teleportation devices, and player setup.
 * It also defines teleport connections between the Forest and Plains maps using {@link TeleDoor},
 * {@link TeleCircle}, and {@link TeleCube}.
 *</p>
 *
 * <p>
 * This class extends {@link World}, which manages multiple game maps and coordinates
 * player movement between them.
 * </p>
 *
 * @author Wern Zhong Lei
 * @version 1.9
 * @since 1.0
 */
public class Earth extends World {

    /**
     * Constructs the Earth world.
     *
     * @param display the display used to render the world.
     */
    public Earth(Display display) {
        super(display);
    }

    /**
     * Constructs and configures the entire game world, including maps, actors,
     * items, and teleportation links.
     * The method initializes two main maps: "Forest" and "Plains". It sets up
     * teleportation links between them, populates the environment with creatures,
     * and places the player and items accordingly.
     *
     * @throws Exception           if a general error occurs during world construction.
     * @throws GameEngineException if an error occurs when creating or registering ground types.
     */
    public void constructWorld() throws Exception, GameEngineException {
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Snow::new);
        groundCreator.registerGround('#', TeleDoor::new);
        groundCreator.registerGround('O', TeleCircle::new);
        groundCreator.registerGround('T', AppleTree::new);
        groundCreator.registerGround('A', HazelnutTree::new);
        groundCreator.registerGround('Y', YewBerryTree::new);

        List<String> map = Arrays.asList(
                ".......O................................",
                "........................................",
                ".......#..........A.....................",
                "........................................",
                ".......Y..........T.....................",
                "........................................",
                "........................................",
                "........................................",
                "........................................",
                "........................................"
        );

        GameMap gameMap = new GameMap("Forest", groundCreator, map);
        this.addGameMap(gameMap);

        Plains plains = new Plains();
        this.addGameMap(plains);

        configureTeleportDestinations(gameMap, plains);

        Deer deer = new Deer("Deer", 'd', 50);
        gameMap.at(1, 3).addActor(deer);

        Wolf wolf = new Wolf("Wolf", 'e', 100);
        gameMap.at(15, 3).addActor(wolf);

        Bear bear = new Bear("Bear", 'B', 200);
        //gameMap.at(25,1).addActor(bear);

        Dragon dragon = new Dragon("Dragon", 'D', 200);
        gameMap.at(25, 1).addActor(dragon);

        Golem golem = new Golem("Golem", 'G', 300);
        gameMap.at(30, 5).addActor(golem);

        Player player = new Player("Explorer", 'ඞ', 100, 20, 30);
        this.addPlayer(player, gameMap.at(1, 1));

        TeleCube cube = new TeleCube();
        cube.linkMaps(gameMap, plains);

        gameMap.at(2,1).addItem(cube);
    }

    public void addMarsLocation(Location location) {

    }

    /**
     * Configures teleportation connections between the "Forest" and "Plains" maps.
     * This method links {@link TeleDoor} and {@link TeleCircle} objects across maps,
     * allowing the player and other actors to teleport between them.
     *
     * @param forest the {@link GameMap} representing the Forest map.
     * @param plains the {@link GameMap} representing the Plains map.
     */
    private void configureTeleportDestinations(GameMap forest, GameMap plains) {
        //connect TeleDoor between maps
        Location forestDoorLoc = forest.at(7, 2);   //location of teleDoor in forest
        Location plainsDoorLoc = plains.at(20, 2);  //location of teleDoor in plains

        Ground forestGround = forestDoorLoc.getGround();
        Ground plainsGround = plainsDoorLoc.getGround();

        if (TeleportDevice.class.isAssignableFrom(forestGround.getClass())
                && TeleportDevice.class.isAssignableFrom(plainsGround.getClass())) {

            ((TeleportDevice) forestGround).addDestination(plainsDoorLoc);
            ((TeleportDevice) plainsGround).addDestination(forestDoorLoc);
        }

        /* ------------------------------------------------------------------- */
        /* ------------------------------------------------------------------- */

        //connect TeleCircle between map
        Location forestCircleLoc = forest.at(7, 0);    //location of teleCircle in forest
        Location plainsCircleLoc = plains.at(10, 3);   //location of teleCircle in plains

        Ground forestGround2 = forestCircleLoc.getGround();
        Ground plainsGround2 = plainsCircleLoc.getGround();

        if (TeleportDevice.class.isAssignableFrom(forestGround2.getClass())
                && TeleportDevice.class.isAssignableFrom(plainsGround2.getClass())) {

            ((TeleportDevice) forestGround2).addDestination(plainsCircleLoc);
            ((TeleportDevice) plainsGround2).addDestination(forestCircleLoc);

        }
    }
}
