package co.edu.uniquindio.poo.proyectofinal.controller;

import co.edu.uniquindio.poo.proyectofinal.model.Inmobiliaria;
import co.edu.uniquindio.poo.proyectofinal.model.Transaccion;

import java.util.ArrayList;
import java.util.Objects;

public class TransaccionController {

    private final Inmobiliaria inmobiliaria;

    public TransaccionController(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = Objects.requireNonNull(inmobiliaria, "La inmobiliaria es obligatoria");
    }

    public ArrayList<Transaccion> listarTransacciones() {
        return inmobiliaria.getListaTransacciones();
    }

    public Transaccion buscarTransaccionPorCodigo(int codigoTransaccion) {
        for (Transaccion transaccion : inmobiliaria.getListaTransacciones()) {
            if (transaccion.getCodigoTransaccion() == codigoTransaccion) {
                return transaccion;
            }
        }
        return null;
    }
}
