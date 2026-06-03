package dk.quack.cbse.player;

import dk.quack.cbse.common.data.Entity;
import dk.quack.cbse.common.data.EntityType;
import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.GameKeys;
import dk.quack.cbse.common.data.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerControlSystemTest {

    @Test
    void thrustMovesPlayerForwardOverTime() {
        GameData gameData = new GameData();
        gameData.setDeltaTime(1.0);
        gameData.getKeys().setKey(GameKeys.UP, true);
        World world = new World();
        Entity player = new Player();
        player.setType(EntityType.PLAYER);
        player.setOwner("PLAYER");
        player.setRotation(0);
        player.setPosition(100, 100);
        world.addEntity(player);

        new PlayerControlSystem().process(gameData, world);

        assertTrue(player.getX() > 100);
    }
}
