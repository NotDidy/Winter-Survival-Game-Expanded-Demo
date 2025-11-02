package game.spawning;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.statuses.Poisonable;
import game.statuses.PoisonedStatus;

/** Poisons all actors in the surrounding exits of the spawner tile. */
public class PoisonActorsInSurroundings implements SpawnEffect {
    private final int turns;
    private final int damagePerTurn;

    public PoisonActorsInSurroundings(int turns, int damagePerTurn) {
        this.turns = turns;
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void apply(Location spawnerLocation, Actor spawned) {
        for (Exit e : spawnerLocation.getExits()) {
            var dest = e.getDestination();
            if (dest.containsAnActor()) {
                Actor target = dest.getActor();
                target.asCapability(Poisonable.class).ifPresent(p ->
                        target.addStatus(new PoisonedStatus(p, turns, damagePerTurn))
                );
            }
        }
    }
}
