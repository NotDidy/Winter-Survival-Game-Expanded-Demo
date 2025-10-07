package game.spawning;

import java.util.Random;

/** REQ2: every Nth turn, succeed with probability p (e.g., N=7, p=0.5). */
public class ChanceEveryN implements SpawnSchedule {
    private final int n;
    private final double p;
    private final Random rnd;

    public ChanceEveryN(int n, double p, Random rnd) {
        this.n = Math.max(1, n);
        this.p = p;
        this.rnd = rnd;
    }

    @Override
    public boolean shouldSpawn(int turn) {
        return (turn % n == 0) && rnd.nextDouble() < p;
    }
}
