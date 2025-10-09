package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.statuses.StunnedStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Action used
 */
public class IntimidateAction extends Action {
    private final List<Actor> actorsAffected;

    public IntimidateAction (List<Actor> actorsAffected) {
        this.actorsAffected = actorsAffected;
    }
    @Override
    public String execute(Actor actor, GameMap map) {
        String executeString = actor + " intimidates ";
        if (this.actorsAffected.isEmpty())
            executeString += "no one.";
        else {
            List<String> actorNames = new ArrayList<>();
            for (Actor actorAffected : this.actorsAffected) {
                actorAffected.addStatus(new StunnedStatus(StunnedStatus.DEFAULT_STUN_DURATION));
                actorNames.add(String.valueOf(actorAffected));
            }
            executeString += String.join(", ",actorNames) + " for " + StunnedStatus.DEFAULT_STUN_DURATION + " turns.";
        }
        return executeString;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " intimidates";
    }
}
