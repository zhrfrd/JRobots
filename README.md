# JRobots
JRobots is a browser-based programming battle arena similar to [CRobots](https://crobots.deepthought.it/home.php) 
but written in Java. The objective of the game is to code your own robot using JRobot's custom DSL and fight against 
other robots.

<strong>Note:</strong> JRobots is still in development and still missing the main functionalities.

## Tech Stack
- Java 12 +
- Spring Boot
  - Spring Web
  - SpringBoot DevTools
  - Lombok (optional)
- Maven

## Design
The game runs at a certain number of ticks per seconds during which a series of tasks are completed in order:
1) Engine resets all the actions.
2) Controllers request actions.
3) Spawn bullets if fire is requested by a robot.
4) Apply robot physics.
5) Apply bullet physics.
6) Detect collision between bullets and robots.
7) Remove dead bullets.
8) Record snapshot.