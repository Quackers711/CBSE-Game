package dk.quack.cbse.common.util;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public enum ServiceLocator {

    INSTANCE;

    private static final Map<Class<?>, List<?>> serviceCache = new LinkedHashMap<>();
    private final ModuleLayer layer;

    ServiceLocator() {
        Path pluginsDir = Paths.get("plugins");
        if (!Files.isDirectory(pluginsDir)) {
            layer = null;
            return;
        }

        try {
            ModuleFinder pluginsFinder = ModuleFinder.of(pluginsDir);
            List<String> plugins = pluginsFinder
                    .findAll()
                    .stream()
                    .map(ModuleReference::descriptor)
                    .map(ModuleDescriptor::name)
                    .collect(Collectors.toList());

            if (plugins.isEmpty()) {
                layer = null;
                return;
            }

            Configuration pluginsConfiguration = ModuleLayer
                    .boot()
                    .configuration()
                    .resolve(pluginsFinder, ModuleFinder.of(Paths.get("mods-mvn")), plugins);

            layer = ModuleLayer
                    .boot()
                    .defineModulesWithOneLoader(pluginsConfiguration, ClassLoader.getSystemClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> locateAll(Class<T> service) {
        List<T> cachedServices = (List<T>) serviceCache.get(service);
        if (cachedServices != null) {
            return cachedServices;
        }

        Map<String, T> services = new LinkedHashMap<>();
        addServices(services, ServiceLoader.load(service));
        if (layer != null) {
            addServices(services, ServiceLoader.load(layer, service));
        }

        List<T> locatedServices = List.copyOf(services.values());
        serviceCache.put(service, locatedServices);
        return locatedServices;
    }

    private static <T> void addServices(Map<String, T> services, ServiceLoader<T> loader) {
        try {
            for (T instance : loader) {
                services.putIfAbsent(instance.getClass().getName(), instance);
            }
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();
        }
    }
}
