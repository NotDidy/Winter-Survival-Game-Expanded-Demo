package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import game.coatings.Coatable;
import game.coatings.Coating;

public class CoatWeaponAction extends Action {

    private final Coatable weapon;
    private final Coating coating;
    private final Item coatingItem; // e.g. Yewberry

    public CoatWeaponAction(Coatable weapon, Coating coating, Item coatingItem) {
        this.weapon = weapon;
        this.coating = coating;
        this.coatingItem = coatingItem;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        weapon.applyCoating(coating);
        actor.removeItemFromInventory(coatingItem);
        return actor + " coats their " + weapon + " with " + coating.getName() + ".";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " coats " + weapon + " with " + coating.getName();
    }
}
