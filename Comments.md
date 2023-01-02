# World managent

Set an action on the loadItem in GameLaucherView.java, using a file chooser and pasing it to the GameLauncher.load methos as an argument.That method reads the file, makes the configuration and the levels list, and create a Game with them.

# Door managent

Only one door class exist, with and argument wich determines if it goes to the above level or below, and othe to check if its open or not. When the player pressed the space key, a sorrounding door is seached, and if it is close, a key is used to open it. A player can only move into a close door, and the level is change then. When changing the level, the player is position in the next level correspondind door, and sprites are updated in the gameEngine checkLevelChange mehod.

# Box movement

When the player tries to move into a box, it check if the box can move in the same direction. If it does, both the player and the box move respectively, if not, neither of them does.

# Gestion de monsters

In the GameEngine Update monsters, a random and posible movement for each monster is searched and the monster moveed into it. This hapens in a random but within a determined interval time. The speed is controlled changing the interval of posible time and frecuency of moves. The collisions are also check, and if the position is the correct and the monter is not in cool off mode, a live is lost from the player. 

# Bonus and Malus Management

At the bonus package we have all the things that the player have some interaction with it.   The most important thing in this package are the bombs which are the ones that have had more work, because we have the class to create them and then we have 2 different package ones to control the number and other one just to control the range of the explosion.


# Decor

This ones (Bomb, Box, strone, tree, door, primcess)  extends from decor and some of them has the implements of Walkable or takeble depending of what the player can do with them.
