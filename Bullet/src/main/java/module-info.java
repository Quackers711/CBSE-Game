import dk.quack.cbse.common.bullet.BulletSPI;
import dk.quack.cbse.common.services.IEntityProcessingService;
import dk.quack.cbse.common.services.IGamePluginService;

module Bullet {
    requires Common;
    requires CommonBullet;

    provides IGamePluginService with dk.quack.cbse.bullet.BulletPlugin;
    provides IEntityProcessingService with dk.quack.cbse.bullet.BulletControlSystem;
    provides BulletSPI with dk.quack.cbse.bullet.BulletControlSystem;
}
