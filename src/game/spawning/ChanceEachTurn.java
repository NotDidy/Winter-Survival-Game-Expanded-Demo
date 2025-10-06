package game.spawning;

import java.util.Random;

/**
 * Tundra spawning rule – 5% chance each turn.
 */
public class ChanceEachTurn implements SpawnSchedule {
    private final double probability;
    private final Random rnd;

    public ChanceEachTurn(double probability, Random rnd) {
        this.probability = probability;
        this.rnd = rnd;
    }

    @Override
    public boolean shouldSpawn(int turn) {
        return rnd.nextDouble() < probability;
    }
}
