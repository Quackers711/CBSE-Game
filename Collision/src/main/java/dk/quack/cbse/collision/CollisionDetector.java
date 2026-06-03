package dk.quack.cbse.collision;

import dk.quack.cbse.common.asteroids.Asteroid;
import dk.quack.cbse.common.asteroids.AsteroidSplitter;
import dk.quack.cbse.common.data.Entity;
import dk.quack.cbse.common.data.EntityType;
import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;
import dk.quack.cbse.common.services.IPostEntityProcessingService;

import java.util.List;
import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = List.copyOf(world.getEntities());
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                Entity first = entities.get(i);
                Entity second = entities.get(j);
                if (world.getEntities().contains(first)
                        && world.getEntities().contains(second)
                        && collides(first, second)) {
                    handleCollision(first, second, gameData, world);
                }
            }
        }
    }

    public boolean collides(Entity first, Entity second) {
        double dx = first.getX() - second.getX();
        double dy = first.getY() - second.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < first.getRadius() + second.getRadius();
    }

    private void handleCollision(Entity first, Entity second, GameData gameData, World world) {
        if (isFriendlyBulletCollision(first, second)) {
            return;
        }
        if (isBulletAsteroid(first, second)) {
            removeBulletAndSplitAsteroid(first, second, gameData, world);
            return;
        }
        if (isShipAsteroid(first, second)) {
            damageShipAndSplitAsteroid(first, second, gameData, world);
            return;
        }
        if (isBulletShip(first, second)) {
            damageShipAndRemoveBullet(first, second, gameData, world);
        }
    }

    private boolean isFriendlyBulletCollision(Entity first, Entity second) {
        return (first.getType() == EntityType.BULLET || second.getType() == EntityType.BULLET)
                && first.getOwner().equals(second.getOwner());
    }

    private boolean isBulletAsteroid(Entity first, Entity second) {
        return hasTypes(first, second, EntityType.BULLET, EntityType.ASTEROID);
    }

    private boolean isShipAsteroid(Entity first, Entity second) {
        return isShip(first) && second.getType() == EntityType.ASTEROID
                || isShip(second) && first.getType() == EntityType.ASTEROID;
    }

    private boolean isBulletShip(Entity first, Entity second) {
        return first.getType() == EntityType.BULLET && isShip(second)
                || second.getType() == EntityType.BULLET && isShip(first);
    }

    private boolean hasTypes(Entity first, Entity second, EntityType firstType, EntityType secondType) {
        return first.getType() == firstType && second.getType() == secondType
                || first.getType() == secondType && second.getType() == firstType;
    }

    private boolean isShip(Entity entity) {
        return entity.getType() == EntityType.PLAYER || entity.getType() == EntityType.ENEMY;
    }

    private void removeBulletAndSplitAsteroid(Entity first, Entity second, GameData gameData, World world) {
        Entity bullet = first.getType() == EntityType.BULLET ? first : second;
        Entity asteroid = first.getType() == EntityType.ASTEROID ? first : second;
        world.removeEntity(bullet);
        splitAsteroid(asteroid, gameData, world);
        gameData.addScore(10);
    }

    private void damageShipAndSplitAsteroid(Entity first, Entity second, GameData gameData, World world) {
        Entity ship = isShip(first) ? first : second;
        Entity asteroid = first.getType() == EntityType.ASTEROID ? first : second;
        ship.damage(1);
        if (ship.isDead()) {
            world.removeEntity(ship);
        }
        splitAsteroid(asteroid, gameData, world);
    }

    private void damageShipAndRemoveBullet(Entity first, Entity second, GameData gameData, World world) {
        Entity bullet = first.getType() == EntityType.BULLET ? first : second;
        Entity ship = isShip(first) ? first : second;
        ship.damage(1);
        world.removeEntity(bullet);
        if (ship.isDead()) {
            world.removeEntity(ship);
            if (ship.getType() == EntityType.ENEMY) {
                gameData.addScore(50);
            }
        }
    }

    private void splitAsteroid(Entity entity, GameData gameData, World world) {
        if (!(entity instanceof Asteroid asteroid)) {
            world.removeEntity(entity);
            return;
        }
        for (AsteroidSplitter splitter : ServiceLoader.load(AsteroidSplitter.class)) {
            splitter.split(asteroid, gameData, world);
            return;
        }
        world.removeEntity(entity);
    }
}
