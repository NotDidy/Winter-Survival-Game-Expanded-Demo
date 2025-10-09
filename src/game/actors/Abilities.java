package game.actors;

/**
 * Use this enum to represent abilities.
 * Example #1: if the player can jump over walls, you can attach Abilities.WALL_JUMP to the Player class
 * @author Kristano, John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.0.0
 */
public enum Abilities {
    /**
     * Represents an actor that can attack another entity.
     */
    CAN_ATTACK,
    /**
     * Represents an actor that is immune to physical attacks
     */
    IMMUNE_TO_ATTACKS,

    /**
     * Actor does not lose warmth each turn (e.g., spawned in tundra).
     */
    COLD_RESISTANT,
    /**
     * Represents an actor that can inflict damage upon taking damage.
     */
    COUNTER_ATTACK,
    /**
     * Represents an actor that can inflict damage upon taking damage.
     */
    PASSIVE_HEAL;
}
