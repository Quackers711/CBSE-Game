package dk.quack.cbse.common.asteroids;

import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;

public interface AsteroidSplitter {

    /**
     * Splits or removes an asteroid after a collision.
     *
     * Precondition: asteroid is present in world and represents an asteroid entity.
     * Postcondition: the original asteroid is removed, and smaller asteroids may be added.
     */
    void split(Asteroid asteroid, GameData gameData, World world);
}
