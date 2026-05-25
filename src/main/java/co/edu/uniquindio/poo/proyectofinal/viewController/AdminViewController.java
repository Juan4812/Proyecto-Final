package co.edu.uniquindio.poo.proyectofinal.viewController;

import co.edu.uniquindio.poo.proyectofinal.app.App;
import co.edu.uniquindio.poo.proyectofinal.controller.AlertaController;
import co.edu.uniquindio.poo.proyectofinal.controller.InmuebleController;
import co.edu.uniquindio.poo.proyectofinal.controller.OfertaController;
import co.edu.uniquindio.poo.proyectofinal.controller.PublicacionController;
import co.edu.uniquindio.poo.proyectofinal.controller.ReporteController;
import co.edu.uniquindio.poo.proyectofinal.controller.TransaccionController;
import co.edu.uniquindio.poo.proyectofinal.controller.UsuarioController;
import co.edu.uniquindio.poo.proyectofinal.model.Alerta;
import co.edu.uniquindio.poo.proyectofinal.model.Inmueble;
import co.edu.uniquindio.poo.proyectofinal.model.Oferta;
import co.edu.uniquindio.poo.proyectofinal.model.Publicacion;
import co.edu.uniquindio.poo.proyectofinal.model.Transaccion;
import co.edu.uniquindio.poo.proyectofinal.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TabPane;

public class AdminViewController {

    @FXML private TextArea txtReporte;
    @FXML private Label lblResumen;
    @FXML private Label lblMensaje;
    @FXML private TabPane tabPaneAdmin;

    @FXML private ListView<Usuario> listaUsuarios;
    @FXML private ListView<Inmueble> listaInmuebles;
    @FXML private ListView<Publicacion> listaPublicaciones;
    @FXML private ListView<Oferta> listaOfertas;
    @FXML private ListView<Transaccion> listaTransacciones;
    @FXML private ListView<Alerta> listaAlertas;
    @FXML private ListView<Inmueble> listaVendidos;
    @FXML private ListView<Inmueble> listaArrendados;

    private final ReporteController reporteController = new ReporteController(App.getInmobiliaria());
    private final UsuarioController usuarioController = new UsuarioController(App.getInmobiliaria());
    private final InmuebleController inmuebleController = new InmuebleController(App.getInmobiliaria());
    private final PublicacionController publicacionController = new PublicacionController(App.getInmobiliaria());
    private final OfertaController ofertaController = new OfertaController(App.getInmobiliaria());
    private final TransaccionController transaccionController = new TransaccionController(App.getInmobiliaria());
    private final AlertaController alertaController = new AlertaController(App.getInmobiliaria());

    @FXML
    public void cargarDatos() {
        txtReporte.setText(reporteController.generarReporteGeneral());

        listaUsuarios.getItems().setAll(usuarioController.listarUsuarios());
        listaInmuebles.getItems().setAll(inmuebleController.listarInmuebles());
        listaPublicaciones.getItems().setAll(publicacionController.listarPublicaciones());
        listaOfertas.getItems().setAll(ofertaController.listarOfertas());
        listaTransacciones.getItems().setAll(transaccionController.listarTransacciones());
        listaAlertas.getItems().setAll(alertaController.listarAlertas());
        listaVendidos.getItems().setAll(reporteController.obtenerInmueblesVendidos());
        listaArrendados.getItems().setAll(reporteController.obtenerInmueblesArrendados());

        lblResumen.setText(
                "Ciudad con mayor demanda: " + reporteController.obtenerCiudadConMayorDemanda()
                        + " | Comprador mas activo: " + textoObjeto(reporteController.obtenerCompradorMasActivo())
                        + " | Vendedor con mas propiedades: " + textoObjeto(reporteController.obtenerVendedorConMasPropiedades())
        );

        lblMensaje.setText("Datos actualizados");
    }

    public void seleccionarTab(int indice) {
        tabPaneAdmin.getSelectionModel().select(indice);
    }

    private String textoObjeto(Object objeto) {
        return objeto == null ? "Ninguno" : objeto.toString();
    }
}