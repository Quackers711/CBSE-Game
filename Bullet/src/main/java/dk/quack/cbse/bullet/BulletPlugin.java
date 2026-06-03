package dk.quack.cbse.bullet;

import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;
import dk.quack.cbse.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities().stream()
                .filter(entity -> entity instanceof dk.quack.cbse.common.bullet.Bullet)
                .toList()
                .forEach(world::removeEntity);
    }
}
