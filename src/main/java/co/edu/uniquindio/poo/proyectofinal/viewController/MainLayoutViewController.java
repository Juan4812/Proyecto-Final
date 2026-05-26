package co.edu.uniquindio.poo.proyectofinal.viewController;

import co.edu.uniquindio.poo.proyectofinal.app.App;
import co.edu.uniquindio.poo.proyectofinal.model.Administrador;
import co.edu.uniquindio.poo.proyectofinal.model.Comprador;
import co.edu.uniquindio.poo.proyectofinal.model.Vendedor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainLayoutViewController {

    @FXML private Label lblUsuario;
    @FXML private StackPane contenedorPrincipal;

    private Comprador compradorActual;
    private Vendedor vendedorActual;
    private Administrador administradorActual;

    public void iniciarComoComprador(Comprador comprador) {
        compradorActual = comprador;
        vendedorActual = null;
        administradorActual = null;
        lblUsuario.setText(comprador.getNombre() + " | Comprador | " + comprador.getClasificacion());
        cargarVistaComprador();
    }

    public void iniciarComoVendedor(Vendedor vendedor) {
        vendedorActual = vendedor;
        compradorActual = null;
        administradorActual = null;
        lblUsuario.setText(vendedor.getNombre() + " | Vendedor | " + vendedor.getClasificacion());
        cargarVistaVendedor();
    }

    public void iniciarComoAdministrador(Administrador administrador) {
        compradorActual = null;
        vendedorActual = null;
        administradorActual = administrador;
        lblUsuario.setText(administrador.getNombre() + " | Administrador");
        cargarVistaAdministrador();
    }

    private void cargarVistaComprador() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/poo/proyectofinal/comprador-view.fxml"));
            Node vista = loader.load();
            CompradorViewController controller = loader.getController();
            controller.setCompradorActual(compradorActual);
            contenedorPrincipal.getChildren().setAll(vista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarVistaVendedor() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/poo/proyectofinal/vendedor-view.fxml"));
            Node vista = loader.load();
            VendedorViewController controller = loader.getController();
            controller.setVendedorActual(vendedorActual);
            contenedorPrincipal.getChildren().setAll(vista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarVistaAdministrador() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/poo/proyectofinal/admin-view.fxml"));
            Node vista = loader.load();
            AdminViewController controller = loader.getController();
            controller.setAdministradorActual(administradorActual);
            controller.cargarDatos();
            contenedorPrincipal.getChildren().setAll(vista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/poo/proyectofinal/seleccion-rol-view.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 720);
            Stage stage = (Stage) contenedorPrincipal.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}