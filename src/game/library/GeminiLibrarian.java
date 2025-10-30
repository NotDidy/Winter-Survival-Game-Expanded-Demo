package game.library;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.TalkAction;
import game.interfaces.ActionCapable;
import game.interfaces.Speakable;

import java.util.List;
import java.util.Random;

/**
 * The GeminiLibrarian is an NPC Actor that can talk to the player.
 * Initially, it produces mock dialogue until connected to Gemini API.
 */
public class GeminiLibrarian extends Actor implements ActionCapable, Speakable {

    private final Random random = new Random();

    public GeminiLibrarian(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String speak() {
        // Simple random dialogue for now
        String[] lines = {
                "Hello traveler! I'm the keeper of forgotten knowledge.",
                "There are ancient knowledge buried in forgotten temples",
                "The courage it takes to face dragons",
                "Books are the windows to the soul and I’ve read quite a few.",
                "Did you come here seeking wisdom or adventure?"
        };
        return lines[random.nextInt(lines.length)];
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        // Only allow talk if the other actor is not this one
        if (otherActor != this) {
            actions.add(new TalkAction(this));
        }
        return actions;
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display){
        return lastAction;
    }

    @Override
    public List<Action> allowableActions() {
        return List.of();
    }
}