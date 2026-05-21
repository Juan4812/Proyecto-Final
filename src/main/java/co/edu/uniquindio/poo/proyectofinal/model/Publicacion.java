package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;

public class Publicacion {
    private int codigoPublicacion;
    private LocalDate fechaPublicacion;
    private String descripcion;
    private Inmueble inmueble;

    public Publicacion(int codigoPublicacion, String descripcion, Inmueble inmueble) {
        this.codigoPublicacion = codigoPublicacion;
        this.fechaPublicacion = LocalDate.now();
        this.descripcion = descripcion;
        this.inmueble = inmueble;
    }

    public int getCodigoPublicacion() { return codigoPublicacion; }
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public String getDescripcion() { return descripcion; }
    public Inmueble getInmueble() { return inmueble; }

    @Override
    public String toString() {
        return "Publicación #" + codigoPublicacion + " - " + fechaPublicacion + "\n" +
               "Inmueble: " + inmueble.getCodigo() + " | " + descripcion;
    }
}