package dk.quack.cbse.common.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorldTest {

    @Test
    void storesAndFiltersEntitiesByType() {
        World world = new World();
        Entity player = new Entity();
        player.setType(EntityType.PLAYER);
        Entity asteroid = new Entity();
        asteroid.setType(EntityType.ASTEROID);

        world.addEntity(player);
        world.addEntity(asteroid);

        assertEquals(2, world.getEntities().size());
        assertEquals(1, world.getEntities(EntityType.PLAYER).size());
        assertTrue(world.getEntities(EntityType.PLAYER).contains(player));
    }
}
