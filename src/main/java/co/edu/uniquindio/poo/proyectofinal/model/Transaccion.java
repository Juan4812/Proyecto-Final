package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;

public class Transaccion {
    private int codigoTransaccion;
    private Comprador comprador;
    private Vendedor vendedor;
    private Inmueble inmueble;
    private double valorFinal;
    private TipoOperacion tipoOperacion;
    private LocalDate fecha;

    public Transaccion(int codigoTransaccion, Comprador comprador, Vendedor vendedor,
                       Inmueble inmueble, double valorFinal, TipoOperacion tipoOperacion) {
        if (comprador == null) throw new IllegalArgumentException("El comprador es obligatorio");
        if (vendedor == null) throw new IllegalArgumentException("El vendedor es obligatorio");
        if (inmueble == null) throw new IllegalArgumentException("El inmueble es obligatorio");
        if (valorFinal <= 0) throw new IllegalArgumentException("El valor final debe ser mayor a 0");
        if (tipoOperacion == null) throw new IllegalArgumentException("El tipo de operacion es obligatorio");

        this.codigoTransaccion = codigoTransaccion;
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.inmueble = inmueble;
        this.valorFinal = valorFinal;
        this.tipoOperacion = tipoOperacion;
        this.fecha = LocalDate.now();
    }

    public int getCodigoTransaccion() { return codigoTransaccion; }
    public Comprador getComprador() { return comprador; }
    public Vendedor getVendedor() { return vendedor; }
    public Inmueble getInmueble() { return inmueble; }
    public double getValorFinal() { return valorFinal; }
    public TipoOperacion getTipoOperacion() { return tipoOperacion; }
    public LocalDate getFecha() { return fecha; }

    @Override
    public String toString() {
        return "Transaccion " + codigoTransaccion + " - " + tipoOperacion + " - $" + valorFinal;
    }
}