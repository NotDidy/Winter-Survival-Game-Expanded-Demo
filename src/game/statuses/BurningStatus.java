package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.actors.Actor;

public class BurningStatus implements Status {
    private int turnsRemaining;
    private int damagePerTurn;
    private int stacks;

    public BurningStatus(){
        this.turnsRemaining = 5;
        this.damagePerTurn = 5;
        this.stacks = 1;
    }

    @Override
    public boolean isStatusActive() {
        return stacks > 0;
    }

    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        //apply burn damage
        if (gameEntity instanceof Actor actor) {
            int totalDamage = stacks * damagePerTurn;
            actor.hurt(totalDamage);

            turnsRemaining--;
            System.out.println(actor + " burns for " + totalDamage + " damage!");

            //check if this stack is finished burning
            if (turnsRemaining <= 0) {
                stacks--;
                if (stacks > 0) {
                    // Start next stack
                    turnsRemaining = 5;
                }
            }
        }
    }

    public void addStack(){
        this.stacks++;
        this.turnsRemaining = 5; //reset the timer for new stack
    }

    public int getStack(){
        return stacks;
    }

    public int getTurnsRemaining(){
        return turnsRemaining;
    }
}
