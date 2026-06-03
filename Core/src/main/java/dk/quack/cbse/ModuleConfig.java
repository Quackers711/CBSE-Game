package dk.quack.cbse;

import dk.quack.cbse.common.services.IEntityProcessingService;
import dk.quack.cbse.common.services.IGamePluginService;
import dk.quack.cbse.common.services.IPostEntityProcessingService;
import dk.quack.cbse.common.util.ServiceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModuleConfig {

    @Bean
    public Game game(
            List<IGamePluginService> gamePluginServices,
            List<IEntityProcessingService> entityProcessingServices,
            List<IPostEntityProcessingService> postEntityProcessingServices
    ) {
        return new Game(gamePluginServices, entityProcessingServices, postEntityProcessingServices);
    }

    @Bean
    public List<IGamePluginService> gamePluginServices() {
        return loadAll(IGamePluginService.class);
    }

    @Bean
    public List<IEntityProcessingService> entityProcessingServices() {
        return loadAll(IEntityProcessingService.class);
    }

    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices() {
        return loadAll(IPostEntityProcessingService.class);
    }

    private static <T> List<T> loadAll(Class<T> serviceType) {
        return ServiceLocator.INSTANCE.locateAll(serviceType);
    }
}
