package co.edu.uniquindio.poo.proyectofinal.controller;

import co.edu.uniquindio.poo.proyectofinal.model.Comprador;
import co.edu.uniquindio.poo.proyectofinal.model.Inmobiliaria;
import co.edu.uniquindio.poo.proyectofinal.model.Usuario;
import co.edu.uniquindio.poo.proyectofinal.model.Vendedor;

import java.util.ArrayList;
import java.util.Objects;

public class UsuarioController {

    private final Inmobiliaria inmobiliaria;

    public UsuarioController(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = Objects.requireNonNull(inmobiliaria, "La inmobiliaria es obligatoria");
    }

    public Comprador registrarComprador(String nombre, String identificacion, String telefono, String correo) {
        return inmobiliaria.registrarComprador(nombre, identificacion, telefono, correo);
    }

    public Vendedor registrarVendedor(String nombre, String identificacion, String telefono, String correo) {
        return inmobiliaria.registrarVendedor(nombre, identificacion, telefono, correo);
    }


    public ArrayList<Comprador> listarCompradores() {
        return inmobiliaria.getListaCompradores();
    }

    public ArrayList<Usuario> listarUsuarios() {
        return inmobiliaria.getListaUsuarios();
    }

    public ArrayList<Vendedor> listarVendedores() {
        return inmobiliaria.getListaVendedores();
    }

    public Comprador buscarCompradorPorIdentificacion(String identificacion) {
        for (Comprador comprador : inmobiliaria.getListaCompradores()) {
            if (comprador.getIdentificacion().equals(identificacion)) {
                return comprador;
            }
        }
        return null;
    }

    public Vendedor buscarVendedorPorIdentificacion(String identificacion) {
        for (Vendedor vendedor : inmobiliaria.getListaVendedores()) {
            if (vendedor.getIdentificacion().equals(identificacion)) {
                return vendedor;
            }
        }
        return null;
    }

    public Usuario buscarUsuarioPorIdentificacion(String identificacion) {
        Comprador comprador = buscarCompradorPorIdentificacion(identificacion);
        if (comprador != null) {
            return comprador;
        }
        return buscarVendedorPorIdentificacion(identificacion);
    }
}
