package co.edu.uniquindio.poo.proyectofinal.controller;

import co.edu.uniquindio.poo.proyectofinal.model.Administrador;
import co.edu.uniquindio.poo.proyectofinal.model.Inmobiliaria;

import java.util.ArrayList;
import java.util.Objects;

public class AdministradorController {

    private final Inmobiliaria inmobiliaria;

    public AdministradorController(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = Objects.requireNonNull(inmobiliaria, "La inmobiliaria es obligatoria");
    }

    public Administrador registrarAdministrador(String nombre, String usuario, String contrasena) {
        return inmobiliaria.registrarAdministrador(nombre, usuario, contrasena);
    }

    public Administrador autenticarAdministrador(String usuario, String contrasena) {
        return inmobiliaria.autenticarAdministrador(usuario, contrasena);
    }

    public void cambiarContrasena(Administrador administrador, String nuevaContrasena, String confirmacionContrasena) {
        inmobiliaria.cambiarContrasenaAdministrador(administrador, nuevaContrasena, confirmacionContrasena);
    }

    public ArrayList<Administrador> listarAdministradores() {
        return inmobiliaria.getListaAdministradores();
    }
}