package dk.quack.cbse.common.bullet;

import dk.quack.cbse.common.data.Entity;
import dk.quack.cbse.common.data.GameData;

public interface BulletSPI {

    /**
     * Creates a bullet from a shooter entity.
     *
     * Precondition: shooter has position, rotation, and owner/type information.
     * Postcondition: the returned bullet is ready to be added to the world.
     */
    Entity createBullet(Entity shooter, GameData gameData);
}
