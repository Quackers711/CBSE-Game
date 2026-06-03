package dk.quack.cbse;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game {

    private final Pane gameWindow = new Pane();

    public void start(Stage window) {
        gameWindow.setPrefSize(800, 600);
        Scene scene = new Scene(gameWindow);
        window.setScene(scene);
        window.show();
    }

    public void render() {}

}
