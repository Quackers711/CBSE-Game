package dk.quack.cbse.player;

import dk.quack.cbse.common.data.EntityType;
import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;
import dk.quack.cbse.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {
    private Player player;

    @Override
    public void start(GameData gameData, World world) {
        player = new Player();
        player.setType(EntityType.PLAYER);
        player.setOwner("PLAYER");
        player.setHitPoints(3);
        player.setRadius(10);
        player.setPosition(gameData.getDisplayWidth() / 2.0, gameData.getDisplayHeight() / 2.0);
        player.setPolygonCoordinates(-10, -8, 16, 0, -10, 8);
        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        if (player != null) {
            world.removeEntity(player);
        }
    }
}
