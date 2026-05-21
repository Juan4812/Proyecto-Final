package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;

public class Publicacion {
    private int codigoPublicacion;
    private LocalDate fechaPublicacion;
    private String descripcion;
    private Inmueble inmueble;
    private TipoOperacion tipoOperacion;

    public Publicacion(int codigoPublicacion, String descripcion, Inmueble inmueble, TipoOperacion tipoOperacion) {
        if (descripcion == null || descripcion.trim().isEmpty()) throw new IllegalArgumentException("La descripcion es obligatoria");
        if (inmueble == null) throw new IllegalArgumentException("El inmueble es obligatorio");
        if (tipoOperacion == null) throw new IllegalArgumentException("El tipo de operacion es obligatorio");

        this.codigoPublicacion = codigoPublicacion;
        this.descripcion = descripcion;
        this.inmueble = inmueble;
        this.tipoOperacion = tipoOperacion;
        this.fechaPublicacion = LocalDate.now();
    }

    public int getCodigoPublicacion() { return codigoPublicacion; }
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public String getDescripcion() { return descripcion; }
    public Inmueble getInmueble() { return inmueble; }
    public TipoOperacion getTipoOperacion() { return tipoOperacion; }

    @Override
    public String toString() {
        return "Publicacion " + codigoPublicacion + " - " + tipoOperacion + " - " + inmueble;
    }
}