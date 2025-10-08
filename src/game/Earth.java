package game;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.*;
import game.actors.Player;
import game.creatures.*;
import game.grounds.Snow;
import game.grounds.trees.AppleTree;
import game.grounds.trees.HazelnutTree;
import game.grounds.trees.YewBerryTree;
import game.grounds.spawners.Tundra;
import game.grounds.spawners.Cave;
import game.grounds.spawners.Meadow;
import game.grounds.spawners.Spawner;

import java.util.*;

public class Earth extends World {

    private final List<Spawner> spawners = new ArrayList<>();

    public Earth(Display display) {
        super(display);
    }

    public void constructWorld() throws Exception {
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Snow::new);
        groundCreator.registerGround('T', AppleTree::new);
        groundCreator.registerGround('A', HazelnutTree::new);
        groundCreator.registerGround('Y', YewBerryTree::new);

        groundCreator.registerGround('_', Tundra::new);
        groundCreator.registerGround('C', Cave::new);
        groundCreator.registerGround('w', Meadow::new);

        List<String> map = Arrays.asList(
                "........................................",
                "........................................",
                "..................A.....................",
                "........................................",
                ".......Y..........T.....................",
                "........................................",
                "....._................C.................",
                "........................................",
                "...............w........................",
                "........................................"
        );

        GameMap gameMap = new GameMap("Forest", groundCreator, map);
        this.addGameMap(gameMap);

        Dragon dragon = new Dragon("Dragon", 'D', 200);
        gameMap.at(29, 3).addActor(dragon);

        Golem golem = new Golem("Golem", 'G', 300);
        gameMap.at(30, 8).addActor(golem);

        Player player = new Player("Explorer", 'ඞ', 100, 20, 30);
        this.addPlayer(player, gameMap.at(1, 1));


        Location cavelocation = gameMap.at(22, 6);
        Spawner caveSpawner = cavelocation.getGroundAs(Spawner.class);
        caveSpawner.addSpawnableActor(() -> new Bear("Bear", 'B', 200));
        caveSpawner.addSpawnableActor(() -> new Wolf("Wolf", 'e', 100));
        caveSpawner.addSpawnableActor(() -> new Deer("Deer", 'd', 50));

        Location tundraLocation = gameMap.at(5, 6);
        Spawner tundraSpawner = tundraLocation.getGroundAs(Spawner.class);
        tundraSpawner.addSpawnableActor(() -> new Bear("Bear", 'B', 200));

        Location meadowLocation = gameMap.at(15, 8);
        Spawner meadowSpawner = meadowLocation.getGroundAs(Spawner.class);
        meadowSpawner.addSpawnableActor(() -> new Deer("Deer", 'd', 50));
    }
}

