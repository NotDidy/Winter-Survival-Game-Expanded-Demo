There are talking characters (NPCs).

There are traders who sell things at the market.

And there’s a magical being called The Oracle that knows everything.
The Oracle lives on the internet (that’s the API).

The Controller tells everyone what to do.

When the player talks to a Bard (MonologueNPC), the Bard asks the Oracle (API) for a new story.

When the player visits the market, the MarketTrader asks the Oracle for new prices.

Both of them use the ApiClient to talk to the Oracle safely.

Everything connects to the engine (your game’s system), so it runs inside the world.

Player -> talks to Bard
Bard -> asks Oracle (API) for story
Oracle -> sends new words
Bard -> speaks new story to player

Player -> checks item price
Trader -> asks Oracle for market factor
Oracle -> says “+10% today!”
Trader -> shows new price