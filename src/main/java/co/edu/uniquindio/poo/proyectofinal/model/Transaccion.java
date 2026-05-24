package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;

public class Transaccion {

    //Atributos
    private int codigoTransaccion;
    private double valorFinal;
    private LocalDate fecha;

    //Relaciones
    private Comprador comprador;
    private Vendedor vendedor;
    private Inmueble inmueble;
    private TipoOperacion tipoOperacion;

    //Contructor
    public Transaccion(int codigoTransaccion, double valorFinal, LocalDate fecha, Comprador comprador, Vendedor vendedor,
                       Inmueble inmueble, TipoOperacion tipoOperacion) {
        if (comprador == null) throw new IllegalArgumentException("El comprador es obligatorio");
        if (vendedor == null) throw new IllegalArgumentException("El vendedor es obligatorio");
        if (inmueble == null) throw new IllegalArgumentException("El inmueble es obligatorio");
        if (valorFinal <= 0) throw new IllegalArgumentException("El valor final debe ser mayor a 0");
        if (tipoOperacion == null) throw new IllegalArgumentException("El tipo de operacion es obligatorio");

        this.codigoTransaccion = codigoTransaccion;
        this.valorFinal = valorFinal;
        this.fecha = LocalDate.now();
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.inmueble = inmueble;
        this.tipoOperacion = tipoOperacion;
    }

    //Metodossssss

    //Get and Set
    public int getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    //ToString
    @Override
    public String toString() {
        return "Transaccion " + codigoTransaccion + " - " + tipoOperacion + " - $" + valorFinal;
    }
}