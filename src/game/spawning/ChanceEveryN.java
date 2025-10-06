package game.spawning;

import java.util.Random;

/**
 * Meadow spawning rule – every N turns with probability P.
 */
public class ChanceEveryN implements SpawnSchedule {
    private final int n;
    private final double probability;
    private final Random rnd;

    public ChanceEveryN(int n, double probability, Random rnd) {
        this.n = Math.max(1, n);
        this.probability = probability;
        this.rnd = rnd;
    }

    @Override
    public boolean shouldSpawn(int turn) {
        if (turn == 0 || (turn % n) != 0) return false;
        return rnd.nextDouble() < probability;
    }
}
