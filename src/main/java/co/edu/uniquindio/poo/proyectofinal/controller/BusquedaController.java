package co.edu.uniquindio.poo.proyectofinal.controller;

import co.edu.uniquindio.poo.proyectofinal.model.*;

import java.util.ArrayList;
import java.util.Objects;

public class BusquedaController {

    private final Inmobiliaria inmobiliaria;

    public BusquedaController(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = Objects.requireNonNull(inmobiliaria, "La inmobiliaria es obligatoria");
    }

    public ArrayList<Inmueble> buscarInmuebles(String ciudad, TipoInmueble tipoInmueble, double precioMinimo, double precioMaximo, double areaMinima, TipoOperacion tipoOperacion) {
        FiltroBusqueda filtro = new FiltroBusqueda(ciudad, tipoInmueble, precioMinimo, precioMaximo, areaMinima, tipoOperacion);
        return inmobiliaria.buscarInmuebles(filtro);
    }

    public ArrayList<Inmueble> buscarInmuebles(FiltroBusqueda filtro) {
        return inmobiliaria.buscarInmuebles(filtro);
    }
}
