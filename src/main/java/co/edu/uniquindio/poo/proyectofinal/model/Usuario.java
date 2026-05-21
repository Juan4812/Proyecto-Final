package co.edu.uniquindio.poo.proyectofinal.model;

public abstract class Usuario {
    private int id;
    private String nombre;
    private String identificacion;
    private String telefono;
    private String correo;
    private int puntosReputacion;
    private String clasificacion;

    public Usuario(int id, String nombre, String identificacion, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.correo = correo;
        this.puntosReputacion = 0;
        this.clasificacion = "Principiante";
    }

    // Getters y Setters (Encapsulamiento)
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getIdentificacion() { return identificacion; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public int getPuntosReputacion() { return puntosReputacion; }
    public String getClasificacion() { return clasificacion; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setCorreo(String correo) { this.correo = correo; }

    // Método para actualizar reputación (usado por polimorfismo y herencia)
    public void actualizarReputacion(int puntos) {
        this.puntosReputacion += puntos;
        actualizarClasificacion();
    }

    private void actualizarClasificacion() {
        if (puntosReputacion <= 100) {
            clasificacion = "Principiante";
        } else if (puntosReputacion <= 500) {
            clasificacion = "Inversionista";
        } else if (puntosReputacion <= 2000) {
            clasificacion = "Experto Inmobiliario";
        } else {
            clasificacion = "Magnate Inmobiliario";
        }
    }

    public abstract String getTipoUsuario();

    @Override
    public String toString() {
        return nombre + " (" + getTipoUsuario() + ") - " + clasificacion + " [" + puntosReputacion + " pts]";
    }
}