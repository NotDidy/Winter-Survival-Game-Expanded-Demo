package game.actions;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Tradeable;
import game.library.GeminiAPIService;

import java.util.List;
import java.util.Scanner;

/**
 * Enables two Tradeable entities to swap items.
 */
public class TradeAction extends Action {
    private final Tradeable trader;
    private final Tradeable customer;
    private final GeminiAPIService api;

    public TradeAction(Tradeable trader, Tradeable customer, GeminiAPIService api) {
        this.trader = trader;
        this.customer = customer;
        this.api = api;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        List<Item> traderItems = trader.getInventoryItems();
        List<Item> customerItems = customer.getInventoryItems();

        if (customerItems.isEmpty()) return customer.getName() + " has no items to trade.";
        if (traderItems.isEmpty()) return trader.getName() + " has nothing to trade.";

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n" + trader.getName() + "'s items");
        for (int i = 0; i < traderItems.size(); i++)
            System.out.println((i + 1) + ". " + traderItems.get(i));

        System.out.print("Choose item number from " + trader.getName() + ": ");
        int traderChoice = scanner.nextInt() - 1;

        if (traderChoice < 0 || traderChoice >= traderItems.size()) return "Invalid choice.";

        Item traderItem = traderItems.get(traderChoice);

        System.out.println("\n--- " + customer.getName() + "'s items ---");
        for (int i = 0; i < customerItems.size(); i++)
            System.out.println((i + 1) + ". " + customerItems.get(i));

        System.out.print("Choose your item to trade (number): ");
        int customerChoice = scanner.nextInt() - 1;

        if (customerChoice < 0 || customerChoice >= customerItems.size()) return "Invalid choice.";

        Item customerItem = customerItems.get(customerChoice);

        trader.removeItem(traderItem);
        customer.removeItem(customerItem);
        trader.addItem(customerItem);
        customer.addItem(traderItem);

        String aiComment = api.generateResponse(
                "Describe this trade: the player traded " + customerItem + " for " + traderItem +
                        ". Respond like a fantasy merchant reacting to a barter."
        );

        return customer.getName() + " traded " + customerItem + " for " + traderItem + ".\n" +
                trader.getName() + " says: " + aiComment;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " trades with " + trader.getName();
    }
}