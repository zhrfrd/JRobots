# JRobots
JRobots is a programming game based on [CRobots](https://crobots.deepthought.it/home.php) by Tom Poindexter. The objective of the game is to code your own robot and fight against other robots.

## JRobots syntax
The syntaxt of JRobots is very simple and intuitive. You can use the conditional operators, loops and call functions.
...

**if** Conditional operator  
**for** Loop   
...

## JRobots functions
The following are in-build JRobots functions that you can use to control the behaviour of your robot.
  
### start()  
---
This is the main function that gets called when the robot get launched. In here you must include the `isAlive()` cycle in which you'll write all the movements and behaviours of your robot.
  
### scan(direction)  
---
Scan the battlefield in a specific direction.
  
### shoot(direction)  
---
The robot shoot in a specific direction.
  
### move(direction)  
---
Move the robot in a specific direction. The parameter **direction** is a String and can be one of these constant values: *UP*, *DOWN*, *LEFT* or *RIGHT*.  
  
*Example: Move the robot to the right.*  
`move(RIGHT)`
  
### getPosX()
---
Get the current X position of the robot.
  
### getPosY()
---
Get the current Y position of the robot.
  
### isAlive()
---
This function returns *true* if the current robot is still alive, *false* if it's dead.
You must enclose the logic of your robot inside a loop that checks its vital conditions each cycle.
  
*Example*
```
public void start() {
    while (isAlive()) {
        move(UP);
    }
}
```