import dk.quack.cbse.common.services.IEntityProcessingService;
import dk.quack.cbse.common.services.IGamePluginService;

module Enemy {
    requires Common;
    requires CommonBullet;

    uses dk.quack.cbse.common.bullet.BulletSPI;
    provides IGamePluginService with dk.quack.cbse.enemy.EnemyPlugin;
    provides IEntityProcessingService with dk.quack.cbse.enemy.EnemyControlSystem;
}
