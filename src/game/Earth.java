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
import game.grounds.Tundra;
import game.grounds.Cave;
import game.grounds.Meadow;

import java.util.*;
import game.spawning.Spawner;
import game.spawning.ChanceEachTurn;
import game.spawning.EveryNTurns;
import game.spawning.ChanceEveryN;
import game.effects.AddHealth;
import game.effects.SetConsumptionBehavior;
import game.behaviors.GroundConsumption;

public class Earth extends World {

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

        groundCreator.registerGround('_', Tundra::new);
        groundCreator.registerGround('C', Cave::new);
        groundCreator.registerGround('w', Meadow::new);

        List<String> map = Arrays.asList(
                "........................................",
                "........................................",
                ".......#..........A.....................",
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

        Deer deer = new Deer("Deer", 'd', 50);
        gameMap.at(2, 2).addActor(deer);

        Wolf wolf = new Wolf("Wolf", 'e', 100);
        gameMap.at(15, 3).addActor(wolf);

        Bear bear = new Bear("Bear", 'B', 200);
        gameMap.at(25, 1).addActor(bear);

        Dragon dragon = new Dragon("Dragon", 'D', 200);
        gameMap.at(25, 3).addActor(dragon);

        Golem golem = new Golem("Golem", 'G', 300);
        gameMap.at(30, 5).addActor(golem);

        Player player = new Player("Explorer", 'ඞ', 100, 20, 30);
        this.addPlayer(player, gameMap.at(1, 1));

        Random rng = new Random();

        for (int y = gameMap.getYRange().min(); y < gameMap.getYRange().max(); y++) {
            for (int x = gameMap.getXRange().min(); x < gameMap.getXRange().max(); x++) {
                Location loc = gameMap.at(x, y);
                char ch = loc.getGround().getDisplayChar();

                switch (ch) {
                    case '_': {
                        Spawner tundraSpawner = new Spawner(loc, new ChanceEachTurn(0.05, rng), rng)
                                .addCandidate(() -> {
                                    Bear b = new Bear("Bear", 'B', 200);
                                    b.enableAbility(game.actors.Abilities.COLD_RESISTANT);
                                    return b;
                                })
                                .addEffect(new AddHealth(10));
                        spawners.add(tundraSpawner);
                        break;
                    }
                    case 'C': {
                        Spawner caveSpawner = new Spawner(loc, new EveryNTurns(5), rng)
                                .addCandidate(() -> new Bear("Bear", 'B', 200))
                                .addCandidate(() -> new Wolf("Wolf", 'e', 100))
                                .addCandidate(() -> new Deer("Deer", 'd', 50));
                        spawners.add(caveSpawner);
                        break;
                    }
                    case 'w': {
                        Spawner meadowSpawner = new Spawner(loc, new ChanceEveryN(7, 0.5, rng), rng)
                                .addCandidate(() -> new Deer("Deer", 'd', 50))
                                .addEffect(new SetConsumptionBehavior(new GroundConsumption()));
                        spawners.add(meadowSpawner);
                        break;
                    }
                    default:
                        break;
                }
            }
        }
    }

    public void tickSpawners(int turn) {
        for (Spawner s : spawners) {
            s.onTick(turn);
        }
    }
}
