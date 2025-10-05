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
import game.items.Bedroll;
import game.items.Bottle;
import game.statuses.Asleep;
import game.weapons.BareFist;

/**
 * Class representing the Player.
 * @author Adrian Kristanto, John Devadhas (34380507)
 * @version 1.1.0
 * @since 1.0.0
 */
public class Player extends Actor implements Flammable {

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
        Item bedroll = new Bedroll("Bedroll", '=', true);
        this.addItemToInventory(bedroll);
        Item bottle = new Bottle("Bottle", 'o', true);
        this.addItemToInventory(bottle);
    }

    // ========= ACCESSORS (GETTER) & MUTATOR (SETTER) ========== //


    // ================================== FUNCTIONS & METHODS ================================== //

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
            this.modifyAttribute(PlayerAttributes.HYDRATION, ActorAttributeOperation.DECREASE, 1);
            this.modifyAttribute(PlayerAttributes.WARMTH, ActorAttributeOperation.DECREASE, 1);
        }
        display.println(PlayerAttributes.HYDRATION + ": " + this.getAttribute(PlayerAttributes.HYDRATION));
        display.println(PlayerAttributes.WARMTH + ": " + this.getAttribute(PlayerAttributes.WARMTH));
        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        if (this.isExhausted())
            map.removeActor(this);

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
}