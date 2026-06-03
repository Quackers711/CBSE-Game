package dk.quack.cbse.player;

import dk.quack.cbse.common.bullet.BulletSPI;
import dk.quack.cbse.common.data.Entity;
import dk.quack.cbse.common.data.EntityType;
import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.GameKeys;
import dk.quack.cbse.common.data.World;
import dk.quack.cbse.common.services.IEntityProcessingService;

import java.util.ServiceLoader;

public class PlayerControlSystem implements IEntityProcessingService {
    private static final double TURN_SPEED = 180.0;
    private static final double THRUST = 80.0;
    private static final double MAX_SPEED = 170.0;

    @Override
    public void process(GameData gameData, World world) {
        double dt = gameData.getDeltaTime();
        for (Entity player : world.getEntities(EntityType.PLAYER)) {
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - TURN_SPEED * dt);
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + TURN_SPEED * dt);
            }
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double radians = Math.toRadians(player.getRotation());
                player.setDx(clamp(player.getDx() + Math.cos(radians) * THRUST * dt, -MAX_SPEED, MAX_SPEED));
                player.setDy(clamp(player.getDy() + Math.sin(radians) * THRUST * dt, -MAX_SPEED, MAX_SPEED));
            }
            if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
                for (BulletSPI bulletSPI : bulletServices()) {
                    world.addEntity(bulletSPI.createBullet(player, gameData));
                }
            }

            player.setX(wrap(player.getX() + player.getDx() * dt, gameData.getDisplayWidth()));
            player.setY(wrap(player.getY() + player.getDy() * dt, gameData.getDisplayHeight()));
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

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private static Iterable<BulletSPI> bulletServices() {
        ModuleLayer layer = PlayerControlSystem.class.getModule().getLayer();
        if (layer == null) {
            return ServiceLoader.load(BulletSPI.class);
        }
        return ServiceLoader.load(layer, BulletSPI.class);
    }
}
