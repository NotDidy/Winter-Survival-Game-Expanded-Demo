package game;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.*;
import game.actors.Player;
import game.creatures.*;
import game.grounds.Snow;
import game.grounds.TeleDoor;
import game.grounds.TeleportDevice;
import game.grounds.trees.AppleTree;
import game.grounds.trees.HazelnutTree;
import game.grounds.trees.YewBerryTree;

import java.util.Arrays;
import java.util.List;

public class Earth extends World {
    public Earth(Display display) {
        super(display);
    }

    public void constructWorld() throws Exception, GameEngineException {
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Snow::new);
        groundCreator.registerGround('#', TeleDoor::new);
        groundCreator.registerGround('T', AppleTree::new);
        groundCreator.registerGround('A', HazelnutTree::new);
        groundCreator.registerGround('Y', YewBerryTree::new);

        List<String> map = Arrays.asList(
                "........................................",
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
    }

    public void addMarsLocation(Location location) {

    }

    private void configureTeleportDestinations(GameMap forest, GameMap plains) {
        //connect TeleDoor on Forest (x=7, y=2) to Plains (x=20, y=2)
        Location forestDoorLoc = forest.at(7, 2);
        Location plainsDoorLoc = plains.at(20, 2);

        Ground forestGround = forestDoorLoc.getGround();
        Ground plainsGround = plainsDoorLoc.getGround();

        if (TeleportDevice.class.isAssignableFrom(forestGround.getClass())
                && TeleportDevice.class.isAssignableFrom(plainsGround.getClass())) {

            ((TeleportDevice) forestGround).addDestination(plainsDoorLoc);
            ((TeleportDevice) plainsGround).addDestination(forestDoorLoc);
        }

    }
}
