package game.trading;

/**
 * Retrieves Bitcoin prices to simulate fluctuating market-based trading.
 * @author Jeffrey Lam PO Yuen (35306750)
 * @version 1.1.0
 * @since 1.1.0
 */
public class CoinDeskPriceSource extends ApiPriceFetcher implements PriceSource {

    private final String apiUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @Override
    public double getPrice(String itemName) {
        try {
            String json = sendGet(apiUrl);

            int usdIndex = json.indexOf("\"rate_float\"");
            if (usdIndex != -1) {
                String sub = json.substring(usdIndex);
                String number = sub.replaceAll("[^0-9.]", " ").trim().split(" ")[0];
                double btcPrice = Double.parseDouble(number);
                return btcPrice / 1000.0; // simulate item price scaling
            }

            return 50.0;
        } catch (Exception e) {
            System.out.println("(API unreachable, using fallback price)");
            return 50.0;
        }
    }
}
