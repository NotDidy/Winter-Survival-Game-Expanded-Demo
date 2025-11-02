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

    /**
     * who uses the Google Gemini API to generate thematic monologues.
     *
     * @param name The name of trader
     * @param displayChar The character representing the trader on the map.
     * @param hitPoints The initial health points of the trader.
     * @param apiKey The Gemini API key for authentication.
     */
    public GeminiLibrarian(String name, char displayChar, int hitPoints, String apiKey) {
        super(name, displayChar, hitPoints);
        this.monologueSource = new GeminiMonologueSource(apiKey);
        this.topic = "forgotten civilizations";
    }
    /**
     * The librarian’s default turn behavior. In this case, the librarian does nothing.
     *
     * @param actions collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do
     * interesting things in conjunction with Action.getNextAction()
     * @param map the map containing the Actor
     * @param display the I/O object to which messages may be written
     * @return The action to perform this turn.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return lastAction;
    }
    /**
     * Defines the list of actions available when another actor
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction String representing the direction of the other Actor
     * @param map current GameMap
     * @return A list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList list = new ActionList();
        list.add(new SpeakAction(this));
        return list;
    }

    /**
     * Generates a monologue
     *
     * @return A short mysterious monologue related to the librarian’s topic.
     */
    public String speak() {
        return monologueSource.generateMonologue(topic);
    }
}