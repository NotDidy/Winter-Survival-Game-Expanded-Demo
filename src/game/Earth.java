package game;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Player;
import game.creatures.*;
import game.grounds.Snow;
import game.grounds.TeleDoor;
import game.grounds.trees.AppleTree;
import game.grounds.trees.HazelnutTree;
import game.grounds.trees.YewBerryTree;

import java.util.Arrays;
import java.util.List;

// REQ2 imports
import java.util.ArrayList;
import java.util.Random;
import game.spawning.Spawner;
import game.spawning.ChanceEachTurn;
import game.spawning.EveryNTurns;
import game.spawning.ChanceEveryN;
import game.effects.AddHealth;
import game.effects.SetConsumptionBehavior;
import game.behaviors.GroundConsumption;

public class Earth extends World {

    // REQ2: keep track of spawners registered on this world
    private final List<Spawner> spawners = new ArrayList<>();

    public Earth(Display display) {
        super(display);
    }

    public void constructWorld() throws Exception {
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

        Deer deer = new Deer("Deer", 'd', 50);
        gameMap.at(1,3).addActor(deer);

        Wolf wolf = new Wolf("Wolf", 'e', 100);
        gameMap.at(15,3).addActor(wolf);

        Bear bear = new Bear("Bear", 'B', 200);
        //gameMap.at(25,1).addActor(bear);

        Dragon dragon = new Dragon("Dragon", 'D', 200);
        gameMap.at(25,1).addActor(dragon);

        Golem golem = new Golem("Golem", 'G', 300);
        gameMap.at(30, 5).addActor(golem);

        Player player = new Player("Explorer", 'ඞ', 100, 20, 30);
        this.addPlayer(player, gameMap.at(1, 1));

        // === REQ2: register automatic animal spawners for Forest ===
        Random rng = new Random();

        // choose three locations on the Forest map for tundra/cave/meadow roles
        Location tundraF  = gameMap.at(5, 1);
        Location caveF    = gameMap.at(20, 4);
        Location meadowF  = gameMap.at(10, 6);

        // Forest tundra: BEAR, 5% each turn, +10 health
        spawners.add(
                new Spawner(tundraF, new ChanceEachTurn(0.05, rng), rng)
                        .addCandidate(() -> new Bear("Bear", 'B', 200))
                        .addEffect(new AddHealth(10))
        );

        // Forest cave: BEAR/WOLF/DEER every 5 turns
        spawners.add(
                new Spawner(caveF, new EveryNTurns(5), rng)
                        .addCandidate(() -> new Bear("Bear", 'B', 200))
                        .addCandidate(() -> new Wolf("Wolf", 'e', 100))
                        .addCandidate(() -> new Deer("Deer", 'd', 50))
        );

        // Forest meadow: DEER every 7 turns with 50%, can eat ground items
        spawners.add(
                new Spawner(meadowF, new ChanceEveryN(7, 0.5, rng), rng)
                        .addCandidate(() -> new Deer("Deer", 'd', 50))
                        .addEffect(new SetConsumptionBehavior(new GroundConsumption()))
        );
        // === end REQ2 spawners ===
    }

    public void addMarsLocation (Location location) {
    }

    // REQ2: call this once per turn from the game loop to drive spawners
    public void tickSpawners(int turn) {
        for (Spawner s : spawners) s.onTick(turn);
    }
}
