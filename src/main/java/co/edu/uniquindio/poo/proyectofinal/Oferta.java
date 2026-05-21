package model;

import java.time.LocalDate;

public class Oferta {
    private int codigoOferta;
    private Comprador comprador;
    private Inmueble inmueble;
    private double valorOferta;
    private LocalDate fechaOferta;
    private EstadoOferta estado;

    public Oferta(int codigoOferta, Comprador comprador, Inmueble inmueble, double valorOferta) {
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
    public EstadoOferta getEstado() { return estado; }

    public void aceptar() {
        this.estado = EstadoOferta.ACEPTADA;
    }

    public void rechazar() {
        this.estado = EstadoOferta.RECHAZADA;
    }

    @Override
    public String toString() {
        return "Oferta #" + codigoOferta + " | Comprador: " + comprador.getNombre() +
               " | Inmueble: " + inmueble.getCodigo() + " | Valor: $" + valorOferta +
               " | Estado: " + estado + " | Fecha: " + fechaOferta;
    }
}