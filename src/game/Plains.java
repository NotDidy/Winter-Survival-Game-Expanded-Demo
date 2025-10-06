package game;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import game.grounds.Snow;
import game.grounds.TeleCircle;
import game.grounds.TeleDoor;

import java.util.Arrays;
import java.util.List;

public class Plains extends GameMap {

    public Plains() throws GameEngineException {
        super("Plains", createGroundCreator(), createMap());
    }

    private static DefaultGroundCreator createGroundCreator() throws GameEngineException {
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Snow::new);
        groundCreator.registerGround('#', TeleDoor::new);
        groundCreator.registerGround('O', TeleCircle::new);
        return groundCreator;
    }

    private static List<String> createMap() {
        return Arrays.asList(
                "........................................",
                "........................................",
                "....................#...................",
                "..........O.............................",
                "........................................",
                "........................................"
        );
    }
}