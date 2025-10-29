package game.creatures;

import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import game.behaviors.AttackBehaviour;
import game.weapons.CrocBite;

public class Crocodile extends Creature {
    private static final int ATTACK_PRIORITY = 30;
    private static final int WARMTH_START = 55;

    public Crocodile(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addBehaviour(ATTACK_PRIORITY, new AttackBehaviour());
        this.setIntrinsicWeapon(new CrocBite());
        this.addNewStatistic(CreatureAttributes.WARMTH, new BaseActorAttribute(WARMTH_START));
    }

    public Crocodile() {
        this("Crocodile", '<', 300);
    }
}
