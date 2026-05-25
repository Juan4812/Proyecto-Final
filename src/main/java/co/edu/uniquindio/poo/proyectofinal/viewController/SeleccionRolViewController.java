package co.edu.uniquindio.poo.proyectofinal.viewController;

import co.edu.uniquindio.poo.proyectofinal.app.App;
import co.edu.uniquindio.poo.proyectofinal.controller.UsuarioController;
import co.edu.uniquindio.poo.proyectofinal.model.Comprador;
import co.edu.uniquindio.poo.proyectofinal.model.Vendedor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SeleccionRolViewController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtIdentificacion;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private Label lblMensaje;

    private final UsuarioController usuarioController = new UsuarioController(App.getInmobiliaria());

    @FXML
    private void ingresarComprador() {
        try {
            Comprador comprador = usuarioController.buscarCompradorPorIdentificacion(txtIdentificacion.getText());

            if (comprador == null) {
                comprador = usuarioController.registrarComprador(
                        txtNombre.getText(),
                        txtIdentificacion.getText(),
                        txtTelefono.getText(),
                        txtCorreo.getText()
                );
            }

            abrirMainComprador(comprador);
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    @FXML
    private void ingresarVendedor() {
        try {
            Vendedor vendedor = usuarioController.buscarVendedorPorIdentificacion(txtIdentificacion.getText());

            if (vendedor == null) {
                vendedor = usuarioController.registrarVendedor(
                        txtNombre.getText(),
                        txtIdentificacion.getText(),
                        txtTelefono.getText(),
                        txtCorreo.getText()
                );
            }

            abrirMainVendedor(vendedor);
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    @FXML
    private void ingresarAdministrador() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/poo/proyectofinal/main-layout-view.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 720);

            MainLayoutViewController controller = loader.getController();
            controller.iniciarComoAdministrador();

            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void abrirMainComprador(Comprador comprador) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/poo/proyectofinal/main-layout-view.fxml"));
        Scene scene = new Scene(loader.load(), 1100, 720);

        MainLayoutViewController controller = loader.getController();
        controller.iniciarComoComprador(comprador);

        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.setScene(scene);
    }

    private void abrirMainVendedor(Vendedor vendedor) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/poo/proyectofinal/main-layout-view.fxml"));
        Scene scene = new Scene(loader.load(), 1100, 720);

        MainLayoutViewController controller = loader.getController();
        controller.iniciarComoVendedor(vendedor);

        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.setScene(scene);
    }

    private void mostrarError(Exception e) {
        lblMensaje.setText(e.getMessage());
    }
}