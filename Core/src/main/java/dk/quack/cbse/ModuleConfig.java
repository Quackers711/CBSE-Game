package dk.quack.cbse;

import dk.quack.cbse.common.services.IEntityProcessingService;
import dk.quack.cbse.common.services.IGamePluginService;
import dk.quack.cbse.common.services.IPostEntityProcessingService;
import dk.quack.cbse.common.util.ServiceLocator;
import dk.quack.cbse.scoring.RestScoreClient;
import dk.quack.cbse.scoring.ScoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class ModuleConfig {

    @Bean
    public Game game(
            List<IGamePluginService> gamePluginServices,
            List<IEntityProcessingService> entityProcessingServices,
            List<IPostEntityProcessingService> postEntityProcessingServices,
            ScoreClient scoreClient
    ) {
        return new Game(gamePluginServices, entityProcessingServices, postEntityProcessingServices, scoreClient);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ScoreClient scoreClient(RestTemplate restTemplate) {
        return new RestScoreClient(restTemplate, "http://127.0.0.1:8080");
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
