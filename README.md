# CBSE-Game

## Build and Run

Build all modules and copy runtime artifacts:

```bash
mvn clean install
```

Run the JavaFX game:

```bash
mvn -N exec:exec
```

Run the scoring microservice in a separate terminal before starting the game:

```bash
mvn -pl ScoringService exec:java
```

The service prints `Scoring service listening on http://127.0.0.1:8080` when it is ready.
The game posts score increments to `http://127.0.0.1:8080` through Spring
`RestTemplate`. If the scoring service is not running, the game continues with its local
score and prints one warning.

Read the microservice score:

```bash
curl http://127.0.0.1:8080/score
```

Run the tests:

```bash
mvn test
```

## JPMS Plugin Layer

The game uses normal JPMS services for the core gameplay components and a separate
JPMS `ModuleLayer` for replaceable plugins. The Player, Bullet/Weapon, and Enemy
modules are still built by Maven, but their JARs are copied to `plugins/` instead of
`mods-mvn/`.

`ServiceLocator` loads services from both places:

- `mods-mvn/`: normal module-path components such as Asteroids and Collision.
- `plugins/`: layer-loaded replaceable components such as Player, Bullet/Weapon, and Enemy.

To demonstrate replacement or removal without recompiling `Core`, rebuild with
`mvn clean install`, then remove or replace one of these files before running
`mvn -N exec:exec`:

- `plugins/Player-1.0-SNAPSHOT.jar`
- `plugins/Bullet-1.0-SNAPSHOT.jar`
- `plugins/Enemy-1.0-SNAPSHOT.jar`

## Tests

The TestLab tests cover the core component contracts at unit level:

- `Common`: world storage and filtering by entity type.
- `Player`: player movement from input and frame delta.
- `Bullet`: bullet creation and movement.
- `Collision`: radius-based collision checks and enemy destruction scoring.
- `ScoringService`: score endpoint query parsing.
- `Core`: scoring client keeps the game running when the service is unavailable.
