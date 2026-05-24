package co.edu.uniquindio.poo.proyectofinal.model;

public class Vendedor extends Usuario {

    public Vendedor(String nombre, String identificacion, String telefono, String correo,int puntosReputacion, String clasificacion) {
        super(nombre, identificacion, telefono, correo, puntosReputacion, clasificacion);
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