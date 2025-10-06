package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Abilities;
import game.actors.Player;
import game.creatures.*;
import game.grounds.Snow;
import game.grounds.TeleDoor;
import game.grounds.trees.AppleTree;
import game.grounds.trees.HazelnutTree;
import game.grounds.trees.YewBerryTree;
import game.behaviors.GroundConsumption;
import game.spawning.Spawner;
import game.spawning.ChanceEachTurn;
import game.spawning.EveryNTurns;
import game.spawning.ChanceEveryN;

import java.util.*;

/**
 * Represents the Earth game world.
 * Handles map creation and REQ2 animal auto-spawning.
 */
public class Earth extends World {

    private final List<Spawner> spawners = new ArrayList<>();
    private int turnCounter = 0;

    /**
     * Earth constructor
     * @param display The display object used for the game
     */
    public Earth(Display display) {
        super(display);
    }

    /**
     * Constructs the game world with all maps, actors, and spawners.
     * @throws Exception if an error occurs during setup
     */
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

        // Existing sample actors
        Deer deer = new Deer("Deer", 'd', 50);
        gameMap.at(1,3).addActor(deer);

        Wolf wolf = new Wolf("Wolf", 'e', 100);
        gameMap.at(15,3).addActor(wolf);

        Player player = new Player("Explorer", 'ඞ', 100, 20, 30);
        this.addPlayer(player, gameMap.at(1, 1));

        // === Inline Tick actor for auto-spawning ===
        gameMap.at(0, 0).addActor(new Actor("Tick", ' ', Integer.MAX_VALUE) {
            @Override
            public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
                tickSpawners(++turnCounter);
                return new DoNothingAction();
            }
            @Override
            public ActionList allowableActions(Actor other, String direction, GameMap map) {
                return new ActionList();
            }
        });

        // === REQ2 Spawners (Forest) ===
        Random rng = new Random();

        Location tundraLoc = gameMap.at(5, 1);
        Location caveLoc   = gameMap.at(20, 4);
        Location meadowLoc = gameMap.at(10, 6);

        // Tundra: 5% per turn — Bears only (+10 HP, cold resistant)
        spawners.add(
                new Spawner(tundraLoc, new ChanceEachTurn(0.05, rng), rng)
                        .addCandidate(() -> {
                            Bear b = new Bear("Bear", 'B', 200);
                            b.heal(10);                                  // tundra +10 HP
                            b.enableAbility(Abilities.COLD_RESISTANT);   // use enableAbility() (present in your codebase)
                            return b;
                        })
        );

        // Cave: every 5 turns — Bear/Wolf/Deer (equal chance)
        spawners.add(
                new Spawner(caveLoc, new EveryNTurns(5), rng)
                        .addCandidate(() -> new Bear("Bear", 'B', 200))
                        .addCandidate(() -> new Wolf("Wolf", 'e', 100))
                        .addCandidate(() -> new Deer("Deer", 'd', 50))
        );

        // Meadow: every 7 turns (50%) — Deer only (can consume ground items)
        spawners.add(
                new Spawner(meadowLoc, new ChanceEveryN(7, 0.5, rng), rng)
                        .addCandidate(() -> {
                            Deer d = new Deer("Deer", 'd', 50);
                            d.setConsumptionBehavior(new GroundConsumption());        // allow ground consumption
                            d.enableAbility(Abilities.CAN_CONSUME_GROUND_ITEMS);      // capability flag
                            return d;
                        })
        );
    }

    /**
     * Drives all spawners each game turn.
     * @param turn current turn number
     */
    public void tickSpawners(int turn) {
        for (Spawner s : spawners) s.onTick(turn);
    }


    public void addMarsLocation(Location location) { }
}
