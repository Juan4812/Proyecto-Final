package co.edu.uniquindio.poo.proyectofinal.model;

public class Comprador extends Usuario {

    public Comprador(int id, String nombre, String identificacion, String telefono, String correo) {
        super(id, nombre, identificacion, telefono, correo);
    }

    @Override
    public String getTipoUsuario() {
        return "Comprador";
    }

    @Override
    public int calcularBeneficio() {
        return getPuntosReputacion() / 10;
    }
}