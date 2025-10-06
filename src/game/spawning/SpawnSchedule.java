package game.spawning;

/**
 * Strategy interface that determines when spawning occurs.
 * (Used by tundra, cave, and meadow.)
 */
public interface SpawnSchedule {
    boolean shouldSpawn(int turn);
}
