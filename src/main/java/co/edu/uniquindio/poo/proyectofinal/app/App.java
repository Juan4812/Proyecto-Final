package co.edu.uniquindio.poo.proyectofinal.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Label titulo = new Label("Inmobiliaria InmoSmart");

        VBox root = new VBox(titulo);
        root.setSpacing(20);
        root.setStyle("-fx-padding: 30; -fx-alignment: center;");

        Scene scene = new Scene(root, 800, 500);

        stage.setTitle("Inmobiliaria");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}