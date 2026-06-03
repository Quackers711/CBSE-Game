package dk.quack.cbse;

import dk.quack.cbse.common.services.IEntityProcessingService;
import dk.quack.cbse.common.services.IGamePluginService;
import dk.quack.cbse.common.services.IPostEntityProcessingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.ServiceLoader;

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
        return ServiceLoader.load(serviceType).stream()
                .map(ServiceLoader.Provider::get)
                .toList();
    }
}
