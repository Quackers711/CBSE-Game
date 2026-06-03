package dk.quack.cbse;

import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class ModuleConfig {
    public ModuleConfig() {

    }

    @Bean
    public Game game() {
        return new Game();
    }
}
