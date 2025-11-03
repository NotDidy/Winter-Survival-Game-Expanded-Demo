package game.creatures;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.behaviors.AttackBehaviour;
import game.behaviors.WanderBehaviour;
import game.items.CursedAmulet;
import game.items.CursedItem;
import game.weapons.Claw;

public class Zombie extends Creature {
    private static final int ATTACK_PRIORITY = 30;
    private static final int WANDER_PRIORITY = 50;
    private final CursedItem linkedAmulet;

    public Zombie(String name, char displayChar, int hitPoints, CursedAmulet amulet) {
        super(name, displayChar, hitPoints);
        this.addBehaviour(ATTACK_PRIORITY, new AttackBehaviour());
        this.addBehaviour(WANDER_PRIORITY, new WanderBehaviour());
        this.setIntrinsicWeapon(new Claw());
        this.linkedAmulet = amulet;
    }

    @Override
    public String unconscious(GameMap map) {
        if (linkedAmulet != null) {
            map.locationOf(this).addItem(linkedAmulet);
            linkedAmulet.liftCurse();
        }

        map.removeActor(this);
        return this + " vanishes, the cursed amulet is dropped onto the ground.";
    }

    @Override
    public String unconscious(Actor actor, GameMap map) {
        if (linkedAmulet != null) {
            map.locationOf(this).addItem(linkedAmulet);
            linkedAmulet.liftCurse();
        }
        map.removeActor(this);
        return this + " vanishes, the cursed amulet is dropped onto the ground.";
    }
}
