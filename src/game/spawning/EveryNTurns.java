package game.spawning;

/** REQ2: true every N turns (e.g., N=5). */
public class EveryNTurns implements SpawnSchedule {
    private final int n;

    public EveryNTurns(int n) { this.n = Math.max(1, n); }

    @Override
    public boolean shouldSpawn(int turn) {
        return turn % n == 0;
    }
}
