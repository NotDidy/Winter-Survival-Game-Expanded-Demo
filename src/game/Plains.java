package game;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Player;
import game.animals.Bear;
import game.animals.Deer;
import game.animals.Wolf;
import game.grounds.Snow;
import game.grounds.trees.AppleTree;
import game.grounds.trees.HazelnutTree;
import game.grounds.trees.YewBerryTree;

import java.util.Arrays;
import java.util.List;

public class Plains extends World {
    public Plains(Display display) {
        super(display);
    }

    public void constructWorld() throws Exception {
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Snow::new);
        groundCreator.registerGround('T', AppleTree::new);
        groundCreator.registerGround('A', HazelnutTree::new);
        groundCreator.registerGround('Y', YewBerryTree::new);

        List<String> map = Arrays.asList(
                ".........................",
                ".........................",
                ".........................",
                ".........................",
                ".........................",
                ".........................",
                ".........................",
                "........................."
        );

        GameMap gameMap = new GameMap("Plains", groundCreator, map);
        this.addGameMap(gameMap);

        Deer deer = new Deer("Deer", 'd', 50);
        gameMap.at(1,3).addActor(deer);

        Wolf wolf = new Wolf("Wolf", 'e', 100);
        gameMap.at(15,3).addActor(wolf);

        Bear bear = new Bear("Bear", 'B', 200);
        gameMap.at(25,1).addActor(bear);

        Player player = new Player("Explorer", 'ඞ', 100, 20, 30);
        this.addPlayer(player, gameMap.at(1, 1));
    }
}