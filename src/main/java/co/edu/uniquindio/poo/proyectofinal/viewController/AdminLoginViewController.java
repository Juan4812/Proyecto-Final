package co.edu.uniquindio.poo.proyectofinal.viewController;

import co.edu.uniquindio.poo.proyectofinal.app.App;
import co.edu.uniquindio.poo.proyectofinal.controller.AdministradorController;
import co.edu.uniquindio.poo.proyectofinal.model.Administrador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminLoginViewController {

    @FXML private TextField txtUsuarioLogin;
    @FXML private PasswordField txtContrasenaLogin;
    @FXML private TextField txtNombreRegistro;
    @FXML private TextField txtUsuarioRegistro;
    @FXML private PasswordField txtContrasenaRegistro;
    @FXML private Label lblMensaje;

    private final AdministradorController administradorController =
            new AdministradorController(App.getInmobiliaria());

    @FXML
    private void iniciarSesion() {
        Administrador administrador = administradorController.autenticarAdministrador(
                txtUsuarioLogin.getText(),
                txtContrasenaLogin.getText()
        );

        if (administrador == null) {
            lblMensaje.setText("Usuario o contraseña incorrecto");
            return;
        }

        abrirMainAdministrador(administrador);
    }

    @FXML
    private void registrarAdministrador() {
        try {
            Administrador administrador = administradorController.registrarAdministrador(
                    txtNombreRegistro.getText(),
                    txtUsuarioRegistro.getText(),
                    txtContrasenaRegistro.getText()
            );

            txtUsuarioLogin.setText(administrador.getUsuario());
            txtContrasenaLogin.clear();
            limpiarRegistro();
            lblMensaje.setText("Administrador registrado. Ingresa la contrasena para iniciar sesion.");
        } catch (Exception e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    @FXML
    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/poo/proyectofinal/seleccion-rol-view.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 720);
            Stage stage = (Stage) lblMensaje.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    private void abrirMainAdministrador(Administrador administrador) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/uniquindio/poo/proyectofinal/main-layout-view.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 720);

            MainLayoutViewController controller = loader.getController();
            controller.iniciarComoAdministrador(administrador);

            Stage stage = (Stage) lblMensaje.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    private void limpiarRegistro() {
        txtNombreRegistro.clear();
        txtUsuarioRegistro.clear();
        txtContrasenaRegistro.clear();
    }
}
