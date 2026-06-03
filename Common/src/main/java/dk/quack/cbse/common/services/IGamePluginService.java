package dk.quack.cbse.common.services;

import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;

public interface IGamePluginService {

    /**
     * Adds initial entities owned by the component to the world.
     *
     * Precondition: gameData and world are initialized.
     * Postcondition: required startup entities for the component are present in the world.
     */
    void start(GameData gameData, World world);

    /**
     * Removes entities owned by the component from the world.
     *
     * Precondition: gameData and world are initialized.
     * Postcondition: component-owned entities are removed or otherwise inactive.
     */
    void stop(GameData gameData, World world);
}
