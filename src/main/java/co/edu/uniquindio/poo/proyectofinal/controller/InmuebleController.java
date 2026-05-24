package co.edu.uniquindio.poo.proyectofinal.controller;


import co.edu.uniquindio.poo.proyectofinal.model.Inmobiliaria;
import co.edu.uniquindio.poo.proyectofinal.model.Inmueble;
import co.edu.uniquindio.poo.proyectofinal.model.TipoInmueble;
import co.edu.uniquindio.poo.proyectofinal.model.Vendedor;

import java.util.ArrayList;
import java.util.Objects;

public class InmuebleController {

    private final Inmobiliaria inmobiliaria;

    public InmuebleController(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = Objects.requireNonNull(inmobiliaria, "La inmobiliaria es obligatoria");
    }

    public Inmueble registrarInmueble(int codigo, String direccion, String ciudad, double area, double precio,
                                      TipoInmueble tipo, Vendedor vendedor) {
        return inmobiliaria.registrarInmueble(codigo, direccion, ciudad, area, precio, tipo, vendedor);
    }

    public ArrayList<Inmueble> listarInmuebles() {
        return inmobiliaria.getListaInmuebles();
    }

    public ArrayList<Inmueble> listarInmueblesDisponibles() {
        ArrayList<Inmueble> disponibles = new ArrayList<>();
        for (Inmueble inmueble : inmobiliaria.getListaInmuebles()) {
            if (inmueble.estaDisponible()) {
                disponibles.add(inmueble);
            }
        }
        return disponibles;
    }

    public ArrayList<Inmueble> listarInmueblesPorVendedor(Vendedor vendedor) {
        ArrayList<Inmueble> inmueblesVendedor = new ArrayList<>();
        for (Inmueble inmueble : inmobiliaria.getListaInmuebles()) {
            if (inmueble.getVendedor() == vendedor) {
                inmueblesVendedor.add(inmueble);
            }
        }
        return inmueblesVendedor;
    }

    public Inmueble buscarInmueblePorCodigo(int codigo) {
        for (Inmueble inmueble : inmobiliaria.getListaInmuebles()) {
            if (inmueble.getCodigo() == codigo) {
                return inmueble;
            }
        }
        return null;
    }

    public void cambiarPrecioInmueble(Inmueble inmueble, double nuevoPrecio) {
        inmobiliaria.cambiarPrecioInmueble(inmueble, nuevoPrecio);
    }
}
