package dk.quack.cbse.common.services;

import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;

public interface IPostEntityProcessingService {

    /**
     * Performs processing that must happen after all entity processors have run.
     *
     * Precondition: gameData and world contain the current frame's updated entities.
     * Postcondition: collisions, cleanup, or other cross-component effects are applied.
     */
    void process(GameData gameData, World world);
}
