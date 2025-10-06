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
     * Represents an entity that can attack another entity.
     */
    CAN_ATTACK,
    /**
     * Represents an entity that can change states.
     */
    CAN_CHANGE_STATES,

    TUNDRA;
}
