package co.edu.uniquindio.poo.proyectofinal.controller;

import co.edu.uniquindio.poo.proyectofinal.model.Inmobiliaria;
import co.edu.uniquindio.poo.proyectofinal.model.Inmueble;
import co.edu.uniquindio.poo.proyectofinal.model.Publicacion;
import co.edu.uniquindio.poo.proyectofinal.model.TipoOperacion;
import co.edu.uniquindio.poo.proyectofinal.model.Vendedor;

import java.util.ArrayList;
import java.util.Objects;

public class PublicacionController {

    private final Inmobiliaria inmobiliaria;

    public PublicacionController(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = Objects.requireNonNull(inmobiliaria, "La inmobiliaria es obligatoria");
    }

    public Publicacion publicarInmueble(Vendedor vendedor, Inmueble inmueble, String descripcion,
                                        TipoOperacion tipoOperacion) {
        return inmobiliaria.publicarInmueble(vendedor, inmueble, descripcion, tipoOperacion);
    }

    public ArrayList<Publicacion> listarPublicaciones() {
        return inmobiliaria.getListaPublicaciones();
    }

    public Publicacion buscarPublicacionPorCodigo(int codigoPublicacion) {
        for (Publicacion publicacion : inmobiliaria.getListaPublicaciones()) {
            if (publicacion.getCodigoPublicacion() == codigoPublicacion) {
                return publicacion;
            }
        }
        return null;
    }
}
