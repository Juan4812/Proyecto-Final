package co.edu.uniquindio.poo.proyectofinal.viewController;

import co.edu.uniquindio.poo.proyectofinal.app.App;
import co.edu.uniquindio.poo.proyectofinal.controller.*;
import co.edu.uniquindio.poo.proyectofinal.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CompradorViewController {

    @FXML private TabPane tabPaneComprador;
    @FXML private Label lblPerfil;
    @FXML private Label lblMensaje;
    @FXML private Label lblInmuebleSeleccionado;

    @FXML private TextField txtCiudad;
    @FXML private TextField txtPrecioMinimo;
    @FXML private TextField txtPrecioMaximo;
    @FXML private TextField txtAreaMinima;
    @FXML private TextField txtValorOferta;

    @FXML private ComboBox<TipoInmueble> cbTipoInmueble;
    @FXML private ComboBox<TipoOperacion> cbTipoOperacion;
    @FXML private ComboBox<String> cbAccionPublicacion;
    @FXML private ComboBox<String> cbAccionBusqueda;

    @FXML private ListView<Publicacion> listaPublicaciones;
    @FXML private ListView<Inmueble> listaInmuebles;
    @FXML private ListView<Oferta> listaOfertas;
    @FXML private ListView<Alerta> listaAlertas;
    @FXML private TextArea txtDetalle;

    private Comprador compradorActual;
    private Inmueble inmuebleSeleccionado;

    private final BusquedaController busquedaController = new BusquedaController(App.getInmobiliaria());
    private final OfertaController ofertaController = new OfertaController(App.getInmobiliaria());
    private final PublicacionController publicacionController = new PublicacionController(App.getInmobiliaria());
    private final RecomendacionController recomendacionController = new RecomendacionController(App.getInmobiliaria());
    private final AlertaController alertaController = new AlertaController(App.getInmobiliaria());

    @FXML
    private void initialize() {
        cbTipoInmueble.getItems().setAll(TipoInmueble.values());
        cbTipoOperacion.getItems().setAll(TipoOperacion.values());
        cbAccionPublicacion.getItems().setAll("Ofertar", "Ver detalle");
        cbAccionBusqueda.getItems().setAll("Ofertar", "Ver detalle");

        listaPublicaciones.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, publicacion) -> {
            if (publicacion != null) {
                inmuebleSeleccionado = publicacion.getInmueble();
                mostrarDetallePublicacion(publicacion);
                actualizarInmuebleSeleccionado();
            }
        });

        listaInmuebles.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, inmueble) -> {
            if (inmueble != null) {
                inmuebleSeleccionado = inmueble;
                mostrarDetalleInmueble(inmueble);
                actualizarInmuebleSeleccionado();
            }
        });

        tabPaneComprador.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null && "Alertas".equals(newTab.getText())) {
                cargarMisAlertas();
            }
        });
    }

    public void setCompradorActual(Comprador compradorActual) {
        this.compradorActual = compradorActual;
        actualizarPerfil();
        cargarPublicaciones();
        cargarMisOfertas();
        cargarMisAlertas();
    }

    @FXML
    private void cargarPublicaciones() {
        listaPublicaciones.getItems().clear();

        for (Publicacion publicacion : publicacionController.listarPublicaciones()) {
            if (publicacion.getInmueble().estaDisponible()) {
                listaPublicaciones.getItems().add(publicacion);
            }
        }

        lblMensaje.setText("Publicaciones disponibles cargadas");
    }

    @FXML
    private void ejecutarAccionPublicacion() {
        Publicacion publicacion = listaPublicaciones.getSelectionModel().getSelectedItem();

        if (publicacion == null) {
            lblMensaje.setText("Selecciona una publicacion");
            return;
        }

        inmuebleSeleccionado = publicacion.getInmueble();
        mostrarDetallePublicacion(publicacion);
        actualizarInmuebleSeleccionado();

        if ("Ofertar".equals(cbAccionPublicacion.getValue())) {
            tabPaneComprador.getSelectionModel().select(2);
            lblMensaje.setText("Escribe el valor de la oferta para el inmueble seleccionado");
        } else if ("Ver detalle".equals(cbAccionPublicacion.getValue())) {
            tabPaneComprador.getSelectionModel().select(4);
        } else {
            lblMensaje.setText("Selecciona una accion");
        }
    }

    @FXML
    private void buscarInmuebles() {
        try {
            listaInmuebles.getItems().setAll(busquedaController.buscarInmuebles(
                    compradorActual,
                    txtCiudad.getText(),
                    cbTipoInmueble.getValue(),
                    leerDouble(txtPrecioMinimo.getText()),
                    leerDouble(txtPrecioMaximo.getText()),
                    leerDouble(txtAreaMinima.getText()),
                    cbTipoOperacion.getValue()
            ));

            lblMensaje.setText("Busqueda realizada");
            cargarMisAlertas();
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    @FXML
    private void cargarRecomendaciones() {
        try {
            listaInmuebles.getItems().setAll(recomendacionController.recomendarInmuebles(compradorActual));
            lblMensaje.setText("Recomendaciones cargadas");
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    @FXML
    private void ejecutarAccionBusqueda() {
        Inmueble inmueble = listaInmuebles.getSelectionModel().getSelectedItem();

        if (inmueble == null) {
            lblMensaje.setText("Selecciona un inmueble");
            return;
        }

        inmuebleSeleccionado = inmueble;
        mostrarDetalleInmueble(inmueble);
        actualizarInmuebleSeleccionado();

        if ("Ofertar".equals(cbAccionBusqueda.getValue())) {
            tabPaneComprador.getSelectionModel().select(2);
            lblMensaje.setText("Escribe el valor de la oferta para el inmueble seleccionado");
        } else if ("Ver detalle".equals(cbAccionBusqueda.getValue())) {
            tabPaneComprador.getSelectionModel().select(4);
        } else {
            lblMensaje.setText("Selecciona una accion");
        }
    }

    @FXML
    private void hacerOferta() {
        try {
            if (inmuebleSeleccionado == null) {
                lblMensaje.setText("Primero selecciona una publicacion o inmueble");
                return;
            }

            ofertaController.realizarOferta(compradorActual, inmuebleSeleccionado, leerDouble(txtValorOferta.getText()));
            txtValorOferta.clear();
            actualizarPerfil();
            cargarMisOfertas();
            cargarMisAlertas();
            lblMensaje.setText("Oferta registrada");
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    @FXML
    private void cargarMisOfertas() {
        if (compradorActual != null) {
            listaOfertas.getItems().setAll(ofertaController.listarOfertasPorComprador(compradorActual));
        }
    }

    @FXML
    private void cargarMisAlertas() {
        if (compradorActual != null) {
            listaAlertas.getItems().setAll(alertaController.listarAlertasPorUsuario(compradorActual));
        }
    }

    @FXML
    private void limpiarFiltros() {
        txtCiudad.clear();
        txtPrecioMinimo.clear();
        txtPrecioMaximo.clear();
        txtAreaMinima.clear();
        cbTipoInmueble.setValue(null);
        cbTipoOperacion.setValue(null);
        cbAccionBusqueda.setValue(null);
        listaInmuebles.getItems().clear();
        lblMensaje.setText("Filtros limpiados");
    }

    private void actualizarPerfil() {
        lblPerfil.setText(
                compradorActual.getNombre()
                        + " | Puntos: " + compradorActual.getPuntosReputacion()
                        + " | Clasificacion: " + compradorActual.getClasificacion()
                        + " | Beneficio: " + compradorActual.calcularBeneficio()
        );
    }

    private void actualizarInmuebleSeleccionado() {
        if (inmuebleSeleccionado == null) {
            lblInmuebleSeleccionado.setText("Ningun inmueble seleccionado");
        } else {
            lblInmuebleSeleccionado.setText("Seleccionado: " + inmuebleSeleccionado);
        }
    }

    private void mostrarDetallePublicacion(Publicacion publicacion) {
        txtDetalle.setText(
                "Publicacion: " + publicacion.getCodigoPublicacion() + "\n"
                        + "Fecha: " + publicacion.getFechaPublicacion() + "\n"
                        + "Operacion: " + publicacion.getTipoOperacion() + "\n"
                        + "Descripcion: " + publicacion.getDescripcion() + "\n\n"
                        + crearDetalleInmueble(publicacion.getInmueble())
        );
    }

    private void mostrarDetalleInmueble(Inmueble inmueble) {
        txtDetalle.setText(crearDetalleInmueble(inmueble));
    }

    private String crearDetalleInmueble(Inmueble inmueble) {
        return "Codigo: " + inmueble.getCodigo() + "\n"
                + "Tipo: " + inmueble.getTipo() + "\n"
                + "Ciudad: " + inmueble.getCiudad() + "\n"
                + "Direccion: " + inmueble.getDireccion() + "\n"
                + "Area: " + inmueble.getArea() + "\n"
                + "Precio: $" + inmueble.getPrecio() + "\n"
                + "Estado: " + inmueble.getEstado() + "\n"
                + "Vendedor: " + inmueble.getVendedor().getNombre();
    }

    private double leerDouble(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return 0;
        }
        return Double.parseDouble(texto);
    }

    private void mostrarError(Exception e) {
        lblMensaje.setText(e.getMessage());
    }
}
