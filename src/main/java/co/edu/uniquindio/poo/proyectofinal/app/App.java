package co.edu.uniquindio.poo.proyectofinal.app;

import co.edu.uniquindio.poo.proyectofinal.model.Inmobiliaria;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static final Inmobiliaria inmobiliaria = new Inmobiliaria("Inmobiliaria", "123");

    public static Inmobiliaria getInmobiliaria() {
        return inmobiliaria;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/poo/proyectofinal/seleccion-rol-view.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 650);
        stage.setTitle("Inmobiliaria");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}