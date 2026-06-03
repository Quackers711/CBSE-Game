package dk.quack.cbse.collision;

import dk.quack.cbse.common.data.Entity;
import dk.quack.cbse.common.data.EntityType;
import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollisionDetectorTest {

    @Test
    void collidesWhenRadiiOverlap() {
        Entity first = entity(EntityType.PLAYER, 0, 0, 10, "PLAYER");
        Entity second = entity(EntityType.ASTEROID, 15, 0, 10, "ASTEROID");

        assertTrue(new CollisionDetector().collides(first, second));
    }

    @Test
    void doesNotCollideWhenEntitiesAreApart() {
        Entity first = entity(EntityType.PLAYER, 0, 0, 10, "PLAYER");
        Entity second = entity(EntityType.ASTEROID, 30, 0, 10, "ASTEROID");

        assertFalse(new CollisionDetector().collides(first, second));
    }

    @Test
    void playerBulletDestroysEnemyAndAddsScore() {
        GameData gameData = new GameData();
        World world = new World();
        Entity bullet = entity(EntityType.BULLET, 100, 100, 3, "PLAYER");
        Entity enemy = entity(EntityType.ENEMY, 100, 100, 10, "ENEMY");
        enemy.setHitPoints(1);
        world.addEntity(bullet);
        world.addEntity(enemy);

        new CollisionDetector().process(gameData, world);

        assertEquals(50, gameData.getScore());
        assertFalse(world.getEntities().contains(bullet));
        assertFalse(world.getEntities().contains(enemy));
    }

    private static Entity entity(EntityType type, double x, double y, double radius, String owner) {
        Entity entity = new Entity();
        entity.setType(type);
        entity.setPosition(x, y);
        entity.setRadius(radius);
        entity.setOwner(owner);
        return entity;
    }
}
