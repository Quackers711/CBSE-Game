package dk.quack.cbse.bullet;

import dk.quack.cbse.common.data.Entity;
import dk.quack.cbse.common.data.EntityType;
import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BulletControlSystemTest {

    @Test
    void createsBulletWithShooterOwnerAndForwardVelocity() {
        Entity shooter = new Entity();
        shooter.setOwner("PLAYER");
        shooter.setRotation(0);
        shooter.setRadius(10);
        shooter.setPosition(100, 100);

        Entity bullet = new BulletControlSystem().createBullet(shooter, new GameData());

        assertEquals(EntityType.BULLET, bullet.getType());
        assertEquals("PLAYER", bullet.getOwner());
        assertTrue(bullet.getDx() > 0);
    }

    @Test
    void processMovesBulletByDeltaTime() {
        GameData gameData = new GameData();
        gameData.setDeltaTime(1.0);
        World world = new World();
        Entity bullet = new Entity();
        bullet.setType(EntityType.BULLET);
        bullet.setPosition(50, 50);
        bullet.setDx(10);
        bullet.setDy(0);
        world.addEntity(bullet);

        new BulletControlSystem().process(gameData, world);

        assertEquals(60, bullet.getX());
    }
}
