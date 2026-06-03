package dk.quack.cbse;

import dk.quack.cbse.common.data.Entity;
import dk.quack.cbse.common.data.GameData;
import dk.quack.cbse.common.data.GameKeys;
import dk.quack.cbse.common.data.World;
import dk.quack.cbse.common.services.IEntityProcessingService;
import dk.quack.cbse.common.services.IGamePluginService;
import dk.quack.cbse.common.services.IPostEntityProcessingService;
import dk.quack.cbse.scoring.ScoreClient;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Game {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Pane gameWindow = new Pane();
    private final Text scoreText = new Text(10, 20, "Score: 0");
    private final Map<String, Polygon> polygons = new ConcurrentHashMap<>();
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServices;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;
    private final ScoreClient scoreClient;
    private int syncedScore;

    public Game(
            List<IGamePluginService> gamePluginServices,
            List<IEntityProcessingService> entityProcessingServices,
            List<IPostEntityProcessingService> postEntityProcessingServices,
            ScoreClient scoreClient
    ) {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServices = entityProcessingServices;
        this.postEntityProcessingServices = postEntityProcessingServices;
        this.scoreClient = scoreClient;
    }

    public void start(Stage window) {
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        scoreText.setFill(Color.WHITE);
        gameWindow.setStyle("-fx-background-color: #101114;");
        gameWindow.getChildren().add(scoreText);

        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(event -> setKey(event.getCode(), true));
        scene.setOnKeyReleased(event -> setKey(event.getCode(), false));

        for (IGamePluginService pluginService : gamePluginServices) {
            pluginService.start(gameData, world);
        }
        scoreClient.reset();

        window.setScene(scene);
        window.setTitle("CBSE Asteroids");
        window.show();
    }

    public void render() {
        new AnimationTimer() {
            private long lastUpdate;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                gameData.setDeltaTime(Math.min(deltaTime, 1.0 / 30.0));
                lastUpdate = now;
                update();
                draw();
                gameData.getKeys().update();
            }
        }.start();
    }

    private void update() {
        for (IEntityProcessingService processingService : entityProcessingServices) {
            processingService.process(gameData, world);
        }
        for (IPostEntityProcessingService postProcessingService : postEntityProcessingServices) {
            postProcessingService.process(gameData, world);
        }
        syncScore();
    }

    private void syncScore() {
        int currentScore = gameData.getScore();
        int delta = currentScore - syncedScore;
        if (delta > 0) {
            scoreClient.addScore(delta);
            syncedScore = currentScore;
        }
    }

    private void draw() {
        scoreText.setText("Score: " + gameData.getScore());
        polygons.keySet().removeIf(entityId -> {
            boolean removed = world.getEntities().stream().noneMatch(entity -> entity.getId().equals(entityId));
            if (removed) {
                Polygon polygon = polygons.get(entityId);
                gameWindow.getChildren().remove(polygon);
            }
            return removed;
        });

        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.computeIfAbsent(entity.getId(), id -> {
                Polygon created = new Polygon(entity.getPolygonCoordinates());
                created.setStroke(Color.WHITE);
                created.setFill(Color.TRANSPARENT);
                gameWindow.getChildren().add(created);
                return created;
            });
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }
    }

    private void setKey(KeyCode keyCode, boolean pressed) {
        if (keyCode == KeyCode.LEFT || keyCode == KeyCode.A) {
            gameData.getKeys().setKey(GameKeys.LEFT, pressed);
        } else if (keyCode == KeyCode.RIGHT || keyCode == KeyCode.D) {
            gameData.getKeys().setKey(GameKeys.RIGHT, pressed);
        } else if (keyCode == KeyCode.UP || keyCode == KeyCode.W) {
            gameData.getKeys().setKey(GameKeys.UP, pressed);
        } else if (keyCode == KeyCode.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, pressed);
        }
    }
}
