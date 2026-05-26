package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;
import java.time.LocalDate;

public class Inmobiliaria implements IOperacionesInmobiliarias {

    private static final int PUNTOS_PUBLICAR_INMUEBLE = 10;
    private static final int PUNTOS_REALIZAR_OFERTA = 5;
    private static final int PUNTOS_COMPRAR_INMUEBLE = 50;
    private static final int PUNTOS_COMPLETAR_TRANSACCION = 100;

    //Atributos
    private String nombre, nit;
    private int siguienteIdUsuario;

    //relaciones
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Inmueble> listaInmuebles;
    private ArrayList<Publicacion> listaPublicaciones;
    private ArrayList<Oferta> listaOfertas;
    private ArrayList<Transaccion> listaTransacciones;
    private ArrayList<Alerta> listaAlertas;
    private ArrayList<HistorialBusqueda> listaHistorialBusquedas;
    private ArrayList<Administrador> listaAdministradores;


    //Constructor
    public Inmobiliaria(String nombre, String nit) {
        this.nombre=nombre;
        this.nit=nit;
        this.siguienteIdUsuario = 1;
        listaUsuarios = new ArrayList<>();
        listaInmuebles = new ArrayList<>();
        listaPublicaciones = new ArrayList<>();
        listaOfertas = new ArrayList<>();
        listaTransacciones = new ArrayList<>();
        listaAlertas = new ArrayList<>();
        listaHistorialBusquedas = new ArrayList<>();
        listaAdministradores = new ArrayList<>();
        registrarAdministrador("Administrador principal", "admin", "admin");

    }

    //Metodossssss

    //Get and Set
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }


    public ArrayList<Usuario> getListaUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }

    public ArrayList<Comprador> getListaCompradores() {
        ArrayList<Comprador> listaCompradores = new ArrayList<>();

        for (Usuario usuario : listaUsuarios) {
            if (usuario instanceof Comprador comprador) {
                listaCompradores.add(comprador);
            }
        }

        return listaCompradores;
    }

    public ArrayList<Vendedor> getListaVendedores() {
        ArrayList<Vendedor> listaVendedores = new ArrayList<>();

        for (Usuario usuario : listaUsuarios) {
            if (usuario instanceof Vendedor vendedor) {
                listaVendedores.add(vendedor);
            }
        }

        return listaVendedores;
    }

    public ArrayList<Inmueble> getListaInmuebles() {
        return new ArrayList<>(listaInmuebles);
    }

    public ArrayList<Publicacion> getListaPublicaciones() {
        return new ArrayList<>(listaPublicaciones);
    }

    public ArrayList<Oferta> getListaOfertas() {
        return new ArrayList<>(listaOfertas);
    }

    public ArrayList<Transaccion> getListaTransacciones() {
        return new ArrayList<>(listaTransacciones);
    }

    public ArrayList<Alerta> getListaAlertas() {
        return new ArrayList<>(listaAlertas);
    }

    public ArrayList<HistorialBusqueda> getListaHistorialBusquedas() {
        return new ArrayList<>(listaHistorialBusquedas);
    }

    public ArrayList<Administrador> getListaAdministradores() {
        return new ArrayList<>(listaAdministradores);
    }

    //Metodo para registrar un comprador
    public Comprador registrarComprador(String nombre, String identificacion, String telefono, String correo) {
        validarUsuarioNoRegistrado(identificacion);
        Comprador comprador = new Comprador(generarIdUsuario(), nombre, identificacion, telefono, correo);
        listaUsuarios.add(comprador);
        return comprador;
    }

    //Metodo para registrar un vendedor
    public Vendedor registrarVendedor(String nombre, String identificacion, String telefono, String correo) {
        validarUsuarioNoRegistrado(identificacion);
        Vendedor vendedor = new Vendedor(generarIdUsuario(), nombre, identificacion, telefono, correo);
        listaUsuarios.add(vendedor);
        return vendedor;
    }

    public Administrador registrarAdministrador(String nombre, String usuario, String contrasena) {
        validarAdministradorNoRegistrado(usuario);
        Administrador administrador = new Administrador(nombre, usuario, contrasena);
        listaAdministradores.add(administrador);
        return administrador;
    }

    public Administrador autenticarAdministrador(String usuario, String contrasena) {
        for (Administrador administrador : listaAdministradores) {
            if (administrador.tieneCredenciales(usuario, contrasena)) {
                return administrador;
            }
        }
        return null;
    }

    public void cambiarContrasenaAdministrador(Administrador administrador, String nuevaContrasena, String confirmacionContrasena) {
        if (!listaAdministradores.contains(administrador)) {
            throw new IllegalArgumentException("El administrador no esta registrado");
        }
        administrador.cambiarContrasena(nuevaContrasena, confirmacionContrasena);
    }

    //Metodo para registrar un inmueble
    public Inmueble registrarInmueble(int codigo, String direccion, String ciudad, double area,
                                      double precio, TipoInmueble tipo, Vendedor vendedor) {
        if (!esVendedorRegistrado(vendedor)) throw new IllegalArgumentException("El vendedor no esta registrado");
        if (buscarInmueblePorCodigo(codigo) != null) throw new IllegalArgumentException("Ya existe un inmueble con ese codigo");
        if (existeInmuebleConMismosDatos(direccion, ciudad, area, precio, tipo)) {
            throw new IllegalArgumentException("Ya existe un inmueble con los mismos datos");
        }

        Inmueble inmueble = new Inmueble(codigo, direccion, ciudad, area, precio, tipo, vendedor);
        listaInmuebles.add(inmueble);
        return inmueble;
    }

    //Metodo para publicar un inmueble
    @Override
    public Publicacion publicarInmueble(Vendedor vendedor, Inmueble inmueble, String descripcion, TipoOperacion tipoOperacion) {
        if (!esVendedorRegistrado(vendedor)) throw new IllegalArgumentException("El vendedor no esta registrado");
        if (!listaInmuebles.contains(inmueble)) throw new IllegalArgumentException("El inmueble no esta registrado");
        if (inmueble.getVendedor() != vendedor) throw new IllegalArgumentException("El inmueble no pertenece al vendedor");
        if (!inmueble.estaDisponible()) throw new IllegalArgumentException("El inmueble no esta disponible");
        if (buscarPublicacionPorInmueble(inmueble) != null) throw new IllegalArgumentException("El inmueble ya esta publicado");

        Publicacion publicacion = new Publicacion(generarCodigoPublicacion(), descripcion, inmueble, tipoOperacion);
        listaPublicaciones.add(publicacion);
        vendedor.sumarReputacion(PUNTOS_PUBLICAR_INMUEBLE);

        notificarCompradoresPorInmuebleSimilar(publicacion);

        return publicacion;
    }

    //Metodo para buscar un inmueble
    @Override
    public ArrayList<Inmueble> buscarInmuebles(FiltroBusqueda filtro) {
        if (filtro == null) throw new IllegalArgumentException("El filtro de busqueda es obligatorio");

        ArrayList<Inmueble> listaInmueblesEncontrados = new ArrayList<>();

        for (Inmueble inmueble : listaInmuebles) {
            if (cumpleFiltroBusqueda(inmueble, filtro)) {
                listaInmueblesEncontrados.add(inmueble);
            }
        }

        return listaInmueblesEncontrados;
    }

    public ArrayList<Inmueble> buscarInmuebles(Comprador comprador, FiltroBusqueda filtro) {
        if (!esCompradorRegistrado(comprador)) throw new IllegalArgumentException("El comprador no esta registrado");

        listaHistorialBusquedas.add(new HistorialBusqueda(comprador, filtro, LocalDate.now()));
        return buscarInmuebles(filtro);
    }


    private void notificarCompradoresPorInmuebleSimilar(Publicacion publicacionNueva) {
        ArrayList<Comprador> listaCompradoresNotificados = new ArrayList<>();

        for (HistorialBusqueda historialBusqueda : listaHistorialBusquedas) {
            Comprador comprador = historialBusqueda.comprador();

            if (!listaCompradoresNotificados.contains(comprador)
                    && cumpleFiltroBusqueda(publicacionNueva.getInmueble(), historialBusqueda.filtroBusqueda())) {

                crearAlerta(
                        comprador,
                        "Aparecio un inmueble similar a tu busqueda: "
                                + publicacionNueva.getInmueble().getTipo()
                                + " en "
                                + publicacionNueva.getInmueble().getCiudad(),
                        TipoAlerta.CORREO
                );

                listaCompradoresNotificados.add(comprador);
            }
        }
    }

    private boolean cumpleFiltroBusqueda(Inmueble inmueble, FiltroBusqueda filtro) {
        if (!inmueble.estaDisponible()) return false;

        if (filtro.ciudad() != null && !filtro.ciudad().trim().isEmpty()
                && !inmueble.getCiudad().equalsIgnoreCase(filtro.ciudad())) {
            return false;
        }

        if (filtro.tipoInmueble() != null && inmueble.getTipo() != filtro.tipoInmueble()) {
            return false;
        }

        if (filtro.precioMinimo() > 0 && inmueble.getPrecio() < filtro.precioMinimo()) {
            return false;
        }

        if (filtro.precioMaximo() > 0 && inmueble.getPrecio() > filtro.precioMaximo()) {
            return false;
        }

        if (filtro.areaMinima() > 0 && inmueble.getArea() < filtro.areaMinima()) {
            return false;
        }

        if (filtro.tipoOperacion() != null) {
            Publicacion publicacion = buscarPublicacionPorInmueble(inmueble);

            if (publicacion == null) {
                return false;
            }

            return publicacion.getTipoOperacion() == filtro.tipoOperacion();
        }

        return true;
    }

    private Publicacion buscarPublicacionPorInmueble(Inmueble inmueble) {
        for (Publicacion publicacion : listaPublicaciones) {
            if (publicacion.getInmueble() == inmueble) {
                return publicacion;
            }
        }
        return null;
    }


    @Override
    public Oferta realizarOferta(Comprador comprador, Inmueble inmueble, double valorOferta) {
        if (!esCompradorRegistrado(comprador)) throw new IllegalArgumentException("El comprador no esta registrado");
        if (!listaInmuebles.contains(inmueble)) throw new IllegalArgumentException("El inmueble no esta registrado");

        Oferta oferta = new Oferta(generarCodigoOferta(), comprador, inmueble, valorOferta);
        listaOfertas.add(oferta);
        comprador.sumarReputacion(PUNTOS_REALIZAR_OFERTA);

        crearAlerta(inmueble.getVendedor(), "Recibiste una nueva oferta por el inmueble " + inmueble.getCodigo(), TipoAlerta.CORREO);
        return oferta;
    }

    @Override
    public Transaccion aceptarOferta(Oferta oferta, TipoOperacion tipoOperacion) {
        if (!listaOfertas.contains(oferta)) throw new IllegalArgumentException("La oferta no esta registrada");
        if (oferta.getEstado() != EstadoOferta.PENDIENTE) throw new IllegalStateException("La oferta ya fue procesada");

        oferta.aceptar();

        Inmueble inmueble = oferta.getInmueble();
        if (tipoOperacion == TipoOperacion.VENTA) {
            inmueble.cambiarEstado(EstadoInmueble.VENDIDO);
        } else {
            inmueble.cambiarEstado(EstadoInmueble.ARRENDADO);
        }

        Transaccion transaccion = new Transaccion(
                generarCodigoTransaccion(),
                oferta.getValorOferta(),
                oferta.getComprador(),
                inmueble.getVendedor(),
                inmueble,
                tipoOperacion
        );
        inmueble.setPrecio(oferta.getValorOferta());
        listaTransacciones.add(transaccion);
        oferta.getComprador().sumarReputacion(PUNTOS_COMPRAR_INMUEBLE);
        oferta.getComprador().sumarReputacion(PUNTOS_COMPLETAR_TRANSACCION);
        inmueble.getVendedor().sumarReputacion(PUNTOS_COMPLETAR_TRANSACCION);

        rechazarOfertasPendientesDelInmueble(inmueble, oferta);
        crearAlerta(oferta.getComprador(), "Tu oferta fue aceptada", TipoAlerta.CORREO);

        return transaccion;
    }

    @Override
    public void rechazarOferta(Oferta oferta) {
        if (!listaOfertas.contains(oferta)) throw new IllegalArgumentException("La oferta no esta registrada");
        oferta.rechazar();
        crearAlerta(oferta.getComprador(), "Tu oferta fue rechazada", TipoAlerta.CORREO);
    }


    @Override
    public ArrayList<Inmueble> recomendarInmuebles(Comprador comprador) {
        if (!esCompradorRegistrado(comprador)) throw new IllegalArgumentException("El comprador no esta registrado");

        ArrayList<Inmueble> listaInmueblesRecomendados = new ArrayList<>();

        agregarRecomendacionesPorHistorialBusqueda(comprador, listaInmueblesRecomendados);
        agregarRecomendacionesPorOfertas(comprador, listaInmueblesRecomendados);

        return listaInmueblesRecomendados;
    }
    private void agregarRecomendacionesPorHistorialBusqueda(Comprador comprador, ArrayList<Inmueble> listaInmueblesRecomendados) {
        for (HistorialBusqueda historialBusqueda : listaHistorialBusquedas) {
            if (historialBusqueda.comprador() == comprador) {
                for (Inmueble inmueble : listaInmuebles) {
                    if (cumpleFiltroBusqueda(inmueble, historialBusqueda.filtroBusqueda())
                            && !listaInmueblesRecomendados.contains(inmueble)) {
                        listaInmueblesRecomendados.add(inmueble);
                    }
                }
            }
        }
    }

    private void agregarRecomendacionesPorOfertas(Comprador comprador, ArrayList<Inmueble> listaInmueblesRecomendados) {
        for (Oferta oferta : listaOfertas) {
            if (oferta.getComprador() == comprador) {
                Inmueble inmuebleReferencia = oferta.getInmueble();

                for (Inmueble inmueble : listaInmuebles) {
                    boolean similar = inmueble.estaDisponible()
                            && inmueble != inmuebleReferencia
                            && inmueble.getTipo() == inmuebleReferencia.getTipo()
                            && inmueble.getCiudad().equalsIgnoreCase(inmuebleReferencia.getCiudad());

                    if (similar && !listaInmueblesRecomendados.contains(inmueble)) {
                        listaInmueblesRecomendados.add(inmueble);
                    }
                }
            }
        }
    }

    @Override
    public String generarReporteGeneral() {
        String reporte = "";
        reporte += "Total compradores: " + getListaCompradores().size() + "\n";
        reporte += "Total vendedores: " + getListaVendedores().size() + "\n";
        reporte += "Total inmuebles: " + listaInmuebles.size() + "\n";
        reporte += "Total publicaciones: " + listaPublicaciones.size() + "\n";
        reporte += "Total ofertas: " + listaOfertas.size() + "\n";
        reporte += "Total transacciones: " + listaTransacciones.size() + "\n";
        reporte += "Comprador mas activo: " + obtenerNombreCompradorMasActivo() + "\n";
        reporte += "Vendedor con mas propiedades: " + obtenerNombreVendedorConMasPropiedades() + "\n";
        reporte += "Ciudad con mayor demanda: " + obtenerCiudadConMayorDemanda() + "\n";
        return reporte;
    }

    @Override
    public ArrayList<Inmueble> obtenerInmueblesVendidos() {
        ArrayList<Inmueble> listaInmueblesVendidos = new ArrayList<>();

        for (Inmueble inmueble : listaInmuebles) {
            if (inmueble.getEstado() == EstadoInmueble.VENDIDO) {
                listaInmueblesVendidos.add(inmueble);
            }
        }

        return listaInmueblesVendidos;
    }

    @Override
    public ArrayList<Inmueble> obtenerInmueblesArrendados() {
        ArrayList<Inmueble> listaInmueblesArrendados = new ArrayList<>();

        for (Inmueble inmueble : listaInmuebles) {
            if (inmueble.getEstado() == EstadoInmueble.ARRENDADO) {
                listaInmueblesArrendados.add(inmueble);
            }
        }

        return listaInmueblesArrendados;
    }

    @Override
    public Comprador obtenerCompradorMasActivo() {
        Comprador compradorMasActivo = null;

        for (Comprador comprador : getListaCompradores()) {
            if (compradorMasActivo == null || comprador.getPuntosReputacion() > compradorMasActivo.getPuntosReputacion()) {
                compradorMasActivo = comprador;
            }
        }

        return compradorMasActivo;
    }

    @Override
    public Vendedor obtenerVendedorConMasPropiedades() {
        Vendedor vendedorConMasPropiedades = null;
        int mayorCantidad = 0;

        for (Vendedor vendedor : getListaVendedores()) {
            int cantidad = 0;

            for (Inmueble inmueble : listaInmuebles) {
                if (inmueble.getVendedor() == vendedor) {
                    cantidad++;
                }
            }

            if (vendedorConMasPropiedades == null || cantidad > mayorCantidad) {
                vendedorConMasPropiedades = vendedor;
                mayorCantidad = cantidad;
            }
        }

        return vendedorConMasPropiedades;
    }

    @Override
    public String obtenerCiudadConMayorDemanda() {
        String ciudadMayorDemanda = "Ninguna";
        int mayorCantidad = 0;

        for (Oferta ofertaBase : listaOfertas) {
            String ciudad = ofertaBase.getInmueble().getCiudad();
            int cantidad = 0;

            for (Oferta oferta : listaOfertas) {
                if (oferta.getInmueble().getCiudad().equalsIgnoreCase(ciudad)) {
                    cantidad++;
                }
            }

            if (cantidad > mayorCantidad) {
                mayorCantidad = cantidad;
                ciudadMayorDemanda = ciudad;
            }
        }

        return ciudadMayorDemanda;
    }

    public void cambiarPrecioInmueble(Inmueble inmueble, double nuevoPrecio) {
        if (!listaInmuebles.contains(inmueble)) {
            throw new IllegalArgumentException("El inmueble no esta registrado");
        }

        inmueble.setPrecio(nuevoPrecio);

        crearAlerta(
                inmueble.getVendedor(),
                "El precio del inmueble " + inmueble.getCodigo() + " fue actualizado",
                TipoAlerta.CORREO
        );

        ArrayList<Comprador> compradoresNotificados = new ArrayList<>();

        for (Oferta oferta : listaOfertas) {
            if (oferta.getInmueble() == inmueble && !compradoresNotificados.contains(oferta.getComprador())) {
                crearAlerta(
                        oferta.getComprador(),
                        "Cambio el precio del inmueble " + inmueble.getCodigo() + ". Nuevo precio: $" + nuevoPrecio,
                        TipoAlerta.CORREO
                );
                compradoresNotificados.add(oferta.getComprador());
            }
        }
    }


    private void validarUsuarioNoRegistrado(String identificacion) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getIdentificacion().equals(identificacion)) {
                throw new IllegalArgumentException("El usuario ya esta registrado");
            }
        }
    }

    private void validarAdministradorNoRegistrado(String usuario) {
        for (Administrador administrador : listaAdministradores) {
            if (administrador.getUsuario().equals(usuario)) {
                throw new IllegalArgumentException("El administrador ya esta registrado");
            }
        }
    }

    private boolean esCompradorRegistrado(Comprador comprador) {
        return comprador != null && listaUsuarios.contains(comprador);
    }

    private boolean esVendedorRegistrado(Vendedor vendedor) {
        return vendedor != null && listaUsuarios.contains(vendedor);
    }

    private boolean existeInmuebleConMismosDatos(String direccion, String ciudad, double area,
                                                 double precio, TipoInmueble tipo) {
        if (direccion == null || ciudad == null || tipo == null) {
            return false;
        }

        for (Inmueble inmueble : listaInmuebles) {
            boolean mismosDatos = inmueble.getDireccion().equalsIgnoreCase(direccion)
                    && inmueble.getCiudad().equalsIgnoreCase(ciudad)
                    && Double.compare(inmueble.getArea(), area) == 0
                    && Double.compare(inmueble.getPrecio(), precio) == 0
                    && inmueble.getTipo() == tipo;

            if (mismosDatos) {
                return true;
            }
        }
        return false;
    }

    private Inmueble buscarInmueblePorCodigo(int codigo) {
        for (Inmueble inmueble : listaInmuebles) {
            if (inmueble.getCodigo() == codigo) return inmueble;
        }
        return null;
    }

    private void rechazarOfertasPendientesDelInmueble(Inmueble inmueble, Oferta ofertaAceptada) {
        for (Oferta oferta : listaOfertas) {
            if (oferta.getInmueble() == inmueble && oferta != ofertaAceptada && oferta.getEstado() == EstadoOferta.PENDIENTE) {
                oferta.rechazar();
                crearAlerta(oferta.getComprador(), "Tu oferta fue rechazada porque otra oferta fue aceptada", TipoAlerta.CORREO);
            }
        }
    }

    private void crearAlerta(Usuario usuario, String mensaje, TipoAlerta tipoAlerta) {
        listaAlertas.add(new Alerta(listaAlertas.size() + 1, usuario, mensaje, tipoAlerta));
    }

    private int generarCodigoPublicacion() {
        return listaPublicaciones.size() + 1;
    }

    private int generarCodigoOferta() {
        return listaOfertas.size() + 1;
    }

    private int generarCodigoTransaccion() {
        return listaTransacciones.size() + 1;
    }

    private int generarIdUsuario() {
        return siguienteIdUsuario++;
    }

    private String obtenerNombreCompradorMasActivo() {
        Comprador compradorMasActivo = obtenerCompradorMasActivo();
        return compradorMasActivo == null ? "Ninguno" : compradorMasActivo.getNombre();
    }

    private String obtenerNombreVendedorConMasPropiedades() {
        Vendedor vendedorConMasPropiedades = obtenerVendedorConMasPropiedades();
        return vendedorConMasPropiedades == null ? "Ninguno" : vendedorConMasPropiedades.getNombre();
    }
}
