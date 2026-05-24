package co.edu.uniquindio.poo.proyectofinal.controller;

import co.edu.uniquindio.poo.proyectofinal.model.Comprador;
import co.edu.uniquindio.poo.proyectofinal.model.Inmobiliaria;
import co.edu.uniquindio.poo.proyectofinal.model.Inmueble;
import co.edu.uniquindio.poo.proyectofinal.model.Vendedor;

import java.util.ArrayList;
import java.util.Objects;

public class ReporteController {

    private final Inmobiliaria inmobiliaria;

    public ReporteController(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = Objects.requireNonNull(inmobiliaria, "La inmobiliaria es obligatoria");
    }

    public String generarReporteGeneral() {
        return inmobiliaria.generarReporteGeneral();
    }

    public ArrayList<Inmueble> obtenerInmueblesVendidos() {
        return inmobiliaria.obtenerInmueblesVendidos();
    }

    public ArrayList<Inmueble> obtenerInmueblesArrendados() {
        return inmobiliaria.obtenerInmueblesArrendados();
    }

    public Comprador obtenerCompradorMasActivo() {
        return inmobiliaria.obtenerCompradorMasActivo();
    }

    public Vendedor obtenerVendedorConMasPropiedades() {
        return inmobiliaria.obtenerVendedorConMasPropiedades();
    }

    public String obtenerCiudadConMayorDemanda() {
        return inmobiliaria.obtenerCiudadConMayorDemanda();
    }
}