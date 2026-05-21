package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;

public class Oferta {
    private int codigoOferta;
    private Comprador comprador;
    private Inmueble inmueble;
    private double valorOferta;
    private LocalDate fechaOferta;
    private EstadoOferta estado;

    public Oferta(int codigoOferta, Comprador comprador, Inmueble inmueble, double valorOferta) {
        if (comprador == null) throw new IllegalArgumentException("El comprador es obligatorio");
        if (inmueble == null) throw new IllegalArgumentException("El inmueble es obligatorio");
        if (valorOferta <= 0) throw new IllegalArgumentException("El valor de la oferta debe ser mayor a 0");
        if (!inmueble.estaDisponible()) throw new IllegalArgumentException("El inmueble no esta disponible");

        this.codigoOferta = codigoOferta;
        this.comprador = comprador;
        this.inmueble = inmueble;
        this.valorOferta = valorOferta;
        this.fechaOferta = LocalDate.now();
        this.estado = EstadoOferta.PENDIENTE;
    }

    public int getCodigoOferta() { return codigoOferta; }
    public Comprador getComprador() { return comprador; }
    public Inmueble getInmueble() { return inmueble; }
    public double getValorOferta() { return valorOferta; }
    public LocalDate getFechaOferta() { return fechaOferta; }
    public EstadoOferta getEstado() { return estado; }

    public void aceptar() {
        if (estado != EstadoOferta.PENDIENTE) throw new IllegalStateException("La oferta ya fue procesada");
        estado = EstadoOferta.ACEPTADA;
    }

    public void rechazar() {
        if (estado != EstadoOferta.PENDIENTE) throw new IllegalStateException("La oferta ya fue procesada");
        estado = EstadoOferta.RECHAZADA;
    }

    @Override
    public String toString() {
        return "Oferta " + codigoOferta + " - " + comprador.getNombre() + " - $" + valorOferta + " - " + estado;
    }
}