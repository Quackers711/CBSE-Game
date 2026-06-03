import dk.quack.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonAsteroids;

    uses dk.quack.cbse.common.asteroids.AsteroidSplitter;
    provides IPostEntityProcessingService with dk.quack.cbse.collision.CollisionDetector;
}
