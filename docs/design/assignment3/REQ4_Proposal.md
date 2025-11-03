We also would like to incorporate a Cursed Item mechanic where certain items have better stats compared to
normal items but at the cost of it inflicting a curse on the player. One of the items would be a Cursed Sword
where if the Player has the Cursed Weapon within its inventory for more than a certain amount of rounds, the item drops 
and a ghost spawns near the player and attacks the player. The Cursed Weapon would have a really high hit chance and damage value but if it is not dropped
within 5 rounds from when it got picked up by the player, the item would drop and the spawn a ghost within a random adjacent
block from the player. The Player would have to kill the ghost first and only then would the weapon drop again to be used
but the mechanic still applies. Another Item would be a CursedAmulet where it would provide passive healing but if the player does not drop
the amulet within 5 rounds, the amulet will spawn a zombie and the amulet will disappear until the zombie is killed in which the
amulet will drop onto the ground.

Technical Implementation 
For example, There would be a Cursed Weapon concrete class (Cursed Sword for now) that operates exactly like a normal weapon. The tick method
for the weapon will count down until the timer reaches the curse threshold. After the timer matches the cursed threshold,
The weapon will spawn a ghost around the player and remove itself from the player's inventory. Upon
killing the ghost, the ghost will drop the same Cursed Weapon on the same position it got killed.