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
        return "Transacción #" + codigoTransaccion + " | " + tipoOperacion +
               " | Inmueble: " + inmueble.getCodigo() +
               " | Valor: $" + valorFinal +
               " | Comprador: " + comprador.getNombre() +
               " | Vendedor: " + vendedor.getNombre() +
               " | Fecha: " + fecha;
    }
}