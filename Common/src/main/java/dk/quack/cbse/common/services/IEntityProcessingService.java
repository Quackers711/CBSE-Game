package dk.quack.cbse.common.services;

import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;

public interface IEntityProcessingService {

    /**
     * Updates entities owned by the component for the current frame.
     *
     * Precondition: gameData and world are non-null and contain the current frame state.
     * Postcondition: the service may update, add, or remove only entities it is responsible for.
     *
     * @param gameData shared game state and input state
     * @param world mutable collection of game entities
     */
    void process(GameData gameData, World world);
}
