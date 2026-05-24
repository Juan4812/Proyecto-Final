package co.edu.uniquindio.poo.proyectofinal.controller;

import co.edu.uniquindio.poo.proyectofinal.model.Comprador;
import co.edu.uniquindio.poo.proyectofinal.model.Inmobiliaria;
import co.edu.uniquindio.poo.proyectofinal.model.Inmueble;

import java.util.ArrayList;
import java.util.Objects;

public class RecomendacionController {

    private final Inmobiliaria inmobiliaria;

    public RecomendacionController(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = Objects.requireNonNull(inmobiliaria, "La inmobiliaria es obligatoria");
    }

    public ArrayList<Inmueble> recomendarInmuebles(Comprador comprador) {
        return inmobiliaria.recomendarInmuebles(comprador);
    }
}
