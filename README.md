# JRobots
JRobots is a programming game based on [CRobots](https://crobots.deepthought.it/home.php) by Tom Poindexter. The objective of the game is to code your own robot and fight against other robots.

## 1. How to use JRobots
The syntaxt of JRobots is based on Java and, in order to create your own robot, you can use the different methods of the JRobots API listed below:

<br />

### 1.1. `runTurn()`

This is the main function that gets called when the robot get launched. In here you'll write all the robot's instructions.

<br />

### 1.2. `scan(int direction, int resolution)` and `scan(direction)`

Each robot is equipped with a radar that can scan the battlefield in a particolar direction. If there are no robots within the scan range the *scan()* function will return 0, otherwize it will return the *direction* where the enemy is positioned. You can invoke two types of scan() function:  

#### 1.2.1. `scan(int direction, int resolution)`

This function scans the battlefield in a particular direction +/- the resolution specified (which it can be a number between 0 and 10 included); in this way the robot is able to scan a wider area but the scanning range is decreased.

Example:  
`scan(98, 5);` - Scans the battleground in an area included between 93 and 103 degrees.

#### 1.2.2. `scan(int direction)`

This function scan only the specified direction. Despite in this case the area scanned is just a simple line (instead of an wider area), the range in length is maximum.

Example:  
`scan(45);` - Scan the line from the robot to the wall in a direction of 45 degrees.

<br />

### 1.3. `shoot(int direction, int range)`

This is the function that allows your robot to attack other robots. It fires a missile towards the direction specified, that will land in the point calculated from the range. 

*Note:* The range is not the point where the enemy is located, whilst the distance between it and your robot.

Example:  
`shoot(180, 50);` - Shoot a missile towards the direction of 180 degrees with a range of 50.

<br />

### 1.4. `move(int direction, int speed);`

Move the robot towards the direction specified at the speed specified.

**Parameters**

`int direction`: The direction in degreed towards where you want the robot to move. The range value is between 0 and 359 included.

`int speed`: The speed at which the robot travels. The range value is between 0 and 5 included.

**Return**

`void`

*Example*: `move(127, 5);`

<br />

### 1.5. `getPosX()`

Get the current X position of the robot.

**Return**

`double` The X coordinate of the robot. 

<br />

### 1.6. `getPosY()`

Get the current Y position of the robot.

**Return**

`double` The Y coordinate of the robot.

<br />

### 1.7. `isAlive()`

Check if the robot is alive or not.

**Return**

`boolean` True if the robot is still alive, false otherwise.