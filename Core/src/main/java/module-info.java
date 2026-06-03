module Core {
    requires javafx.controls;
    requires spring.context;
    requires spring.web;
    requires micrometer.observation;
    requires Common;

    uses dk.quack.cbse.common.services.IGamePluginService;
    uses dk.quack.cbse.common.services.IEntityProcessingService;
    uses dk.quack.cbse.common.services.IPostEntityProcessingService;

    exports dk.quack.cbse;
    opens dk.quack.cbse to spring.core, spring.beans, spring.context;
}
