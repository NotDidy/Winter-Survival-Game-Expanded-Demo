package game.spawning;

/** REQ2: schedule predicate that decides if a spawn happens on a given turn. */
public interface SpawnSchedule {
    boolean shouldSpawn(int turn);
}
