package dk.quack.cbse.asteroids;

import dk.quack.cbse.common.asteroids.Asteroid;
import dk.quack.cbse.common.asteroids.AsteroidSplitter;
import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;

public class AsteroidSplitterImpl implements AsteroidSplitter {
    private static final double MIN_SPLIT_RADIUS = 14.0;

    @Override
    public void split(Asteroid asteroid, GameData gameData, World world) {
        world.removeEntity(asteroid);
        if (asteroid.getRadius() <= MIN_SPLIT_RADIUS) {
            return;
        }

        double childRadius = asteroid.getRadius() * 0.62;
        world.addEntity(AsteroidFactory.create(asteroid.getX(), asteroid.getY(), childRadius, asteroid.getRotation() + 35));
        world.addEntity(AsteroidFactory.create(asteroid.getX(), asteroid.getY(), childRadius, asteroid.getRotation() - 35));
    }
}
