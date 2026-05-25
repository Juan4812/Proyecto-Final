package co.edu.uniquindio.poo.proyectofinal.viewController;

import co.edu.uniquindio.poo.proyectofinal.app.App;
import co.edu.uniquindio.poo.proyectofinal.controller.*;
import co.edu.uniquindio.poo.proyectofinal.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class VendedorViewController {

    @FXML private TabPane tabPaneVendedor;

    @FXML private Label lblPerfil;
    @FXML private Label lblMensaje;
    @FXML private Label lblInmuebleSeleccionado;

    @FXML private TextField txtCodigo;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtCiudad;
    @FXML private TextField txtArea;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtDescripcion;

    @FXML private ComboBox<TipoInmueble> cbTipoInmueble;
    @FXML private ComboBox<TipoOperacion> cbTipoOperacion;
    @FXML private ComboBox<String> cbAccionInmueble;

    @FXML private ListView<Inmueble> listaInmuebles;
    @FXML private ListView<Publicacion> listaPublicaciones;
    @FXML private ListView<Oferta> listaOfertas;
    @FXML private ListView<Alerta> listaAlertas;
    @FXML private TextArea txtDetalle;

    private Vendedor vendedorActual;
    private Inmueble inmuebleSeleccionado;

    private final InmuebleController inmuebleController = new InmuebleController(App.getInmobiliaria());
    private final PublicacionController publicacionController = new PublicacionController(App.getInmobiliaria());
    private final OfertaController ofertaController = new OfertaController(App.getInmobiliaria());
    private final AlertaController alertaController = new AlertaController(App.getInmobiliaria());

    @FXML
    private void initialize() {
        cbTipoInmueble.getItems().setAll(TipoInmueble.values());
        cbTipoOperacion.getItems().setAll(TipoOperacion.values());
        cbAccionInmueble.getItems().setAll("Publicar", "Ver detalle", "Ver ofertas");

        listaInmuebles.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, inmueble) -> {
            if (inmueble != null) {
                inmuebleSeleccionado = inmueble;
                mostrarDetalleInmueble(inmueble);
                actualizarInmuebleSeleccionado();
                cargarOfertasDelInmueble(inmueble);
            }
        });

        listaPublicaciones.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, publicacion) -> {
            if (publicacion != null) {
                inmuebleSeleccionado = publicacion.getInmueble();
                mostrarDetallePublicacion(publicacion);
                actualizarInmuebleSeleccionado();
                cargarOfertasDelInmueble(publicacion.getInmueble());
            }
        });
    }

    public void setVendedorActual(Vendedor vendedorActual) {
        this.vendedorActual = vendedorActual;
        actualizarPerfil();
        cargarMisInmuebles();
        cargarMisPublicaciones();
        cargarMisAlertas();
        actualizarInmuebleSeleccionado();
    }

    public void seleccionarTab(int indice) {
        tabPaneVendedor.getSelectionModel().select(indice);
    }

    @FXML
    private void registrarInmueble() {
        try {
            inmuebleController.registrarInmueble(
                    Integer.parseInt(txtCodigo.getText()),
                    txtDireccion.getText(),
                    txtCiudad.getText(),
                    Double.parseDouble(txtArea.getText()),
                    Double.parseDouble(txtPrecio.getText()),
                    cbTipoInmueble.getValue(),
                    vendedorActual
            );

            cargarMisInmuebles();
            limpiarFormularioRegistro();
            lblMensaje.setText("Inmueble registrado");
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    @FXML
    private void ejecutarAccionInmueble() {
        Inmueble inmueble = listaInmuebles.getSelectionModel().getSelectedItem();

        if (inmueble == null) {
            lblMensaje.setText("Selecciona un inmueble");
            return;
        }

        if (cbAccionInmueble.getValue() == null) {
            lblMensaje.setText("Selecciona una accion");
            return;
        }

        inmuebleSeleccionado = inmueble;
        mostrarDetalleInmueble(inmueble);
        actualizarInmuebleSeleccionado();

        if ("Publicar".equals(cbAccionInmueble.getValue())) {
            tabPaneVendedor.getSelectionModel().select(1);
            lblMensaje.setText("Completa la descripcion y la operacion para publicar el inmueble seleccionado");
        } else if ("Ver detalle".equals(cbAccionInmueble.getValue())) {
            tabPaneVendedor.getSelectionModel().select(4);
        } else if ("Ver ofertas".equals(cbAccionInmueble.getValue())) {
            cargarOfertasDelInmueble(inmueble);
            tabPaneVendedor.getSelectionModel().select(2);
        }
    }

    @FXML
    private void publicarInmueble() {
        try {
            if (inmuebleSeleccionado == null) {
                lblMensaje.setText("Selecciona un inmueble desde Mis inmuebles");
                return;
            }

            publicacionController.publicarInmueble(
                    vendedorActual,
                    inmuebleSeleccionado,
                    txtDescripcion.getText(),
                    cbTipoOperacion.getValue()
            );

            txtDescripcion.clear();
            actualizarPerfil();
            cargarMisPublicaciones();
            cargarMisAlertas();
            lblMensaje.setText("Inmueble publicado");
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    @FXML
    private void cambiarPrecio() {
        try {
            if (inmuebleSeleccionado == null) {
                lblMensaje.setText("Selecciona un inmueble");
                return;
            }

            inmuebleController.cambiarPrecioInmueble(inmuebleSeleccionado, Double.parseDouble(txtPrecio.getText()));
            cargarMisInmuebles();
            cargarMisPublicaciones();
            cargarMisAlertas();
            mostrarDetalleInmueble(inmuebleSeleccionado);
            lblMensaje.setText("Precio actualizado");
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    @FXML
    private void aceptarOferta() {
        try {
            Oferta oferta = listaOfertas.getSelectionModel().getSelectedItem();

            if (oferta == null) {
                lblMensaje.setText("Selecciona una oferta");
                return;
            }

            if (cbTipoOperacion.getValue() == null) {
                lblMensaje.setText("Selecciona si la transaccion sera VENTA o ARRIENDO");
                return;
            }

            ofertaController.aceptarOferta(oferta, cbTipoOperacion.getValue());
            actualizarPerfil();
            cargarMisInmuebles();
            cargarMisPublicaciones();
            cargarOfertasRecibidas();
            cargarMisAlertas();
            lblMensaje.setText("Oferta aceptada y transaccion registrada");
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    @FXML
    private void rechazarOferta() {
        try {
            Oferta oferta = listaOfertas.getSelectionModel().getSelectedItem();

            if (oferta == null) {
                lblMensaje.setText("Selecciona una oferta");
                return;
            }

            ofertaController.rechazarOferta(oferta);
            cargarOfertasRecibidas();
            cargarMisAlertas();
            lblMensaje.setText("Oferta rechazada");
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    @FXML
    private void cargarMisInmuebles() {
        if (vendedorActual != null) {
            listaInmuebles.getItems().setAll(inmuebleController.listarInmueblesPorVendedor(vendedorActual));
        }
    }

    @FXML
    private void cargarMisPublicaciones() {
        listaPublicaciones.getItems().clear();

        if (vendedorActual == null) {
            return;
        }

        for (Publicacion publicacion : publicacionController.listarPublicaciones()) {
            if (publicacion.getInmueble().getVendedor() == vendedorActual) {
                listaPublicaciones.getItems().add(publicacion);
            }
        }
    }

    @FXML
    private void cargarOfertasRecibidas() {
        listaOfertas.getItems().clear();

        if (vendedorActual == null) {
            return;
        }

        for (Inmueble inmueble : inmuebleController.listarInmueblesPorVendedor(vendedorActual)) {
            listaOfertas.getItems().addAll(ofertaController.listarOfertasPorInmueble(inmueble));
        }

        lblMensaje.setText("Ofertas cargadas");
    }

    @FXML
    private void cargarMisAlertas() {
        if (vendedorActual != null) {
            listaAlertas.getItems().setAll(alertaController.listarAlertasPorUsuario(vendedorActual));
        }
    }

    @FXML
    private void limpiarFormulario() {
        limpiarFormularioRegistro();
        txtDescripcion.clear();
        cbTipoOperacion.setValue(null);
        cbAccionInmueble.setValue(null);
        txtDetalle.clear();
        inmuebleSeleccionado = null;
        actualizarInmuebleSeleccionado();
        lblMensaje.setText("Formulario limpio");
    }

    private void limpiarFormularioRegistro() {
        txtCodigo.clear();
        txtDireccion.clear();
        txtCiudad.clear();
        txtArea.clear();
        txtPrecio.clear();
        cbTipoInmueble.setValue(null);
    }

    private void cargarOfertasDelInmueble(Inmueble inmueble) {
        listaOfertas.getItems().setAll(ofertaController.listarOfertasPorInmueble(inmueble));
    }

    private void actualizarPerfil() {
        lblPerfil.setText(
                vendedorActual.getNombre()
                        + " | Puntos: " + vendedorActual.getPuntosReputacion()
                        + " | Clasificacion: " + vendedorActual.getClasificacion()
                        + " | Beneficio: " + vendedorActual.calcularBeneficio()
        );
    }

    private void actualizarInmuebleSeleccionado() {
        if (lblInmuebleSeleccionado == null) {
            return;
        }

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

    private void mostrarError(Exception e) {
        lblMensaje.setText(e.getMessage());
    }
}
