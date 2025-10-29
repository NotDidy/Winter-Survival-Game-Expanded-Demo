package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Speakable;

/**
 * TalkAction allows an Actor to talk to a Speakable entity such as the Gemini Librarian.
 */
public class TalkAction extends Action {
    private final Speakable target;

    public TalkAction(Speakable target) {
        this.target = target;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return target.getName() + " says: \"" + target.speak() + "\"";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " talks to " + target.getName();
    }
}
