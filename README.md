# JRobots

JRobots is a programming game similar to [CRobots](https://crobots.deepthought.it/home.php). The objective of the game
is to code your own robot and fight against other robots.

**JRobots is still in development**, which means that it's still not fully functional so, if you want to contribute to
the code base you are more than welcome ;) Just read the [CONTRIBUTING.md](CONTRIBUTING.md) file to understand how to
set up the project and our guidelines.

## 1. Requirements

This is a Java project that uses unit testing with [Maven](https://maven.apache.org/).

- [Java](https://docs.oracle.com/javase/10/install/installation-jdk-and-jre-macos.htm#JSJIG-GUID-577CEA7C-E51C-416D-B9C6-B1469F45AC78) (
  Java 19 recommended).
- [Maven](https://maven.apache.org/).

Recommended IDE's:

- [Eclipse](https://www.eclipse.org/downloads/).
- [IntelliJ IDEA](https://www.jetbrains.com/idea/).
- [VS Code](https://code.visualstudio.com/).

## 2. Clone

Clone this repository inside your workspace:

`git clone git@github.com:zhrfrd/JRobots.git`

## 3. Install Maven

If you don't have Maven installed in your machine:

- Go to [Maven's download page]().
- Under the **Files** section, select the link relative to the **Binary zip archive**.

### MacOS and Linux

- Extract the downloaded content inside any chosen folder. In my case I decided to extract it inside the Library folder.
- Open your terminal and go to HOME by typing `cd $HOME`.
- and open the .zshrc file `cat > .zshrc`.
- Add Maven to the PATH environment variable by typing the two lines below, press **control** + **D** to save the file (
  If you saved correctly you should be able to see the new PATH by typing `car .zshrc`) and close the terminal:

```
export MAVEN_HOME=$HOME/Library/apache-maven-3.8.6
export PATH=$MAVEN_HOME/bin:$PATH
```

### Windows

- Extract the downloaded content inside any chosen folder. In my case I decided to extract it inside the Program Files
  folder.
- Open the System Properties by pressing **WinKey** + **Pause**.
- Go to **Advanced** tab.
- Select **Environment Variables**.
- Add the **PATH** to the user variables i.e. `C:\Program Files\apache-maven-3.8.7\bin`.

If the installation has been completed correctly, you should be able to verify the Maven version by typing: `mvn -v`.

## 4. Configure Maven (Eclipse)

To configure Maven make sure first to add the cloned project to the Project Explorer section.

- Right click on the JRobots repository.
- Select **Maven**.
- Select **Update Project..**.

## 5. Build your JRobot

The syntax of JRobots is based on Java and, in order to create your own robot, you can use the different methods of the
JRobots API listed below.

---

### void runTurn()

This is the main function that gets called when the robot get launched. In here you'll write all the robot's
instructions.

*Example*

```
public void runTurn() {
    // Write all you code in here
    // ....
}
```

---

### double scan(int direction, int resolution)

This function scans the battlefield in a particular direction +/- the resolution specified (which it can be a number
between 0 and 10 included); in this way the robot is able to scan a wider area but the scanning range is decreased.

*Example*: Turn the scanner to the position of 180 degrees and scan an area of angle 10 degrees.

```
public void runTurn() {
    scan(180, 10);
}
```

---

### double scan(int direction)

This function scan only the specified direction. Despite in this case the area scanned is just a simple line (instead of
an wider area), the range in length is maximum.

*Example*: Turn the scanner to the position of 180 degrees without specifying the resolution.

```
public void runTurn() {
    scan(180);
}
```

---

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

---

### void move(int direction, int speed)

Move the robot towards the direction specified at the speed specified. The direction is specified in degreese between 0
and 359 included, and the speed can be between 0 and 5 included.

*Example*: Move the robot to the position of 90 degreese at the maximum speed of 5.

```
public void runTurn() {
    move(90, 5);
}
```

---

### double getPosX()

Get the current X position of the robot.

*Example*: If the current X position of the robot is 75, change its direction of 90 degreese and move it towards that
direction decreasing the speed by one point.

```
public void runTurn () {
    move(0, 5);

    if (getPosX() == 75)
        move (90, 4);
}
```

---

### double getPosY()

Get the current Y position of the robot.

*Example*: If the current Y position of the robot is 50, change its direction of 90 degreese and move it towards that
direction at the same speed.

```
public void runTurn() {
    move(90, 2);

    if (getPosY() == 50)
        move(90, 2);
}
```

---

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