package dk.quack.cbse.common.services;

import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService {

    void process(GameData gameData, World world);
}