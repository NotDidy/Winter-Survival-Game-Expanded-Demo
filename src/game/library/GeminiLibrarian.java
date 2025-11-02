package game.library;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.actors.Actor;
import game.actions.SpeakAction;

/**
 * NPC that uses Gemini API to generate random monologues.
 * @author Jeffrey Lam PO Yuen (35306750)
 * @version 1.1.0
 * @since 1.1.0
 */
public class GeminiLibrarian extends Actor {

    private final MonologueSource monologueSource;
    private final String topic;

    public GeminiLibrarian(String name, char displayChar, int hitPoints, String apiKey) {
        super(name, displayChar, hitPoints);
        this.monologueSource = new GeminiMonologueSource(apiKey);
        this.topic = "forgotten civilizations";
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return lastAction;
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList list = new ActionList();
        list.add(new SpeakAction(this));
        return list;
    }

    public String speak() {
        return monologueSource.generateMonologue(topic);
    }
}