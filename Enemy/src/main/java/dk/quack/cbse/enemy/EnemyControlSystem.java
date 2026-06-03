package dk.quack.cbse.enemy;

import dk.quack.cbse.common.bullet.BulletSPI;
import dk.quack.cbse.common.data.Entity;
import dk.quack.cbse.common.data.EntityType;
import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;
import dk.quack.cbse.common.services.IEntityProcessingService;

import java.util.Comparator;
import java.util.ServiceLoader;

public class EnemyControlSystem implements IEntityProcessingService {
    private static final double ENEMY_SPEED = 45.0;
    private long lastShot;

    @Override
    public void process(GameData gameData, World world) {
        double dt = gameData.getDeltaTime();
        Entity player = world.getEntities(EntityType.PLAYER).stream().findFirst().orElse(null);
        for (Entity enemy : world.getEntities(EntityType.ENEMY)) {
            if (player != null) {
                double angle = Math.toDegrees(Math.atan2(player.getY() - enemy.getY(), player.getX() - enemy.getX()));
                enemy.setRotation(angle);
            } else {
                enemy.setRotation(enemy.getRotation() + 45.0 * dt);
            }

            double radians = Math.toRadians(enemy.getRotation());
            enemy.setX(wrap(enemy.getX() + Math.cos(radians) * ENEMY_SPEED * dt, gameData.getDisplayWidth()));
            enemy.setY(wrap(enemy.getY() + Math.sin(radians) * ENEMY_SPEED * dt, gameData.getDisplayHeight()));

            long now = System.currentTimeMillis();
            if (player != null && now - lastShot > 1_200) {
                bulletServices().stream()
                        .map(ServiceLoader.Provider::get)
                        .min(Comparator.comparing(provider -> provider.getClass().getName()))
                        .ifPresent(bulletSPI -> world.addEntity(bulletSPI.createBullet(enemy, gameData)));
                lastShot = now;
            }
        }
    }

    private static double wrap(double value, double max) {
        if (value < 0) {
            return max + value;
        }
        if (value > max) {
            return value - max;
        }
        return value;
    }

    private static ServiceLoader<BulletSPI> bulletServices() {
        ModuleLayer layer = EnemyControlSystem.class.getModule().getLayer();
        if (layer == null) {
            return ServiceLoader.load(BulletSPI.class);
        }
        return ServiceLoader.load(layer, BulletSPI.class);
    }
}
