package game.creatures.creatureStates;

import edu.monash.fit2099.engine.positions.GameMap;
import game.creatures.StatefulCreature;
import java.util.*;

/**
 * Manages a creature's current state and transitions between states.
 * Handles all state switching logic independently of the states themselves.
 * Generative AI was used to understand the syntax usage of some sections in this class, particularly transitionMap and Set usage.
 * @author John Devadhas (34380507)
 */
public class StateManager {
    private final StatefulCreature creature;
    private CreatureState currentState;
    private final Set<CreatureState> allStates;
    private double remainStateProbability;
    private static final double DEFAULT_REMAIN_STATE_PROBABILITY = 0.5;
    private static final double FULL_PROBABILITY = 1.0;
    /**
     * Mapping that defines what states each state can transition into.
     * Example: FireState → {IceState, LightningState}
     */
    private final Map<Class<? extends CreatureState>, Set<Class<? extends CreatureState>>> transitionMap = new HashMap<>();

    /**
     * Constructor.
     *
     * @param creature     The creature that contains the StateManager.
     * @param initialState The initialState of the creature at GameCreation.
     */
    public StateManager(StatefulCreature creature, CreatureState initialState) {
        this.creature = creature;
        this.currentState = initialState;
        initialState.stateDetails(creature);
        this.allStates = new HashSet<>();
        this.allStates.add(initialState);
        this.remainStateProbability = DEFAULT_REMAIN_STATE_PROBABILITY;
    }

    /**
     * Constructor.
     *
     * @param creature     The creature that contains the StateManager.
     * @param initialState The initialState of the creature at GameCreation.
     */
    public StateManager(StatefulCreature creature, CreatureState initialState, Set<CreatureState> possibleStates) {
        this.creature = creature;
        this.currentState = initialState;
        initialState.stateDetails(creature);
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
        if (probability >= 0 && probability <= FULL_PROBABILITY) {
            this.remainStateProbability = probability;
        }
    }

    /**
     * Adds a possible state transition mapping.
     * Example: addTransition(FireState.class, Set.of(IceState.class, LightningState.class))
     * @param fromState     The CreatureState that the creature is transitioning from
     * @param toStates      The CreatureState(s) that the creature can transition to
     */
    public void addTransition(Class<? extends CreatureState> fromState, Set<Class<? extends CreatureState>> toStates) {
        transitionMap.put(fromState, new HashSet<>(toStates));
    }

    /**
     * Processes state transition.
     *
     * @param map   The GameMap object where the current Stateful Creature is in.
     */
    public String handleStateTransition(GameMap map) {
        double random = Math.random();
        CreatureState nextState = currentState; // default to same state

        if (random >= remainStateProbability) {
            // Get valid next states for current state (if mapping exists)
            Set<Class<? extends CreatureState>> validNextClasses = transitionMap.getOrDefault(currentState.getClass(), Collections.emptySet());

            // Filter only the mapped states that are part of the creature’s available states
            List<CreatureState> validNextStates = new ArrayList<>();
            for (CreatureState state : allStates) {
                if (validNextClasses.contains(state.getClass())) {
                    validNextStates.add(state);
                }
            }
            if (!validNextStates.isEmpty()) {
                String transitionFromStateString = "";
                double remainingProbability = FULL_PROBABILITY - remainStateProbability;
                double portionPerState = remainingProbability / validNextStates.size();

                double threshold = remainStateProbability;
                for (CreatureState state : validNextStates) {
                    threshold += portionPerState;
                    if (random < threshold) {
                        nextState = state;
                        transitionFromStateString = currentState.transitionFromState(this.creature);
                        break;
                    }
                }
                changeState(nextState);
                return nextState.stateDetails(creature) + " " + transitionFromStateString;
            }
        }
        return this.creature + " remains in " + this.currentState.getClass().getSimpleName() + ".";
    }
}

