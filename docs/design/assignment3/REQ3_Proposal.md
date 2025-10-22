We propose the idea of crafting items using other items as an Action for Player. The Crafting Recipe must be
predetermined during the setup before the game runs.

For example, to craft a sword, The Player first needs to obtain
a stick item and stone item which then outputs a Craft Action option to
craft the sword.

More technical details (not complete, but a rough idea):
Add new Item classes like Stick and Stone etc. Will have CraftingRecipe interface/abstract class which child Recipe 
concrete classes can be developed to store details of what items are required, CraftAction that extends Action

