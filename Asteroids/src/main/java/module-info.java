import dk.quack.cbse.common.asteroids.AsteroidSplitter;
import dk.quack.cbse.common.services.IEntityProcessingService;
import dk.quack.cbse.common.services.IGamePluginService;

module Asteroids {
    requires Common;
    requires CommonAsteroids;

    provides IGamePluginService with dk.quack.cbse.asteroids.AsteroidPlugin;
    provides IEntityProcessingService with dk.quack.cbse.asteroids.AsteroidProcessor;
    provides AsteroidSplitter with dk.quack.cbse.asteroids.AsteroidSplitterImpl;
}
