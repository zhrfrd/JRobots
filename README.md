# JRobots
JRobots is a programming game based on [CRobots](https://crobots.deepthought.it/home.php) by Tom Poindexter. The objective of the game is to code your own robot and fight against other robots.

## 1. How to use JRobots
The syntaxt of JRobots is based on Java and, in order to create your own robot, you can use the different methods of the JRobots API listed below:

**runTurn()**

This is the main function that gets called when the robot get launched. In here you'll write all the robot's instructions.

*Example*

```
runTurn() {
    // Write all you code in here
    // ....
}
```

**scan(int direction, int resolution)**

This function scans the battlefield in a particular direction +/- the resolution specified (which it can be a number between 0 and 10 included); in this way the robot is able to scan a wider area but the scanning range is decreased.

*Example*: Turn the scanner to the position of 180 degrees and scan an area of angle 10 degrees. 

```
runTurn() {
    scan(180, 10);
}
```

**scan(int direction)**

This function scan only the specified direction. Despite in this case the area scanned is just a simple line (instead of an wider area), the range in length is maximum.

*Example*: Turn the scanner to the position of 180 degrees without specifying the resolution. 

```
runTurn() {
    scan(180);
}
```

**shoot(int direction, int range)**

This is the function that allows your robot to attack other robots. It fires a missile towards the direction specified, that will land in the point calculated from the range. 

*Note:* The range is not the point where the enemy is located, whilst the distance between it and your robot.

**move(int direction, int speed);**

Move the robot towards the direction specified at the speed specified.

**Parameters**

`int direction`: The direction in degreed towards where you want the robot to move. The range value is between 0 and 359 included.

`int speed`: The speed at which the robot travels. The range value is between 0 and 5 included.

**Return**

`void`

**getPosX()**

Get the current X position of the robot.

**Return**

`double` The X coordinate of the robot. 

**getPosY()**

Get the current Y position of the robot.

**Return**

`double` The Y coordinate of the robot.

**isAlive()**

Check if the robot is alive or not.

**Return**

`boolean` True if the robot is still alive, false otherwise.