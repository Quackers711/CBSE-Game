package dk.quack.cbse.common.services;

import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;

public interface IGamePluginService {

    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}