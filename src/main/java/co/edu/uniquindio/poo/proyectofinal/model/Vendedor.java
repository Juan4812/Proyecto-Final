package co.edu.uniquindio.poo.proyectofinal.model;

public class Vendedor extends Usuario {

    public Vendedor(int id, String nombre, String identificacion, String telefono, String correo) {
        super(id, nombre, identificacion, telefono, correo);
    }

    @Override
    public String getTipoUsuario() {
        return "Vendedor";
    }

    @Override
    public int calcularBeneficio() {
        return getPuntosReputacion() / 5;
    }
}