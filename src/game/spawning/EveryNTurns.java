package game.spawning;

/**
 * Cave spawning rule – spawn every N turns (e.g., 5).
 */
public class EveryNTurns implements SpawnSchedule {
    private final int n;

    public EveryNTurns(int n) {
        this.n = Math.max(1, n);
    }

    @Override
    public boolean shouldSpawn(int turn) {
        return turn > 0 && (turn % n) == 0;
    }
}
