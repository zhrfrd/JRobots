# JRobots
JRobots is a programming game based on [CRobots](https://crobots.deepthought.it/home.php) by Tom Poindexter. The objective of the game is to code your own robot and fight against other robots.

## 1. How to use JRobots
The syntaxt of JRobots is based on Java and, in order to create your own robot, you can use the different methods of the JRobots API listed below:

<br/>

### void runTurn()

This is the main function that gets called when the robot get launched. In here you'll write all the robot's instructions.

*Example*

```
public void runTurn() {
    // Write all you code in here
    // ....
}
```

<br/>

### double scan(int direction, int resolution)

This function scans the battlefield in a particular direction +/- the resolution specified (which it can be a number between 0 and 10 included); in this way the robot is able to scan a wider area but the scanning range is decreased.

*Example*: Turn the scanner to the position of 180 degrees and scan an area of angle 10 degrees. 

```
public void runTurn() {
    scan(180, 10);
}
```

<br/>

### double scan(int direction)

This function scan only the specified direction. Despite in this case the area scanned is just a simple line (instead of an wider area), the range in length is maximum.

*Example*: Turn the scanner to the position of 180 degrees without specifying the resolution. 

```
public void runTurn() {
    scan(180);
}
```

<br/>

### void shoot(int direction)

This is the function that allows your robot to attack other robots by firing a missile towards the direction specified.

*Example*: Move the robot towards the left wall and when it hits it, shoot one missile to the opposite direction.

```
public void runTurn() {
    move(180, 3)

    if (getPosX() == 0)
        shoot(0);
}
```

<br/>

### void move(int direction, int speed);

Move the robot towards the direction specified at the speed specified. The direction is specified in degreese between 0 and 359 included, and the speed can be between 0 and 5 included.

*Example*: Move the robot to the position of 90 degreese at the maximum speed of 5.

```
public void runTurn() {
    move(90, 5);
}
```

<br/>

### double getPosX()

Get the current X position of the robot.

*Example*: If the current X position of the robot is 75, change its direction of 90 degreese and move it towards that direction decreasing the speed by one point.

```
public void runTurn () {
    move(0, 5);

    if (getPosX() == 75)
        move (90, 4);
}
```

<br/>

### double getPosY()

Get the current Y position of the robot.

*Example*: If the current Y position of the robot is 50, change its direction of 90 degreese and move it towards that direction at the same speed.

```
public void runTurn() {
    move(90, 2);

    if (getPosY() == 50)
        move(90, 2);
}
```

<br/>

### boolean isAlive()

Check if the robot is alive or not.

*Example*: Keep the robot in the same position while it's alive.

```
public void runTurn() {
    while(isAlive()) {
        move(40, 0);
    }
}
```