# CBSE-Game

## Build and Run

Build all modules and copy runtime artifacts:

```bash
mvn clean install
```

Run the JavaFX game:

```bash
mvn exec:exec
```

## JPMS Plugin Layer

The game uses normal JPMS services for the core gameplay components and a separate
JPMS `ModuleLayer` for replaceable plugins. The `Enemy` module is still built by Maven,
but its JAR is copied to `plugins/` instead of `mods-mvn/`.

`ServiceLocator` loads services from both places:

- `mods-mvn/`: normal module-path components such as Player, Bullet, Asteroids, and Collision.
- `plugins/`: layer-loaded components such as Enemy.

To demonstrate replacement or removal without recompiling `Core`, rebuild with
`mvn clean install`, then remove or replace `plugins/Enemy-1.0-SNAPSHOT.jar` before
running `mvn exec:exec`.
