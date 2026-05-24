package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;

public interface IOperacionesInmobiliarias {
    Publicacion publicarInmueble(Vendedor vendedor, Inmueble inmueble, String descripcion, TipoOperacion tipoOperacion);

    ArrayList<Inmueble> buscarInmuebles(FiltroBusqueda filtro);

    Oferta realizarOferta(Comprador comprador, Inmueble inmueble, double valorOferta);

    Transaccion aceptarOferta(Oferta oferta, TipoOperacion tipoOperacion);

    void rechazarOferta(Oferta oferta);

    ArrayList<Inmueble> recomendarInmuebles(Comprador comprador);

    String generarReporteGeneral();
}