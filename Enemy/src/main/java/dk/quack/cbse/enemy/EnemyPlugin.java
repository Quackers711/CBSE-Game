package dk.quack.cbse.enemy;

import dk.quack.cbse.common.data.EntityType;
import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;
import dk.quack.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    private Enemy enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = new Enemy();
        enemy.setType(EntityType.ENEMY);
        enemy.setOwner("ENEMY");
        enemy.setHitPoints(2);
        enemy.setRadius(11);
        enemy.setPosition(90, 90);
        enemy.setRotation(35);
        enemy.setPolygonCoordinates(-10, -8, 14, 0, -10, 8, -5, 0);
        world.addEntity(enemy);
    }

    @Override
    public void stop(GameData gameData, World world) {
        if (enemy != null) {
            world.removeEntity(enemy);
        }
    }
}
