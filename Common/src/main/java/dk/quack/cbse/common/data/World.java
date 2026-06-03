package dk.quack.cbse.common.data;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class World {
    private final Map<String, Entity> entities = new ConcurrentHashMap<>();

    public void addEntity(Entity entity) {
        entities.put(entity.getId(), entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity.getId());
    }

    public Collection<Entity> getEntities() {
        return entities.values();
    }

    public Collection<Entity> getEntities(EntityType type) {
        return entities.values().stream()
                .filter(entity -> entity.getType() == type)
                .toList();
    }
}
