package dk.quack.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {
    private double x;
    private double y;

    private final UUID ID = UUID.randomUUID();

    public String getId() {
        return ID.toString();
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double[] getPosition() {
        return new double[]{x, y};
    }

}
