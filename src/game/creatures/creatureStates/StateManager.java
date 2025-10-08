package game.creatures.creatureStates;

import edu.monash.fit2099.engine.positions.GameMap;
import game.creatures.StatefulCreature;
import java.util.*;

/**
 * Manages a creature's current state and transitions between states.
 * Handles all state switching logic independently of the states themselves.
 * @author John Devadhas (34380507)
 */
public class StateManager {
    private final StatefulCreature creature;
    private CreatureState currentState;
    private final Set<CreatureState> allStates;
    private double remainStateProbability;
    private static final double DEFAULT_REMAIN_STATE_PROBABILITY = 0.5;

    /**
     * Constructor.
     *
     * @param creature     The creature that contains the StateManager.
     * @param initialState The initialState of the creature at GameCreation.
     */
    public StateManager(StatefulCreature creature, CreatureState initialState) {
        this.creature = creature;
        this.currentState = initialState;
        this.allStates = new HashSet<>();
        this.allStates.add(initialState);
        this.remainStateProbability = DEFAULT_REMAIN_STATE_PROBABILITY;
    }

    public StateManager(StatefulCreature creature, CreatureState initialState, Set<CreatureState> possibleStates) {
        this.creature = creature;
        this.currentState = initialState;
        this.allStates = new HashSet<>(possibleStates);
        this.allStates.add(initialState);
        this.remainStateProbability = DEFAULT_REMAIN_STATE_PROBABILITY;
    }

    public CreatureState getCurrentState() {
        return currentState;
    }

    public boolean addState(CreatureState state) {
        return allStates.add(state);
    }

    public boolean removeState(CreatureState state) {
        if (state.equals(currentState)) return false;
        return allStates.remove(state);
    }

    public void changeState(CreatureState newState) {
        if (newState == null || !allStates.contains(newState)) return;
        this.currentState = newState;
    }

    public Set<CreatureState> getAllStates() {
        return new HashSet<>(allStates);
    }

    public void setRemainStateProbability(double probability) {
        if (probability >= 0 && probability <= 1) {
            this.remainStateProbability = probability;
        }
    }

    /**
     * Handles the state transition logic — centralized and decoupled from state classes.
     */
    public String handleStateTransition(GameMap map) {
        System.out.println(this.allStates);
        double rng = Math.random();
        System.out.println(rng);
        CreatureState nextState = currentState; // default to same

        if (rng >= remainStateProbability && allStates.size() > 1) {
            List<CreatureState> otherStates = new ArrayList<>(allStates);
            otherStates.remove(currentState);

            double remainingProbability = 1.0 - remainStateProbability;
            double portionPerState = remainingProbability / otherStates.size();

            double threshold = remainStateProbability;
            for (CreatureState state : otherStates) {
                threshold += portionPerState;
                System.out.println(rng);

                System.out.println(state);
                System.out.println(threshold);
                if (rng < threshold) {
                    nextState = state;
                    break;
                }
            }
        }

        changeState(nextState);
        return nextState.stateDetails(creature);
    }
}
