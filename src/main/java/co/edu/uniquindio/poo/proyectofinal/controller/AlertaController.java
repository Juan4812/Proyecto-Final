package co.edu.uniquindio.poo.proyectofinal.controller;

import co.edu.uniquindio.poo.proyectofinal.model.Alerta;
import co.edu.uniquindio.poo.proyectofinal.model.Inmobiliaria;
import co.edu.uniquindio.poo.proyectofinal.model.Usuario;

import java.util.ArrayList;
import java.util.Objects;

public class AlertaController {

    private final Inmobiliaria inmobiliaria;

    public AlertaController(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = Objects.requireNonNull(inmobiliaria, "La inmobiliaria es obligatoria");
    }

    public ArrayList<Alerta> listarAlertas() {
        return inmobiliaria.getListaAlertas();
    }

    public ArrayList<Alerta> listarAlertasPorUsuario(Usuario usuario) {
        ArrayList<Alerta> alertasUsuario = new ArrayList<>();
        for (Alerta alerta : inmobiliaria.getListaAlertas()) {
            if (alerta.getUsuario() == usuario) {
                alertasUsuario.add(alerta);
            }
        }
        return alertasUsuario;
    }
}
