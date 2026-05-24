package co.edu.uniquindio.poo.proyectofinal.controller;

import co.edu.uniquindio.poo.proyectofinal.model.Inmobiliaria;

import java.util.Objects;

public class ReporteController {

    private final Inmobiliaria inmobiliaria;

    public ReporteController(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = Objects.requireNonNull(inmobiliaria, "La inmobiliaria es obligatoria");
    }

    public String generarReporteGeneral() {
        return inmobiliaria.generarReporteGeneral();
    }
}
