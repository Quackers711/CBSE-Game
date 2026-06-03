package dk.quack.cbse.asteroids;

import dk.quack.cbse.common.asteroids.Asteroid;
import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;
import dk.quack.cbse.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {
    private static final int ASTEROID_COUNT = 5;
    private final Random random = new Random();

    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < ASTEROID_COUNT; i++) {
            world.addEntity(AsteroidFactory.create(
                    random.nextInt(gameData.getDisplayWidth()),
                    random.nextInt(gameData.getDisplayHeight()),
                    22 + random.nextInt(16),
                    random.nextDouble() * 360
            ));
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities().stream()
                .filter(entity -> entity instanceof Asteroid)
                .toList()
                .forEach(world::removeEntity);
    }
}
