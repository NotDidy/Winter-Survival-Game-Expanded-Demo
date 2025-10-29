package game.interfaces;
import edu.monash.fit2099.engine.actions.Action;

import java.util.List;
public interface ActionCapable{
    /**
     * Retrieves a list of actions that are allowable.
     *
     * @return A list of {@link Action} objects that are allowable.
     */
    List<Action> allowableActions();
}
