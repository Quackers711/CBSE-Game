package dk.quack.cbse.asteroids;

import dk.quack.cbse.common.data.Entity;
import dk.quack.cbse.common.data.EntityType;
import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;
import dk.quack.cbse.common.services.IEntityProcessingService;

public class AsteroidProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        double dt = gameData.getDeltaTime();
        for (Entity asteroid : world.getEntities(EntityType.ASTEROID)) {
            asteroid.setX(wrap(asteroid.getX() + asteroid.getDx() * dt, gameData.getDisplayWidth()));
            asteroid.setY(wrap(asteroid.getY() + asteroid.getDy() * dt, gameData.getDisplayHeight()));
            asteroid.setRotation(asteroid.getRotation() + 18.0 * dt);
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
}
