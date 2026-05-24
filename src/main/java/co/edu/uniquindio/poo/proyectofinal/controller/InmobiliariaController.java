package co.edu.uniquindio.poo.proyectofinal.controller;

import co.edu.uniquindio.poo.proyectofinal.model.Alerta;
import co.edu.uniquindio.poo.proyectofinal.model.Comprador;
import co.edu.uniquindio.poo.proyectofinal.model.Inmobiliaria;
import co.edu.uniquindio.poo.proyectofinal.model.Inmueble;
import co.edu.uniquindio.poo.proyectofinal.model.Oferta;
import co.edu.uniquindio.poo.proyectofinal.model.Publicacion;
import co.edu.uniquindio.poo.proyectofinal.model.Transaccion;
import co.edu.uniquindio.poo.proyectofinal.model.Vendedor;

import java.util.ArrayList;
import java.util.Objects;

public class InmobiliariaController {

    private final Inmobiliaria inmobiliaria;

    public InmobiliariaController() {
        this(new Inmobiliaria("InmoSmart", "000000000"));
    }

    public InmobiliariaController(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = Objects.requireNonNull(inmobiliaria, "La inmobiliaria es obligatoria");
    }

    public Inmobiliaria getInmobiliaria() {
        return inmobiliaria;
    }

    public ArrayList<Comprador> obtenerCompradores() {
        return inmobiliaria.getListaCompradores();
    }

    public ArrayList<Vendedor> obtenerVendedores() {
        return inmobiliaria.getListaVendedores();
    }

    public ArrayList<Inmueble> obtenerInmuebles() {
        return inmobiliaria.getListaInmuebles();
    }

    public ArrayList<Publicacion> obtenerPublicaciones() {
        return inmobiliaria.getListaPublicaciones();
    }

    public ArrayList<Oferta> obtenerOfertas() {
        return inmobiliaria.getListaOfertas();
    }

    public ArrayList<Transaccion> obtenerTransacciones() {
        return inmobiliaria.getListaTransacciones();
    }

    public ArrayList<Alerta> obtenerAlertas() {
        return inmobiliaria.getListaAlertas();
    }

    public UsuarioController crearUsuarioController() {
        return new UsuarioController(inmobiliaria);
    }

    public InmuebleController crearInmuebleController() {
        return new InmuebleController(inmobiliaria);
    }

    public PublicacionController crearPublicacionController() {
        return new PublicacionController(inmobiliaria);
    }

    public BusquedaController crearBusquedaController() {
        return new BusquedaController(inmobiliaria);
    }

    public OfertaController crearOfertaController() {
        return new OfertaController(inmobiliaria);
    }

    public TransaccionController crearTransaccionController() {
        return new TransaccionController(inmobiliaria);
    }

    public RecomendacionController crearRecomendacionController() {
        return new RecomendacionController(inmobiliaria);
    }

    public ReporteController crearReporteController() {
        return new ReporteController(inmobiliaria);
    }

    public AlertaController crearAlertaController() {
        return new AlertaController(inmobiliaria);
    }
}
