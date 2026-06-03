package dk.quack.cbse.bullet;

import dk.quack.cbse.common.bullet.Bullet;
import dk.quack.cbse.common.bullet.BulletSPI;
import dk.quack.cbse.common.data.Entity;
import dk.quack.cbse.common.data.EntityType;
import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;
import dk.quack.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {
    private static final double BULLET_SPEED = 260.0;
    private static final long BULLET_TTL_MS = 1_400;

    @Override
    public void process(GameData gameData, World world) {
        long now = System.currentTimeMillis();
        double dt = gameData.getDeltaTime();
        for (Entity bullet : world.getEntities(EntityType.BULLET)) {
            bullet.setX(bullet.getX() + bullet.getDx() * dt);
            bullet.setY(bullet.getY() + bullet.getDy() * dt);
            if (now - bullet.getCreatedAt() > BULLET_TTL_MS
                    || bullet.getX() < 0
                    || bullet.getX() > gameData.getDisplayWidth()
                    || bullet.getY() < 0
                    || bullet.getY() > gameData.getDisplayHeight()) {
                world.removeEntity(bullet);
            }
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        double radians = Math.toRadians(shooter.getRotation());
        double changeX = Math.cos(radians);
        double changeY = Math.sin(radians);

        Bullet bullet = new Bullet();
        bullet.setType(EntityType.BULLET);
        bullet.setOwner(shooter.getOwner());
        bullet.setHitPoints(1);
        bullet.setRadius(3);
        bullet.setRotation(shooter.getRotation());
        bullet.setPosition(shooter.getX() + changeX * (shooter.getRadius() + 6), shooter.getY() + changeY * (shooter.getRadius() + 6));
        bullet.setDx(changeX * BULLET_SPEED + shooter.getDx());
        bullet.setDy(changeY * BULLET_SPEED + shooter.getDy());
        bullet.setPolygonCoordinates(-2, -2, 4, 0, -2, 2);
        return bullet;
    }
}
