# Snake

### Description
In order to beat the game, you need to get to the 5th level.
To beat each level, you need to achieve >= 200 score points.

You can score points by eating the red foods. This will
increase the length of the snake as well.

Level 0 is the easiest level becase you get no barriers.
All the levels after that have wall barriers that are
randomly distributed on the board. After each level,
the number of these barriers increase by 5.

During each level, up to 2 power ups (golden nuggets) will be
placed on the board. This will give you an extra life.

As with any Snakes game, you die if you run in to the wall or
the snake itself. This will decrease your life points.
If you lose all of your life points, the game is over.

### How to run the game
Run `make` and then `make run`

### Keys
* Arrow keys for movement
* `q` - quit
* `p` - pause
* `r` - restart
* `s` - start/continue

### Cool features
1. There are sound effects for death, eating food, eating powerUps
and leveling up.
2. I've added graident texture to the snake, the golden nuggets
and the walls.
3. The golden nugget is the power up that increments the snake's
life by one.
4. The level design is as described in the description above. Also,
at each level after 0, the snake can wrap around through gaps in the
walls.
5. The game difficulty increases by the number of barriers dropped
on the board.

### Screenshot
![Screenshot]
(https://github.com/icantsayrural/Snake/blob/master/screenshot.png)

### Development Environment
Linux Mint
Java Version 1.8.0_91

###Sources
Sounds: http://www.grsites.com/archive/sounds/category/23/
