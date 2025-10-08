package game.grounds.spawners;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.statuses.SpawnStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/** Represents cave ground (symbol 'C') for REQ2 animal spawning. */
public class Cave extends Ground implements Spawner {
    private final Set<Supplier<Actor>> spawnableActors;
    private static final int SPAWN_ACTOR_TICKS = 5;

    /**
     * Cave constructor.
     */
    public Cave() {
        super('C', "Cave");
        this.addStatus(new SpawnStatus(SPAWN_ACTOR_TICKS));
        this.enableAbility(SpawnerAbilities.CAN_SPAWN);
        this.spawnableActors = new HashSet<>();
    }

    /**
     * Adds a new Actor supplier that this Cave can spawn.
     *
     * @param actorSupplier The Actor supplier to be added.
     */
    @Override
    public boolean addSpawnableActor(Supplier<Actor> actorSupplier) {
        return spawnableActors.add(actorSupplier);
    }

    /**
     * Removes a spawnable Actor supplier from this Cave.
     *
     * @param actorSupplier The Actor supplier to remove.
     */
    @Override
    public boolean removeSpawnableActor(Supplier<Actor> actorSupplier) {
        return spawnableActors.remove(actorSupplier);
    }

    /**
     * Returns a copy of the spawnable actor supplier set.
     */
    public Set<Supplier<Actor>> getSpawnableActors() {
        return new HashSet<>(spawnableActors);
    }

    /**
     * Cave tick. Current implementation processes whether it is time for the Cave to spawn actors.
     *
     * @param location The location of the Ground
     */
    public void tick(Location location) {
//        System.out.println(this.hasAbility(SpawnerAbilities.CAN_SPAWN));
        if (this.hasAbility(SpawnerAbilities.CAN_SPAWN)) {
//            System.out.println(!this.hasStatus(SpawnStatus.class));
            if (!this.hasStatus(SpawnStatus.class)) {
                try {
                    this.spawnActor(location);
                    this.addStatus(new SpawnStatus(SPAWN_ACTOR_TICKS));
                } catch (GameEngineException e) {
                    Display display = new Display();
                    display.println("Failed to spawn actor: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Generates Apple objects to be dropped around the AppleTree.
     * @param location  The location of the AppleTree
     */
    @Override
    public void spawnActor(Location location) throws GameEngineException {
        if (location.containsAnActor() || spawnableActors.isEmpty())
            return;

        List<Supplier<Actor>> actorList = new ArrayList<>(spawnableActors);
        int randomIndex = (int) (Math.random() * actorList.size());
        Actor actor = actorList.get(randomIndex).get();

        try {
            location.map().addActor(actor, location);
        } catch (GameEngineException e) {
            throw new GameEngineException("Could not instantiate " + actor + ": " + e);
        }
    }
}
