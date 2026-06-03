import dk.quack.cbse.common.services.IEntityProcessingService;
import dk.quack.cbse.common.services.IGamePluginService;

module Player {
    requires Common;
    requires CommonBullet;

    uses dk.quack.cbse.common.bullet.BulletSPI;
    provides IGamePluginService with dk.quack.cbse.player.PlayerPlugin;
    provides IEntityProcessingService with dk.quack.cbse.player.PlayerControlSystem;
}
