package co.edu.uniquindio.poo.proyectofinal.controller;

import co.edu.uniquindio.poo.proyectofinal.model.Comprador;
import co.edu.uniquindio.poo.proyectofinal.model.Inmobiliaria;
import co.edu.uniquindio.poo.proyectofinal.model.Inmueble;
import co.edu.uniquindio.poo.proyectofinal.model.Oferta;
import co.edu.uniquindio.poo.proyectofinal.model.TipoOperacion;
import co.edu.uniquindio.poo.proyectofinal.model.Transaccion;

import java.util.ArrayList;
import java.util.Objects;

public class OfertaController {

    private final Inmobiliaria inmobiliaria;

    public OfertaController(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = Objects.requireNonNull(inmobiliaria, "La inmobiliaria es obligatoria");
    }

    public Oferta realizarOferta(Comprador comprador, Inmueble inmueble, double valorOferta) {
        return inmobiliaria.realizarOferta(comprador, inmueble, valorOferta);
    }

    public Transaccion aceptarOferta(Oferta oferta, TipoOperacion tipoOperacion) {
        return inmobiliaria.aceptarOferta(oferta, tipoOperacion);
    }

    public void rechazarOferta(Oferta oferta) {
        inmobiliaria.rechazarOferta(oferta);
    }

    public ArrayList<Oferta> listarOfertas() {
        return inmobiliaria.getListaOfertas();
    }

    public ArrayList<Oferta> listarOfertasPorComprador(Comprador comprador) {
        ArrayList<Oferta> ofertasComprador = new ArrayList<>();
        for (Oferta oferta : inmobiliaria.getListaOfertas()) {
            if (oferta.getComprador() == comprador) {
                ofertasComprador.add(oferta);
            }
        }
        return ofertasComprador;
    }

    public ArrayList<Oferta> listarOfertasPorInmueble(Inmueble inmueble) {
        ArrayList<Oferta> ofertasInmueble = new ArrayList<>();
        for (Oferta oferta : inmobiliaria.getListaOfertas()) {
            if (oferta.getInmueble() == inmueble) {
                ofertasInmueble.add(oferta);
            }
        }
        return ofertasInmueble;
    }

    public Oferta buscarOfertaPorCodigo(int codigoOferta) {
        for (Oferta oferta : inmobiliaria.getListaOfertas()) {
            if (oferta.getCodigoOferta() == codigoOferta) {
                return oferta;
            }
        }
        return null;
    }
}
