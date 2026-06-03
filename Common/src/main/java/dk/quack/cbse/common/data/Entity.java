package dk.quack.cbse.common.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

public class Entity implements Serializable {
    private double x;
    private double y;
    private double dx;
    private double dy;
    private double rotation;
    private double radius = 8;
    private double[] polygonCoordinates = new double[0];
    private EntityType type = EntityType.UNKNOWN;
    private String owner = "";
    private int hitPoints = 1;
    private long createdAt = System.currentTimeMillis();
    private final UUID id = UUID.randomUUID();

    public String getId() {
        return id.toString();
    }

    public UUID getID() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPosition(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double[] getPolygonCoordinates() {
        return Arrays.copyOf(polygonCoordinates, polygonCoordinates.length);
    }

    public void setPolygonCoordinates(double... polygonCoordinates) {
        this.polygonCoordinates = Arrays.copyOf(polygonCoordinates, polygonCoordinates.length);
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void damage(int amount) {
        hitPoints -= amount;
    }

    public boolean isDead() {
        return hitPoints <= 0;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
