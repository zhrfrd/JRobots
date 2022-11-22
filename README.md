# JRobots
JRobots is a programming game based on [CRobots](https://crobots.deepthought.it/home.php) by Tom Poindexter. The objective of the game is to code your own robot and fight against other robots.

## 1. How to use JRobots
The syntaxt of JRobots is very simple and intuitive. You can use the conditional operators, loops and call functions.
...

**if** Conditional operator  
**for** Loop   
...

The following are in-build JRobots functions that you can use to control the behaviour of your robot.
  
## runTurn()

This is the main function that gets called when the robot get launched. In here you'll write all the robot's instructions.

## scan(direction, resolution) and scan(direction)

Each robot is equipped with a radar that can scan the battlefield in a particolar direction. If there are no robots within the scan range the *scan()* function will return 0, otherwize it will return the *direction* where the enemy is positioned. You can invoke two types of scan() function:  

## scan(direction, resolution)

This function scans the battlefield in a particular direction +/- the resolution specified (which it can be a number between 0 and 10 included); in this way the robot is able to scan a wider area but the scanning range is decreased.

Example:  
`scan(98, 5);` - Scans the battleground in an area included between 93 and 103 degrees.

## scan(direction)

This function scan only the specified direction. Despite in this case the area scanned is just a simple line (instead of an wider area), the range in length is maximum.

Example:  
`scan(45);` - Scan the line from the robot to the wall in a direction of 45 degrees.
  
## shoot(direction, range)

This is the function that allows your robot to attack other robots. It fires a missile towards the direction specified, that will land in the point calculated from the range. 

*Note:* The range is not the point where the enemy is located, whilst the distance between it and your robot.

Example:  
`shoot(180, 50);` - Shoot a missile towards the direction of 180 degrees with a range of 50.
  
## move(direction)

Move the robot in a specific direction. The parameter **direction** is a String and can be one of these constant values: *UP*, *DOWN*, *LEFT* or *RIGHT*.  
  
Example:  
`move(RIGHT);` - Move the robot to the right.
  
## getPosX()

Get the current X position of the robot.
  
## getPosY()

Get the current Y position of the robot.
  
## isAlive()
This function returns *true* if the current robot is still alive, *false* if it's destroyed.
You must enclose the logic of your robot inside a loop that checks its vital conditions each cycle.