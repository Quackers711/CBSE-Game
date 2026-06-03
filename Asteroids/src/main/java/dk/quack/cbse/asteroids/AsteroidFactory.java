package dk.quack.cbse.asteroids;

import dk.quack.cbse.common.asteroids.Asteroid;
import dk.quack.cbse.common.data.EntityType;

final class AsteroidFactory {
    private AsteroidFactory() {
    }

    static Asteroid create(double x, double y, double radius, double direction) {
        double radians = Math.toRadians(direction);
        Asteroid asteroid = new Asteroid();
        asteroid.setType(EntityType.ASTEROID);
        asteroid.setOwner("ASTEROID");
        asteroid.setHitPoints(1);
        asteroid.setRadius(radius);
        asteroid.setPosition(x, y);
        asteroid.setDx(Math.cos(radians) * 35.0);
        asteroid.setDy(Math.sin(radians) * 35.0);
        asteroid.setRotation(direction);
        asteroid.setPolygonCoordinates(
                -radius, -radius * 0.4,
                -radius * 0.2, -radius,
                radius * 0.8, -radius * 0.5,
                radius, radius * 0.3,
                radius * 0.1, radius,
                -radius * 0.8, radius * 0.5
        );
        return asteroid;
    }
}
