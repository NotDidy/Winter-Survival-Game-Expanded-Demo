package game.actors;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import game.Flammable;
import game.actions.SilentAction;
import game.items.Bedroll;
import game.items.Bottle;
import game.items.Stick;
import game.items.StringItem;
import game.items.crafting.CraftingTable;
import game.statuses.Asleep;
import game.statuses.Poisonable;
import game.statuses.StunnedStatus;
import game.weapons.BareFist;

/**
 * Class representing the Player.
 * @author Adrian Kristanto, John Devadhas (34380507)
 * @version 1.3.0
 * @since 1.0.0
 */
public class Player extends Actor implements Flammable, Poisonable {

    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints, int hydrationLevel, int warmthLevel) {
        super(name, displayChar, hitPoints);
        this.enableAbility(Abilities.CAN_ATTACK);
        this.setIntrinsicWeapon(new BareFist());
        this.addNewStatistic(BaseAttributes.HEALTH, new BaseActorAttribute(hitPoints));
        this.addNewStatistic(PlayerAttributes.HYDRATION, new BaseActorAttribute(hydrationLevel));
        this.addNewStatistic(PlayerAttributes.WARMTH, new BaseActorAttribute(warmthLevel));
        Item bedroll = new Bedroll();
        this.addItemToInventory(bedroll);
        Item bottle = new Bottle();
        this.addItemToInventory(bottle);
        Item craftingTable = new CraftingTable();
        this.addItemToInventory(craftingTable);
        Item item;
        for (int i = 0; i < 12 ; i++) {
            item = new Stick();
            this.addItemToInventory(item);
        }
        for (int i = 0; i < 3 ; i++) {
            item = new StringItem();
            this.addItemToInventory(item);
        }
    }


    /**
     * Processes the user's turn.
     * @param actions collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do
     * interesting things in conjunction with Action.getNextAction()
     * @param map the map containing the Actor
     * @param display the I/O object to which messages may be written
     * @return  The action selected by the User.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        display.println(String.valueOf(this));
        if (!this.hasStatus(Asleep.class)) {
            int decreaseAmount = 1;
            this.modifyAttribute(PlayerAttributes.HYDRATION, ActorAttributeOperation.DECREASE, decreaseAmount);
            this.modifyAttribute(PlayerAttributes.WARMTH, ActorAttributeOperation.DECREASE, decreaseAmount);
        }
        display.println(PlayerAttributes.HYDRATION + ": " + this.getAttribute(PlayerAttributes.HYDRATION));
        display.println(PlayerAttributes.WARMTH + ": " + this.getAttribute(PlayerAttributes.WARMTH));

        if (this.isExhausted()) {
            map.removeActor(this);
            return new SilentAction();
        }

        if (this.hasStatus(StunnedStatus.class))
            return new SilentAction();

        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        // return/print the console menu
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }

    /**
     * Checks if the Player is fainted.
     * @return  true if the Player's hydration or warmth level is 0, else false.
     */
    public boolean isExhausted () {
        return this.getAttribute(PlayerAttributes.HYDRATION) == 0 || this.getAttribute(PlayerAttributes.WARMTH) == 0;
    }

    /**
     * Reduces the Player's health by a certain amount of damage.
     * @param damage    The damage applied to the Actor.
     */
    @Override
    public void burn(int damage) {
        this.hurt(damage);
    }

    @Override
    public void poison(int damage) {
        this.hurt(damage);
    }
}