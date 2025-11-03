package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.library.GeminiLibrarian;

/**
 * Allows a player to listen to the Librarian's words.
 * @author Jeffrey Lam PO Yuen (35306750)
 * @version 1.3.0
 * @since 1.3.0
 */
public class SpeakAction extends Action {
    private final GeminiLibrarian librarian;

    /**
     * Parameterised constructor
     * @param librarian the librarian object
     */
    public SpeakAction(GeminiLibrarian librarian) {
        this.librarian = librarian;
    }

    /**
     * Perform the Action.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened (the result of the action being performed) that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return librarian.speak();
    }

    /**
     * Describe what action will be performed if this Action is chosen in the menu.
     *
     * @param actor The actor performing the action.
     * @return the action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " listens to " + librarian + "’s monologue";
    }
}