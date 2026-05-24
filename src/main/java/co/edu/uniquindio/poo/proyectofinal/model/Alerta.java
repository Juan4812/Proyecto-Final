package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDateTime;

public class Alerta {

    //Atributos
    private int codigo;
    private Usuario usuario;
    private String mensaje;
    private TipoAlerta tipoAlerta;
    private LocalDateTime fecha;

    //Constructor
    public Alerta(int codigo, Usuario usuario, String mensaje, TipoAlerta tipoAlerta) {
        if (usuario == null) throw new IllegalArgumentException("El usuario es obligatorio");
        if (mensaje == null || mensaje.trim().isEmpty()) throw new IllegalArgumentException("El mensaje es obligatorio");
        if (tipoAlerta == null) throw new IllegalArgumentException("El tipo de alerta es obligatorio");

        this.codigo = codigo;
        this.usuario = usuario;
        this.mensaje = mensaje;
        this.tipoAlerta = tipoAlerta;
        this.fecha = LocalDateTime.now();
    }

    //Metodossssss

    //Get and Set
    public int getCodigo() { return codigo; }
    public Usuario getUsuario() { return usuario; }
    public String getMensaje() { return mensaje; }
    public TipoAlerta getTipoAlerta() { return tipoAlerta; }
    public LocalDateTime getFecha() { return fecha; }


    //ToString
    @Override
    public String toString() {
        return tipoAlerta + " para " + usuario.getNombre() + ": " + mensaje;
    }
}