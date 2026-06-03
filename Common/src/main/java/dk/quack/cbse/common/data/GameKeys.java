package dk.quack.cbse.common.data;

import java.util.HashMap;
import java.util.Map;

public class GameKeys {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int SPACE = 3;

    private final Map<Integer, Boolean> keys = new HashMap<>();
    private final Map<Integer, Boolean> previousKeys = new HashMap<>();

    public void setKey(int key, boolean pressed) {
        keys.put(key, pressed);
    }

    public boolean isDown(int key) {
        return keys.getOrDefault(key, false);
    }

    public boolean isPressed(int key) {
        return isDown(key) && !previousKeys.getOrDefault(key, false);
    }

    public void update() {
        previousKeys.clear();
        previousKeys.putAll(keys);
    }
}
