package game;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.*;
import game.actors.Player;
import game.creatures.*;
import game.grounds.Snow;
import game.grounds.*;
import game.grounds.trees.AppleTree;
import game.grounds.trees.HazelnutTree;
import game.grounds.trees.YewBerryTree;
import game.items.Axe;
import game.items.Bow;
import game.items.Torch;
import game.items.YewBerry;
import game.grounds.spawners.Tundra;
import game.grounds.spawners.Cave;
import game.grounds.spawners.Meadow;
import game.grounds.spawners.Spawner;

// REQ2 update:
import game.creatures.Crocodile;
import game.grounds.spawners.Swamp;

import game.items.TeleCube;

import java.util.Arrays;
import java.util.List;

public class Earth extends World {

    public Earth(Display display) {
        super(display);
    }

    public void constructWorld() throws Exception, GameEngineException {
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Snow::new);
        groundCreator.registerGround('+', DirtGround::new);
        groundCreator.registerGround('#', TeleDoor::new);
        groundCreator.registerGround('O', TeleCircle::new);
        groundCreator.registerGround('T', AppleTree::new);
        groundCreator.registerGround('A', HazelnutTree::new);
        groundCreator.registerGround('Y', YewBerryTree::new);

        groundCreator.registerGround('_', Tundra::new);
        groundCreator.registerGround('C', Cave::new);
        groundCreator.registerGround('w', Meadow::new);

        // REQ2: register Swamp '~'
        groundCreator.registerGround('~', Swamp::new);

        List<String> map = Arrays.asList(
                ".......O................................",
                "........................................",
                ".......#..........A.....................",
                "........................................",
                ".......Y..........T.....................",
                "........................................",
                "....._................C.................",
                "........................................",
                "...........~...w........................",
                "........................................"
        );

        GameMap gameMap = new GameMap("Forest", groundCreator, map);
        this.addGameMap(gameMap);

        Plains plains = new Plains();
        this.addGameMap(plains);

        configureTeleportDestinations(gameMap, plains);

        TeleCube cube = new TeleCube();
        cube.linkMaps(gameMap, plains);
        gameMap.at(2,1).addItem(cube);

        Dragon dragon = new Dragon("Dragon", 'D', 200);
        gameMap.at(29, 3).addActor(dragon);

        Golem golem = new Golem("Golem", 'G', 300);
        gameMap.at(30, 8).addActor(golem);

        Player player = new Player("Explorer", 'ඞ', 100, 20, 30);
        this.addPlayer(player, gameMap.at(1, 1));
        player.addItemToInventory(new Axe(Axe.DEFAULT_NAME, Axe.DEFAULT_DISPLAY_CHAR, Axe.DEFAULT_PORTABLE));
        player.addItemToInventory(new Bow(Bow.DEFAULT_NAME, Bow.DEFAULT_DISPLAY_CHAR, Bow.DEFAULT_PORTABLE));
        player.addItemToInventory(new Torch(Torch.DEFAULT_NAME, Torch.DEFAULT_DISPLAY_CHAR, Torch.DEFAULT_PORTABLE));
        player.addItemToInventory(new YewBerry());

        Location caveLocation = gameMap.at(22, 6);
        Spawner caveSpawner = caveLocation.getGroundAs(Spawner.class);
        caveSpawner.addSpawnableActor(() -> new Bear("Bear", 'B', 200));
        caveSpawner.addSpawnableActor(() -> new Wolf("Wolf", 'e', 100));
        caveSpawner.addSpawnableActor(() -> new Deer("Deer", 'd', 50));

        Location tundraLocation = gameMap.at(5, 6);
        Spawner tundraSpawner = tundraLocation.getGroundAs(Spawner.class);
        tundraSpawner.addSpawnableActor(() -> new Bear("Bear", 'B', 200));

        Location meadowLocation = gameMap.at(15, 8);
        Spawner meadowSpawner = meadowLocation.getGroundAs(Spawner.class);
        meadowSpawner.addSpawnableActor(() -> new Deer("Deer", 'd', 50));

        Location plainsCaveLocation = plains.at(34, 1);
        Spawner plainsCaveSpawner = plainsCaveLocation.getGroundAs(Spawner.class);
        plainsCaveSpawner.addSpawnableActor(() -> new Bear("Bear", 'B', 200));
        plainsCaveSpawner.addSpawnableActor(() -> new Wolf("Wolf", 'e', 100));

        Location plainstundraLocation = plains.at(20, 4);
        Spawner plainstundraSpawner = plainstundraLocation.getGroundAs(Spawner.class);
        plainstundraSpawner.addSpawnableActor(() -> new Wolf("Wolf", 'e', 100));

        Location plainsMeadowLocation = plains.at(2, 1);
        Spawner plainsMeadowSpawner = plainsMeadowLocation.getGroundAs(Spawner.class);
        plainsMeadowSpawner.addSpawnableActor(() -> new Deer("Deer", 'd', 50));
        plainsMeadowSpawner.addSpawnableActor(() -> new Bear("Bear", 'B', 200));

        // ===== REQ2: add crocodiles and configure swamps =====

        // Forest Meadow now also spawns Crocodiles
        meadowSpawner.addSpawnableActor(Crocodile::new);

        Location forestSwampLocation = gameMap.at(11, 8);
        Spawner forestSwamp = forestSwampLocation.getGroundAs(Spawner.class);
        forestSwamp.addSpawnableActor(() -> new Deer("Deer", 'd', 50));
        forestSwamp.addSpawnableActor(Crocodile::new);

        // Plains Tundra now spawns Crocodiles (in addition to Wolves)
        plainstundraSpawner.addSpawnableActor(Crocodile::new);

        // Plains Swamp (~) coordinates from modified Plains map (x=26, y=1)
        Location plainsSwampLocation = plains.at(14, 4);
        Spawner plainsSwamp = plainsSwampLocation.getGroundAs(Spawner.class);
        plainsSwamp.addSpawnableActor(Crocodile::new);
    }

    private void configureTeleportDestinations(GameMap forest, GameMap plains) {
        Location forestDoorLoc = forest.at(7, 2);
        Location plainsDoorLoc = plains.at(20, 2);

        Ground forestGround = forestDoorLoc.getGround();
        Ground plainsGround = plainsDoorLoc.getGround();

        if (TeleportDevice.class.isAssignableFrom(forestGround.getClass())
                && TeleportDevice.class.isAssignableFrom(plainsGround.getClass())) {

            ((TeleportDevice) forestGround).addDestination(plainsDoorLoc);
            ((TeleportDevice) plainsGround).addDestination(forestDoorLoc);
        }

        Location forestCircleLoc = forest.at(7, 0);
        Location plainsCircleLoc = plains.at(10, 3);

        Ground forestGround2 = forestCircleLoc.getGround();
        Ground plainsGround2 = plainsCircleLoc.getGround();

        if (TeleportDevice.class.isAssignableFrom(forestGround2.getClass())
                && TeleportDevice.class.isAssignableFrom(plainsGround2.getClass())) {

            ((TeleportDevice) forestGround2).addDestination(plainsCircleLoc);
            ((TeleportDevice) plainsGround2).addDestination(forestCircleLoc);
        }
    }
}
