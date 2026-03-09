# JRobots
JRobots is a browser-based programming battle arena similar to [CRobots](https://crobots.deepthought.it/home.php) 
but written in Java.

Players can program their robots using a custom "mini-language" and watch them fight inside a deterministic (the same 
input always produce the same result) simulation engine.

<strong>Note:</strong> JRobots is still in development and still missing the main functionalities.

## Tech Stack
- Java 12 +
- Spring Boot
  - Spring Web
  - SpringBoot DevTools
  - Lombok (optional)
- Maven

## Project structure
```
JRobots/
├── backend/
├── frontend/
├── pom.xml
├── README.md
└── .gitignore
```

### Backend package structure
```
JRobots/
└── backend/
    ├── api/   # REST API endpoints
    ├── bots/   # Built-in robots for testing
    ├── config/   # Currently EMPTY
    ├── dsl/   # Currently EMPTY
    ├── engine/   # Simulation engine (robots, bullets, physics)
    └── replay/   # Replay data structures (snapshots + events)
```

## Current features
- Robot movement and turning.
- Bullet firing system.
- Bullet physics and collision detection.
- Energy-based damage system.
- Match result (WIN / DRAW).
- Replay generation.
- Event timeline per tick.

## Design
The game runs at a certain number of ticks per seconds during which a series of tasks are completed in order:
1) Engine resets all the actions.
2) Controllers request actions.
3) Spawn bullets if fire is requested by a robot.
4) Apply robot physics.
5) Apply bullet physics.
6) Detect collision between bullets and robots.
7) Remove dead bullets.
8) Generate events.
9) Record snapshot.

## Replay system
Every match generates a replay containing the full history of the simulation by saving a snapshot at each tick.

A snapshot looks like this:

```json
{
  "tick": 42,
  "robots": [
    { "id": 1, "x": 150.0, "y": 320.0, "energy": 80.0},
    { "id": 2, "x": 600.0, "y": 310.0, "energy": 10.0}
  ],
  "bullets": [
    { "id": 12, "ownerId": 1, "x": 420.0, "y": 305.0}
  ],
  "events": [
    { "type": "FIRE", "robotId": 1, "bulletId": 12 },
    { "type": "HIT", "bulletId": 12, "ownerId": 1, "targetRobotId": 2, "damage": 10 },
    { "type": "DEATH", "robotId": 2 },
    { "type": "END", "result": "WIN", "winnerId": 1 }
  ]
}
```

## Events
Events are important gameplay actions that happen during a tick.

| Event | Description |
|-------| ----------- |
| FIRE  | A robot fired a bullet|
| HIT   | A bullet hit a robot|
| DEATH | A robot’s energy reached zero|
| END   | The match ended (WIN or DRAW)|