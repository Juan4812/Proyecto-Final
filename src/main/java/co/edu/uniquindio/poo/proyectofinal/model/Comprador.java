package co.edu.uniquindio.poo.proyectofinal.model;

public class Comprador extends Usuario {

    //Constructor
    public Comprador(String nombre, String identificacion, String telefono, String correo,int puntosReputacion, String clasificacion) {
        super(nombre, identificacion, telefono, correo, puntosReputacion, clasificacion);
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