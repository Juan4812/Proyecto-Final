package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;

public class Inmobiliaria implements OperacionesInmobiliarias {
    private ArrayList<Comprador> listaCompradores;
    private ArrayList<Vendedor> listaVendedores;
    private ArrayList<Inmueble> listaInmuebles;
    private ArrayList<Publicacion> listaPublicaciones;
    private ArrayList<Oferta> listaOfertas;
    private ArrayList<Transaccion> listaTransacciones;
    private ArrayList<Alerta> listaAlertas;

    public Inmobiliaria() {
        listaCompradores = new ArrayList<>();
        listaVendedores = new ArrayList<>();
        listaInmuebles = new ArrayList<>();
        listaPublicaciones = new ArrayList<>();
        listaOfertas = new ArrayList<>();
        listaTransacciones = new ArrayList<>();
        listaAlertas = new ArrayList<>();
    }

    public ArrayList<Comprador> getListaCompradores() { return listaCompradores; }
    public ArrayList<Vendedor> getListaVendedores() { return listaVendedores; }
    public ArrayList<Inmueble> getListaInmuebles() { return listaInmuebles; }
    public ArrayList<Publicacion> getListaPublicaciones() { return listaPublicaciones; }
    public ArrayList<Oferta> getListaOfertas() { return listaOfertas; }
    public ArrayList<Transaccion> getListaTransacciones() { return listaTransacciones; }
    public ArrayList<Alerta> getListaAlertas() { return listaAlertas; }

    public Comprador registrarComprador(int id, String nombre, String identificacion, String telefono, String correo) {
        validarUsuarioNoRegistrado(id, identificacion);
        Comprador comprador = new Comprador(id, nombre, identificacion, telefono, correo);
        listaCompradores.add(comprador);
        return comprador;
    }

    public Vendedor registrarVendedor(int id, String nombre, String identificacion, String telefono, String correo) {
        validarUsuarioNoRegistrado(id, identificacion);
        Vendedor vendedor = new Vendedor(id, nombre, identificacion, telefono, correo);
        listaVendedores.add(vendedor);
        return vendedor;
    }

    public Inmueble registrarInmueble(int codigo, TipoInmueble tipo, String direccion, String ciudad,
                                      double area, double precio, Vendedor vendedor) {
        if (!listaVendedores.contains(vendedor)) throw new IllegalArgumentException("El vendedor no esta registrado");
        if (buscarInmueblePorCodigo(codigo) != null) throw new IllegalArgumentException("Ya existe un inmueble con ese codigo");

        Inmueble inmueble = new Inmueble(codigo, tipo, direccion, ciudad, area, precio, vendedor);
        listaInmuebles.add(inmueble);
        return inmueble;
    }

    @Override
    public Publicacion publicarInmueble(Vendedor vendedor, Inmueble inmueble, String descripcion, TipoOperacion tipoOperacion) {
        if (!listaVendedores.contains(vendedor)) throw new IllegalArgumentException("El vendedor no esta registrado");
        if (!listaInmuebles.contains(inmueble)) throw new IllegalArgumentException("El inmueble no esta registrado");
        if (inmueble.getVendedor() != vendedor) throw new IllegalArgumentException("El inmueble no pertenece al vendedor");
        if (!inmueble.estaDisponible()) throw new IllegalArgumentException("El inmueble no esta disponible");

        Publicacion publicacion = new Publicacion(generarCodigoPublicacion(), descripcion, inmueble, tipoOperacion);
        listaPublicaciones.add(publicacion);
        vendedor.sumarReputacion(10);
        return publicacion;
    }

    @Override
    public ArrayList<Inmueble> buscarInmuebles(FiltroBusqueda filtro) {
        ArrayList<Inmueble> listaInmueblesEncontrados = new ArrayList<>();

        for (Inmueble inmueble : listaInmuebles) {
            if (!inmueble.estaDisponible()) continue;
            if (filtro.getCiudad() != null && !filtro.getCiudad().trim().isEmpty()
                    && !inmueble.getCiudad().equalsIgnoreCase(filtro.getCiudad())) continue;
            if (filtro.getTipoInmueble() != null && inmueble.getTipo() != filtro.getTipoInmueble()) continue;
            if (filtro.getPrecioMinimo() > 0 && inmueble.getPrecio() < filtro.getPrecioMinimo()) continue;
            if (filtro.getPrecioMaximo() > 0 && inmueble.getPrecio() > filtro.getPrecioMaximo()) continue;
            if (filtro.getAreaMinima() > 0 && inmueble.getArea() < filtro.getAreaMinima()) continue;

            listaInmueblesEncontrados.add(inmueble);
        }

        return listaInmueblesEncontrados;
    }

    @Override
    public Oferta realizarOferta(Comprador comprador, Inmueble inmueble, double valorOferta) {
        if (!listaCompradores.contains(comprador)) throw new IllegalArgumentException("El comprador no esta registrado");
        if (!listaInmuebles.contains(inmueble)) throw new IllegalArgumentException("El inmueble no esta registrado");

        Oferta oferta = new Oferta(generarCodigoOferta(), comprador, inmueble, valorOferta);
        listaOfertas.add(oferta);
        comprador.sumarReputacion(5);

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
                oferta.getComprador(),
                inmueble.getVendedor(),
                inmueble,
                oferta.getValorOferta(),
                tipoOperacion
        );

        listaTransacciones.add(transaccion);
        oferta.getComprador().sumarReputacion(50);
        oferta.getComprador().sumarReputacion(100);
        inmueble.getVendedor().sumarReputacion(100);

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
        if (!listaCompradores.contains(comprador)) throw new IllegalArgumentException("El comprador no esta registrado");

        ArrayList<Inmueble> listaInmueblesRecomendados = new ArrayList<>();

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

        return listaInmueblesRecomendados;
    }

    @Override
    public String generarReporteGeneral() {
        String reporte = "";
        reporte += "Total compradores: " + listaCompradores.size() + "\n";
        reporte += "Total vendedores: " + listaVendedores.size() + "\n";
        reporte += "Total inmuebles: " + listaInmuebles.size() + "\n";
        reporte += "Total publicaciones: " + listaPublicaciones.size() + "\n";
        reporte += "Total ofertas: " + listaOfertas.size() + "\n";
        reporte += "Total transacciones: " + listaTransacciones.size() + "\n";
        reporte += "Comprador mas activo: " + obtenerCompradorMasActivo() + "\n";
        reporte += "Vendedor con mas propiedades: " + obtenerVendedorConMasPropiedades() + "\n";
        reporte += "Ciudad con mayor demanda: " + obtenerCiudadConMayorDemanda() + "\n";
        return reporte;
    }

    public void cambiarPrecioInmueble(Inmueble inmueble, double nuevoPrecio) {
        if (!listaInmuebles.contains(inmueble)) throw new IllegalArgumentException("El inmueble no esta registrado");
        inmueble.setPrecio(nuevoPrecio);
        crearAlerta(inmueble.getVendedor(), "El precio del inmueble " + inmueble.getCodigo() + " fue actualizado", TipoAlerta.CORREO);
    }

    private void validarUsuarioNoRegistrado(int id, String identificacion) {
        for (Comprador comprador : listaCompradores) {
            if (comprador.getId() == id || comprador.getIdentificacion().equals(identificacion)) {
                throw new IllegalArgumentException("El usuario ya esta registrado");
            }
        }

        for (Vendedor vendedor : listaVendedores) {
            if (vendedor.getId() == id || vendedor.getIdentificacion().equals(identificacion)) {
                throw new IllegalArgumentException("El usuario ya esta registrado");
            }
        }
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

    private String obtenerCompradorMasActivo() {
        Comprador compradorMasActivo = null;

        for (Comprador comprador : listaCompradores) {
            if (compradorMasActivo == null || comprador.getPuntosReputacion() > compradorMasActivo.getPuntosReputacion()) {
                compradorMasActivo = comprador;
            }
        }

        return compradorMasActivo == null ? "Ninguno" : compradorMasActivo.getNombre();
    }

    private String obtenerVendedorConMasPropiedades() {
        Vendedor vendedorConMasPropiedades = null;
        int mayorCantidad = 0;

        for (Vendedor vendedor : listaVendedores) {
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

        return vendedorConMasPropiedades == null ? "Ninguno" : vendedorConMasPropiedades.getNombre();
    }

    private String obtenerCiudadConMayorDemanda() {
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
}